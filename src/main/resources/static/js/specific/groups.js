/**
 * Created by Hexray on 04.12.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Назва', 'Тип', 'Рівень', 'Номер курсу'];
    insertTable(titles, "content-table");

    var entityParams = ['id', 'number', 'groupType', 'groupDegree', 'courseNumber'];
    fillTableFrom("content-table", API + "/groups/getAll", entityParams);
});

//It sends serialized
$('#button-group-post').click(function(){
    var form = $('#modal-body');
    sendAjaxPost(form, 'api/groups/create');
});

