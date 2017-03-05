/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['Номер', 'Тип', 'Рівень', 'Номер курсу', 'Куратор'];
    insertTable(titles, "groupCurators-table");
    loadTable(0, $('#numberOnPage').val());
});

function loadTable(page, size) {
    $.getJSON("/api/groups" ,{page: page, size: size} ,function(response){
        var htmlRow = "";
        $.each(response.data.content, function (i, entity) {
            htmlRow += "<tr id=" + entity.id + ">";
            htmlRow +="<td>" + entity.shortSpecialityName + "-" + entity.number + "</td>";
            htmlRow +="<td>" + groupType[entity.groupType] + "</td>";
            htmlRow +="<td>" + groupDegree[entity.groupDegree] + "</td>";
            htmlRow +="<td>" + courseNumbers[entity.courseNumber] + "</td><td class='curators'>";
            htmlRow +="</td><td><button id='" + entity.id + "' type='button'   class='btn btn-default btn-sm' data-toggle='modal' data-target='#curator-dialog'>Додати куратора</button></td>";
            htmlRow += "</tr>";
        });
        $("#groupCurators-table > tbody").html(htmlRow);
        createPagination('groupCuratorsPagination', response.data['totalPages'], page);
    })
        .done(function(){
        getCuratorsForGroups()
    });
}

function loadList(name, id, page, size) {
    $('#teacher-container').html("<h2 id='"+ id +"'>" + name + "</h2> <br/>");
    $.getJSON("api/teachers/search", {filter: 'notCurators', groupId: id, page: page, size: size}, function(response){
        var items = "";
        $.each(response.data.content, function(i, teach){
            items += "<input type='button' id='" + teach.id + "' class='btn btn-default col-xs-12' value = '"
                + teach.lastName + " " + teach.firstName + " " + teach.patronymic + "'/>";
        });
        $('#teacher-container').append(items);
        createPagination('groupCuratorsAddPagination', response.data['totalPages'], response.data.number);
    });
}

function getCuratorsForGroups(){
    $('#groupCurators-table > tbody  > tr').each(function() {
        loadCuratorsForGroup(this.id, this)
    });
}

$('#groupCurators-table > tbody').on('click', 'tr > td > .btn-default', function(){
    var grId = $(this).attr("id");
    var info = "Для групи " + $(this).closest('tr').children('td:first').text() + ": ";
    loadList(info, grId, 0, $('#numberOnPageList').val());
});

$('#teacher-container').on('click', 'input', function(){
    var gId = $(this).parent().children('h2:first').attr('id');
    var tId= $(this).attr('id');
    $.post( "/api/groups/" + gId + "/curators/" + tId)
        .done(function(){
            $('#curator-dialog').modal("hide");
            loadCuratorsForGroup(gId, "#groupCurators-table > tbody > #" + gId);
        })
        .fail(function() {
            alert( "Помилка!" );
        });
});


function loadCuratorsForGroup(id, $row){
    $.getJSON("/api/groups/" + id + "/curators", {page: 0, size: 10}, function(curators) {
        var htmlRow="";
        if (curators.data['totalElements'] == 0){
            htmlRow += 'не призначено';
        }
        else{
            $.each(curators.data.content, function (i, curator){
                htmlRow += "<p>" + curator.lastName + "</p>";
            });
        }
        $($row).children(".curators").html(htmlRow);
    });
};

// change number of Page in List
$('.pagination-list').on('click', 'li:not(.disabled)', function(){
    var active = $(this).siblings('.active').children('a').text();
    var number = $(this).children('a').text();
    var page = active;
    switch ($(this).children('a').attr('aria-label')) {
        case 'Previous':
            page -= 2;
            break;
        case 'Next':
            break;
        default:
            var page = number - 1;
    }
    var name = $('#teacher-container > h2').text();
    var id = $('#teacher-container > h2').attr('id');
    loadList(name, id, page, $('#numberOnPageList').val());
});

// change number of rows in table
$('#numberOnPageList').on('change', function(){
    var name = $('#teacher-container > h2').text();
    var id = $('#teacher-container > h2').attr('id');
    loadList(name, id, 0, $(this).val());
});
