/**
 * Created by Hexray on 27.11.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, "content-table");
    var entityParams = ['id', 'beginYear', 'endYear'];
    fillTableFrom("content-table", "/api/years", entityParams);
});

//It sends serialized
$('#button-year-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, '/api/years', 'add-dialog');
});

// очистка формы при ее закрытии
$('#add-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
});

function addItem(item){
    var htmlRow = "<tr><td>" + item.id + "</td><td>" + item.beginYear + "</td><td>" + item.endYear + "</td></tr>";
    $("#content-table > tbody").append(htmlRow);
}