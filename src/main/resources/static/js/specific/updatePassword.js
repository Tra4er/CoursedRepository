/**
 * Created by Trach on 12/26/2016.
 */

$("#passwordField-reset-button").click(function () {
    $('#passwordField-reset-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/users/savePassword",
        contentType: "application/json",
        data: JSON.stringify($("#updatePassword-form").serializeObject()),
    }).done(function (data) {
        $('#passwordField-reset-button').button('reset');
        if (data.message == "success") {
            $("#resetPassword-request-server-status").text("Пароль змінено.");
        }
    }).fail(function (data) {
        $('#emailField-reset-button').button('reset');
        $("#emailField-reset-request-server-status").text(data.responseJSON.message);
    });
})