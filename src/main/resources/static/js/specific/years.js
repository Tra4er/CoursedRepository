/**
 * Created by Hexray on 27.11.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, "content-table");

    fillTable();
});
function fillTable(){
    var entityParams = ['id', 'beginYear', 'endYear'];
    fillTableFrom("content-table", API + "/years/getAll", entityParams);
}
//It sends serialized
$('#button-year-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/years/create', 'add-dialog');

});

$('#add-dialog').on('hide.bs.modal', function(){
    $("#tbodyId").empty();
    fillTable();
});