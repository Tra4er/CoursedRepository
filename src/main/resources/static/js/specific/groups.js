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
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");

});

//AJAX post to create a group
$('#button-group-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/groups/create', 'add-dialog');
});

//When the modal hides set to dafault selects and inputs
$('#add-dialog').on('hidden.bs.modal', function() {
    var items = "<option value='0'> " + $("#semesterId > option:first").text() + "</option>";
    $("#semesterId").html(items);
    $('#modal-body-form').children('select').each(function(i,elem){
        $(elem).val('0');
    });
    $('#modal-body-form').find('input[name=number]').val('');
});

function addItem(item){
    var params = ['id', 'number', 'groupType', 'groupDegree', 'courseNumber'];
    var htmlRow = "<tr>";
    $.each(item, function (paramName, paramValue) {
        if($.inArray(paramName, params) !== -1) {
            if (typeof localGroupUkr[paramValue] != 'undefined')
            {
                htmlRow += ("<td>" + localGroupUkr[paramValue] + "</td>");
            }
            else
            {
                htmlRow += ("<td>" + paramValue + "</td>");
            }
        }
    });
    htmlRow += "</tr>";
    $("#content-table > tbody").append(htmlRow);
}