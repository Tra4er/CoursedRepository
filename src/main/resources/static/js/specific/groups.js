/**
 * Created by Hexray on 04.12.2016.
 */
//On document ready
$(document).ready(function () {
    var titles = ['Група', 'Тип', 'Рівень', 'Номер курсу'];
    insertTable(titles, "content-table");
    loadTable(0, $('#numberOnPage').val());
    fillSelect("courseNumber", courseNumbers);
    fillSelect("groupType", groupType);
    fillSelect("groupDegree", groupDegree);
    fillSelectYear("yearId", "/api/years?page=0&size=10");
    fillSelectFrom("specialityId", "/api/specialities?page=0&size=10", "fullName");
});

function loadTable(page, size){
    $.getJSON("/api/groups" ,{page: page, size: size} ,function(response){
        var htmlRow = "";
        $.each(response.data.content, function (i, entity) {
            htmlRow +="<tr><td>" + entity.shortSpecialityName + "-" + entity.number + "</td>";
            htmlRow +="<td>" + groupType[entity.groupType] + "</td>";
            htmlRow +="<td>" + groupDegree[entity.groupDegree] + "</td>";
            htmlRow +="<td>" + courseNumbers[entity.courseNumber] + "</td></tr>";
        });
        $("#content-table > tbody").html(htmlRow);
        createPagination('groupsPagination', response.data['totalPages'], page);
    })
};

// AJAX post to create a group
$('#button-group-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, '/api/groups', 'add-dialog');
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