/**
 * Created by Алена on 01.12.2016.
 */
$(function () {
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");
    fillSelect("studentEducationStatus", studentEducationStatus)
});

// заполняет группы для выбранных семестра и специальности
function fillGroups(){
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";

    if ($("#specialityId > option:selected").attr("value") != '0'
    && $("#semesterId > option:selected").attr("value") != '0') {
        $.getJSON(API + "/groups/getAll/", {specialityId: $("#specialityId > option:selected").attr("value"), semesterId: $("#semesterId > option:selected").attr("value")}, function (response) {
            $.each(response, function (i, entity) {
                items += "<option value='" + entity.id + "'>" + entity.number + "</option>";
            });
            $("#groupId").html(items);
        });
    }
    else
    {
        $("#groupId").html(items);
    }
};

$("#specialityId").on('change', function () {
    fillGroups()
});

$("#semesterId").on('change', function () {
    fillGroups()
});

$('#button-student-post').click(function(){
    var form = $('#form-student');
    sendAjaxPost(form, 'api/user/createStudent', 'Myform');
});