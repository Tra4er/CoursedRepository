/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['Номер', 'Тип', 'Рівень', 'Номер курсу', 'Куратор'];
    insertTable(titles, "groupCurators-table");
    fillCuratorsTable();
});

function fillCuratorsTable() {
    $.getJSON(API + "/groups/getAll" , function(response){
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
             htmlRow +="<td>" + entity.speciality['groupsName'] + "-" + entity.number + "</td>";
            htmlRow +="<td>" + groupType[entity.groupType] + "</td>";
            htmlRow +="<td>" + groupDegree[entity.groupDegree] + "</td>";
            htmlRow +="<td>" + courseNumbers[entity.courseNumber] + "</td><td class='curators'>";
            if (entity.curators.length != 0){
                $.each(entity.curators, function (i, curator) {
                    htmlRow += "<p>" + curator.lastName + "</p>";
                })
            }
            else {
                htmlRow += 'не призначено';
            }
            htmlRow +="</td><td><button id='" + entity.id + "' type='button'   class='btn btn-default btn-sm' data-toggle='modal' data-target='#curator-dialog'>Додати куратора</button></td>";
            htmlRow += "</tr>";
            $("#groupCurators-table > tbody").append(htmlRow);
        });
    });
}

$('#groupCurators-table > tbody').on('click', 'tr > td > .btn-default', function(){
    var grId = $(this).attr("id");
    var info = $(this).closest('tr').children('td:first').text()
    $('#teacher-container').html("<h2 id='"+ grId +"'> група " + info + "</h2> <br/>");

    $.getJSON("api/user/getAllTeachers", {groupId: grId}, function(response){
        $.each(response, function(i, teach){
            var item = "<input type='button' id='" + teach.teacherEntity['id'] + "' class='btn btn-default col-xs-12' value = '"
                + teach.teacherEntity['lastName'] + " " + teach.teacherEntity['firstName'] + " " + teach.teacherEntity['patronymic'] + "'/>";
            $('#teacher-container').append(item);
        })
    });
});

$('#teacher-container').on('click', 'input', function(){
    var gId = $(this).parent().children('h2:first').attr('id');
    var tId= $(this).attr('id');
    $.post( "api/groups/connectWithTeacher", {groupId: gId, teacherId: tId})
        .done(function(){
            $('#curator-dialog').modal("hide");
            //ToDo: update string tr
            //ToDo: Oleg get curators by groupId

        })
        .fail(function() {
            alert( "Помилка!" );
        });
});
