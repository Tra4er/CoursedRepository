/**
 * Created by Алена on 01.12.2016.
 */
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

$("#semesterId").on('change', function () {
    fillGroups()
});

$("#yearId").on('change', function () {
    var firstString = $("#groupId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    $("#groupIdId").html(items);
});

$('#button-student-post').click(function () {
    // if (Validate()) {
        var form = $('#registration-student-form');
        sendRegistrationAjaxPost(form, API + '/users/registration-student', 'Myform');
    // }
});

$('#button-teacher-post').click(function () {
    // if (Validate()) {
        var form = $('#registration-teacher-form');
        sendRegistrationAjaxPost(form, API + '/users/registration-teacher', 'Myform');
    // }
});

function sendRegistrationAjaxPost(element, url, modalId) {
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(element.serializeObject()),
        success: function (data) {
            // alert("Успішно");
            $('#' + modalId).modal("toggle");
            addItem(data);
        },
        error: function (data) {
            alert("Помилка!");
            console.log(data)
        }
    }).fail(function (data) { // TODO
        // if (data.responseJSON.error.indexOf("MailError") > -1) {
        //     window.location.href = "<c:url value=" / emailError.html
        //     "></c:url>";
        // }
        // else if (data.responseJSON.error.indexOf("InternalError") > -1) {
        //     window.location.href = "<c:url value=" / login.html
        //     "></c:url>" +
        //     "?message=" + data.responseJSON.message;
        // }
        // else if (data.responseJSON.error == "UserAlreadyExist") {
        //     $("#emailError").show().html(data.responseJSON.message);
        // }
        // else {
        //     var errors = $.parseJSON(data.responseJSON.message);
        //     $.each(errors, function (index, item) {
        //         $("#" + item.field + "Error").show().html(item.defaultMessage);
        //     });
        //     errors = $.parseJSON(data.responseJSON.error);
        //     $.each(errors, function (index, item) {
        //         $("#globalError").show().append(item.defaultMessage + "<br>");
        //     });
        // }
    });
}