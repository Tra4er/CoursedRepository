/**
 * Created by Hexray on 01.12.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Повна назва', 'Скорочена назва для груп'];
    insertTable(titles, "content-table");

    var entityParams = ['id', 'fullName', 'groupsName'];
    fillTableFrom("content-table", API + "/specialities/getAll", entityParams);
});

//It sends serialized
$('#button-spec-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/specialities/create', 'add-dialog');
});

function addItem(item){
    var params = ['id', 'fullName', 'groupsName'];
    var htmlRow = "<tr>";
    $.each(item, function (paramName, paramValue) {
        htmlRow += ("<td>" + paramValue + "</td>");
    });
    htmlRow += "</tr>";
    $("#content-table > tbody").append(htmlRow);
}

$('#add-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
});