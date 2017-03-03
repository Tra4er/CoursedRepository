/**
 * Created by Алена on 01.12.2016.
 */

$(document).ready(function () {
    fillSelect("studentEducationStatus", studentEducationStatus);
});

var person;

// get person
$("#registration-student").click(function () {
    person = "student";
});
$("#registration-teacher").click(function () {
    person = "teacher";
});

$("#registration-student").on('click', function () {
    fillSelectYear("yearId", "/api/years?page=0&size=6");
    fillSelectFrom("specialityId", "/api/specialities?page=0&size=10", "fullName");
});

// заполняет группы для выбранных семестра и специальности
function fillGroups() {
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";

    if ($("#specialityId > option:selected").attr("value") != '0'
        && $("#semesterId > option:selected").attr("value") != '0') {
        $.getJSON("/api/groups?page=0&size=50", {
            specialityId: $("#specialityId > option:selected").attr("value"),
            semesterId: $("#semesterId > option:selected").attr("value")
        }, function (response) {
            $.each(response.data.content, function (i, entity) {
                var myVar = "";
                if (entity.groupType == 'DISTANCE_FORM') myVar = " (заочна)";
                items += "<option value='" + entity.id + "'>" + entity.number + myVar + "</option>";
            });
            $("#groupId").html(items);
        });
    }
    else {
        $("#groupId").html(items);
    }
};

$("#specialityId").on('change', function () {
    fillGroups()
});

$("#semesterId").on('change', function () {
    fillGroups()
});

$("#yearId").on('change', function () {
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    $("#groupId").html(items);
});

$('#button-student-post').click(function () {
    if (Validate()) {
        var sendButton = $('#button-student-post');
        var form = $('#registration-student-form');
        var result = $("#student-request-server-status");
        sendButton.button('loading');
        sendRegistrationAjaxPost(form, '/api/students', 'Myform', result, sendButton);
    }
});

$('#button-teacher-post').click(function () {
    if (Validate()) {
        var sendButton = $('#button-teacher-post');
        var form = $('#registration-teacher-form');
        var result = $("#teacher-request-server-status");
        sendButton.button('loading');
        sendRegistrationAjaxPost(form, '/api/teachers', 'Myform', result, sendButton);
    }
});

function sendRegistrationAjaxPost(element, url, modalId, resultElement, sendButton) {
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(element.serializeObject()),
    }).done(function (response) {
        $('#' + modalId).modal("toggle");
        if (response.status == "success") {
            sendButton.button('reset');
            $("#userEmail-modal").text($("#emailField-" + person).val());
            $("#userEmail-modal").attr("href", "mailto:" + $("#emailField-" + person).val());
            $("#emailSentMessage").modal({backdrop: "static", keyboard: false});
        }
    }).fail(function (response) {
        grecaptcha.reset();
        if (response.message == "InvalidReCaptcha") {
            $("#captchaError").show().html(response.responseJSON.data);
        }
        resultElement.text(response.responseJSON.data + " / " + response.responseJSON.message);
        sendButton.button('reset');
    });
}