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
            // if (entity.curators.length != 0){
            //     $.each(entity.curators, function (i, curator) {
            //         htmlRow += "<p>" + curator.lastName + "</p>";
            //     })
            // }
            // else {
                htmlRow += 'не призначено';
            // }
            htmlRow +="</td><td><button id='" + entity.id + "' type='button'   class='btn btn-default btn-sm' data-toggle='modal' data-target='#curator-dialog'>Додати куратора</button></td>";
            htmlRow += "</tr>";
        });
        $("#groupCurators-table > tbody").html(htmlRow);
        createPagination(response.data['totalPages'], page);
    });
}

$('#groupCurators-table > tbody').on('click', 'tr > td > .btn-default', function(){
    var grId = $(this).attr("id");
    var info = $(this).closest('tr').children('td:first').text();
    $('#teacher-container').html("<h2 id='"+ grId +"'> група " + info + "</h2> <br/>");
    $.getJSON("api/teachers/search", {curatorsOfGroupId: grId}, function(response){
        $.each(response.data.content, function(i, teach){
            var item = "<input type='button' id='" + teach.teacherEntity['id'] + "' class='btn btn-default col-xs-12' value = '"
                + teach.teacherEntity['lastName'] + " " + teach.teacherEntity['firstName'] + " " + teach.teacherEntity['patronymic'] + "'/>";
            $('#teacher-container').append(item);
        })
    });
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