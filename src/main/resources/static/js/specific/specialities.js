/**
 * Created by Hexray on 01.12.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Повна назва', 'Скорочена назва для груп'];
    insertTable(titles, "content-table");
    loadTable(0,$('#numberOnPage').val());
});

function loadTable(page, size){
    var entityParams = ['id', 'fullName', 'groupsName'];
    fillTableFrom("content-table", "/api/specialities?page=" + page + "&size=" + size, entityParams, page, 'specialitiesPagination');
};

//It sends serialized
$('#button-spec-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/specialities', 'add-dialog');
});

$('#add-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
});