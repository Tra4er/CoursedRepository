/**
 * Created by Алена on 01.12.2016.
 */

var person;

// get person
$("#registration-student").click(function () {
    person = "student";
});
$("#registration-teacher").click(function () {
    person = "teacher";
});

$("#registration-student").on('click', function () {
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");
    fillSelect("studentEducationStatus", studentEducationStatus)
});

// заполняет группы для выбранных семестра и специальности
function fillGroups() {
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";

    if ($("#specialityId > option:selected").attr("value") != '0'
        && $("#semesterId > option:selected").attr("value") != '0') {
        $.getJSON(API + "/groups/getAll/", {
            specialityId: $("#specialityId > option:selected").attr("value"),
            semesterId: $("#semesterId > option:selected").attr("value")
        }, function (response) {
            $.each(response, function (i, entity) {
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

$("#semesterId").on('change', function () { // TODO should not do this on every swap
    fillGroups()
});

$("#yearId").on('change', function () {
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    $("#groupIdId").html(items);
});

$('#button-student-post').click(function () {
    // if (Validate()) {
        var sendButton = $('#button-student-post');
        var form = $('#registration-student-form');
        var result = $("#student-request-server-status");
        sendButton.button('loading');
        sendRegistrationAjaxPost(form, API + '/users/registration-student', 'Myform', result, sendButton);
    // }
});

$('#button-teacher-post').click(function () {
    // if (Validate()) {
        var sendButton = $('#button-teacher-post');
        var form = $('#registration-teacher-form');
        var result = $("#teacher-request-server-status");
        sendButton.button('loading');
        sendRegistrationAjaxPost(form, API + '/users/registration-teacher', 'Myform', result, sendButton);
    // }
});

function sendRegistrationAjaxPost(element, url, modalId, resultElement, sendButton) {
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(element.serializeObject()),
        success: function (data) {
            // alert("Успішно");
            // $('#' + modalId).modal("toggle");
        },
        error: function (data) {
            // alert("Помилка!");
            // console.log(data)
        }
    }).done(function (data) {
        $('#' + modalId).modal("toggle");
        if (data.message == "success") {
            sendButton.button('reset');
            $("#userEmail-modal").text($("#emailField-" + person).val());
            $("#userEmail-modal").href = $("#emailField-" + person).val();
            $("#emailSentMessage").modal({backdrop: "static", keyboard : false});
        }
    }).fail(function (data) {
        resultElement.text(data.responseJSON.message);
        sendButton.button('reset');
    });
}