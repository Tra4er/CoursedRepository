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
    sendAjaxPost(form, 'api/specialities/create');
});

