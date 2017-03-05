/**
 * Created by Hexray on 27.11.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, "content-table");
    loadTable(0,$('#numberOnPage').val());
});

function loadTable(page, size){
    var params = ['id', 'beginYear', 'endYear'];
    fillTableFrom("content-table", "/api/years?page=" + page + "&size=" + size, params, page, 'yearsPagination');
};

//It sends serialized
$('#button-year-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, '/api/years', 'add-dialog');
});

// очистка формы при ее закрытии
$('#add-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
});
