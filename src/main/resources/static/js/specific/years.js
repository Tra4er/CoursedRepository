/**
 * Created by Hexray on 27.11.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, "content-table");

    var entityParams = ['id', 'beginYear', 'endYear'];
    fillTableFrom("content-table", API + "/years/getAll", entityParams);

});

//It sends serialized
$('#button-year-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/years/create', 'add-dialog');

});
