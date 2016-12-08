/**
 * Created by Hexray on 04.12.2016.
 */
//On document ready
$(document).ready(function () {
    var titles = ['id', 'Назва', 'Тип', 'Рівень', 'Номер курсу'];
    insertTable(titles, "content-table");

    var entityParams = ['id', 'number', 'groupType', 'groupDegree', 'courseNumber'];
    fillLocalizedTableFrom("content-table", API + "/groups/getAll", entityParams, localGroupUkr);

    fillSelect("courseNumber", courseNumbers);
    fillSelect("groupType", groupType);
    fillSelect("groupDegree", groupDegree);
});

//AJAX post to create a group
$('#button-group-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/groups/create', 'add-dialog');
});

//When the modal shows
$('#add-dialog').on('show.bs.modal', function() {
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");

    var items = "<option value='0'> " + $("#semesterId > option:first").text() + "</option>";
    $("#semesterId").html(items);
});

//ToDo: обнулить значения
//When the modal hides
$('#add-dialog').on('hidden.bs.modal', function() {
    $('#modal-body-form').each(function(i,elem){
        var id = $(elem[i]).attr( "id");
        $('#' + id).val('0');
    });
});

