/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['ПІБ викладача', 'Email'];
    insertTable(titles, "UnconfirmedTeachers-table");

    fillTeachersTable();
});

function fillTeachersTable(){
    $.getJSON("api/user/getAllUnconfirmedTeachers", function(response){
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            var htmlRow = "<tr >";
            htmlRow += ("<td>" + entity.teacherEntity['lastName']+ " " + entity.teacherEntity['firstName'] +" " + entity.teacherEntity['patronymic'] +"</td>");
            htmlRow += ("<td>" + entity.email + "</td>");
            htmlRow += ("<td id=" + entity.id + "><button type='button' class='btn btn-success btn-sm'><span class='glyphicon glyphicon-ok'></span> Так</button><button type='button' class='btn btn-danger btn-sm'><span class='glyphicon glyphicon-remove'></span> Ні</button></td>");
            htmlRow += "</tr>";
            $("#UnconfirmedTeachers-table > tbody").append(htmlRow);
        });
    });
}

$('#UnconfirmedTeachers-table > tbody').on('click', 'tr > td > .btn-success', function(){
    var myId = $(this).parent().attr("id");
    $.post( "api/user/confirm-teacher", {userId: myId}).done(function(){
        //ToDO: test
        $(this).closest('tr').remove();
        }
    );

});
$('#UnconfirmedTeachers-table > tbody').on('click', 'tr > td > .btn-danger', function(){
    alert('Видалення користувачів на стадії розробки');
});
