/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['ПІБ', 'Email', ''];
    insertTable(titles, "content-table");

    fillTeachersTable();
});

function fillTeachersTable(tableId, requestAddress, params){
    $.getJSON("api/teacher/users", function(response){
        //Go through the each entity in the response
        $.each(response, function (i, teacher) {
            var htmlRow = "<tr>";
            htmlRow += ("<td>" + teacer['lastName'] + "</td>");
            htmlRow += ("<td>" + teacer['firstName'] + "</td>");
            htmlRow += ("<td><button type='button' class='btn btn-default btn-lg'><span class='glyphicon glyphicon-ok' aria-hidden='true'></span></button></td>");
            htmlRow += "</tr>";
            $("#content-table > tbody").append(htmlRow);
        });
    });
}