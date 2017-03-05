/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['ПІБ викладача', 'Email'];
    insertTable(titles, "UnconfirmedTeachers-table");
    loadTable(0, $('#numberOnPage').val());
});

function loadTable(page, size){
    $.getJSON("api/teachers/search", {page: page, size: size, filter: "unconfirmed"}, function(response){
        var htmlRow = "";
        $.each(response.data.content, function (i, entity) {
            if(entity != null) {
                htmlRow += "<tr >";
                htmlRow += ("<td>" + entity.id + " " + entity.lastName + " " + entity.firstName + " " + entity.patronymic + "</td>");
                htmlRow += ("<td id=" + entity.id + "><button type='button' class='btn btn-success btn-sm'><span class='glyphicon glyphicon-ok'></span> Так</button><button type='button' class='btn btn-danger btn-sm'><span class='glyphicon glyphicon-remove'></span> Ні</button></td>");
                htmlRow += "</tr>";
            }
        });
        $("#UnconfirmedTeachers-table > tbody").html(htmlRow);
        createPagination('unconfirmedTeachersPagination', response.data['totalPages'], page);
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
