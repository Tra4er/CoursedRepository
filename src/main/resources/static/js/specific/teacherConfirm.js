/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['ПІБ викладача', 'Email'];
    insertTable(titles, "UnconfirmedTeachers-table");

    fillTeachersTable();
});

function fillTeachersTable(){
    $.getJSON("api/teachers/search", {filter: "unconfirmed"}, function(response){
        //Go through the each entity in the response
        $.each(response.data, function (i, entity) {
            if(entity != null) {
                var htmlRow = "<tr >";
                // htmlRow += ("<td>" + entity.teacherEntity['lastName'] + " " + entity.teacherEntity['firstName'] + " " + entity.teacherEntity['patronymic'] + "</td>");
                // htmlRow += ("<td>" + entity.email + "</td>");
                htmlRow += ("<td>" + entity.id + " " + entity.lastName + " " + entity.firstName + " " + entity.patronymic + "</td>");
                htmlRow += ("<td id=" + entity.id + "><button type='button' class='btn btn-success btn-sm'><span class='glyphicon glyphicon-ok'></span> Так</button><button type='button' class='btn btn-danger btn-sm'><span class='glyphicon glyphicon-remove'></span> Ні</button></td>");
                htmlRow += "</tr>";
                $("#UnconfirmedTeachers-table > tbody").append(htmlRow);
            }
        });
    });
}

$('#UnconfirmedTeachers-table > tbody').on('click', 'tr > td > .btn-success', function(){
    var myId = $(this).parent().attr("id");
    var thisButton = $(this).closest('tr');
    $.post( "api/teachers/" + myId + "/confirm")
        .done(function(){
            $(thisButton).remove();
        })
        .fail(function () {
            alert("Помилка!")
        });
});

$('#UnconfirmedTeachers-table > tbody').on('click', 'tr > td > .btn-danger', function(){
    var myId = $(this).parent().attr("id");
    var thisButton = $(this).closest('tr');
    $.ajax({
        type: 'DELETE',
        url: "api/teachers/" + myId,
        contentType: "application/json"})
        .done(function(){
            $(thisButton).remove();
        })
        .fail(function () {
            alert("Помилка!")
        });

});
