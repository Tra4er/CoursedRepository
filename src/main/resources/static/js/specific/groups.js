/**
 * Created by Hexray on 04.12.2016.
 */
//On document ready
$(document).ready(function () {
    var titles = ['id', 'Назва', 'Тип', 'Рівень', 'Номер курсу'];
    insertTable(titles, "content-table");
    loadTable(0, $('#numberOnPage').val());
    fillSelect("courseNumber", courseNumbers);
    fillSelect("groupType", groupType);
    fillSelect("groupDegree", groupDegree);
    fillSelectYear("yearId", "/api/years?page=0&size=10");
    fillSelectFrom("specialityId", "/api/specialities?page=0&size=10", "fullName");
});

function loadTable(page, size){
    var entityParams = ['id', 'number', 'groupType', 'groupDegree', 'courseNumber'];
    fillLocalizedTableFrom("content-table", "/api/groups?page=" + page + "&size=" + size, entityParams,
        localGroupUkr, page);
};

//AJAX post to create a group
// $('#button-group-post').click(function(){
//     var form = $('#modal-body-form');
//     sendAjaxPost(form, '/api/groups', 'add-dialog');
// });


$('#button-group-post').on('click', function () {
    var obj = JSON.stringify({
        "number": $('#number').val(),
        "groupType": $('#groupType').val(),
        "groupDegree": $('#groupDegree').val(),
        "courseNumber": $('#courseNumber').val(),
        "semesterId": $('#semesterId').val(),
        "specialityId": $('#specialityId').val()
    });

    $.ajax({
        type: "POST",
        url: "/api/groups",
        contentType: "application/json",
        data: obj,
        success: function (response) {
            $('#add-dialog').modal("toggle");
            var active = $('.pagination').children('.active').children('a').text() - 1;
            loadTable(active, $('#numberOnPage').val());
        },
        error: function (response) {
            alert('Помилка!');
            console.log(response)
        }
    });
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