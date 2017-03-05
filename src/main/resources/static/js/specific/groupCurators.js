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
            // htmlRow +="<td>" + entity.speciality['groupsName'] + "-" + entity.number + "</td>";
            htmlRow +="<td>" + entity.number + "</td>";
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

function loadList(id, page, size) {
    $.getJSON("api/teachers/search", {filter: 'notCurators', groupId: id, page: 0, size: 10}, function(response){
        var item = "";
        $.each(response.data.content, function(i, teach){
            item += "<input type='button' id='" + teach.teacherEntity['id'] + "' class='btn btn-default col-xs-12' value = '"
                + teach.teacherEntity['lastName'] + " " + teach.teacherEntity['firstName'] + " " + teach.teacherEntity['patronymic'] + "'/>";
        });
        $('#teacher-container').html(item);
        createPagination('groupCuratorsAddPagination', response.data['totalPages'], response.data.number);
    });
}

function getCuratorsForGroups(){
    $('#groupCurators-table > tbody  > tr').each(function() {
        var $row = this;
        var id = this.id;
        $.getJSON("/api/groups/" + id + "/curators", {page: 0, size: 5}, function(curators) {
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
    });
};

$('#groupCurators-table > tbody').on('click', 'tr > td > .btn-default', function(){
    var grId = $(this).attr("id");
    var info = $(this).closest('tr').children('td:first').text();
    $('#teacher-container').html("<h2 id='"+ grId +"'> група " + info + "</h2> <br/>");
    loadList(grId, 0, $('#numberOnPageList').val());
});

$('#teacher-container').on('click', 'input', function(){
    var gId = $(this).parent().children('h2:first').attr('id');
    var tId= $(this).attr('id');
    $.post( "api/groups/" + gId + "/curators/" + tId)
        .done(function(){
            $('#curator-dialog').modal("hide");
            reloadCuratorsForGroup(gId);
        })
        .fail(function() {
            alert( "Помилка!" );
        });
});


function reloadCuratorsForGroup(grId){
    $.getJSON('api/groups/' + grId + '/curators', function(response){
            var htmlRow = "";
            if (response.data.length != 0){
                $.each( response.data, function (i, curator) {
                    htmlRow += "<p>" + curator.lastName + "</p>";
                })
            }
            else {
                htmlRow += 'не призначено';
            }
            $("#groupCurators-table > tbody > #" + grId + " > td:last").prev().html(htmlRow);
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
    var id =  $(this).next().attr('id');
    loadList(id, page, $('#numberOnPageList').val());
});
