/**
 * Created by Trach on 12/26/2016.
 */

$("#passwordField-update-button").click(function () {
    $('#passwordField-update-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/users/updatePassword",
        contentType: "application/json",
        data: JSON.stringify($("#updatePassword-form").serializeObject()),
    }).done(function (data) {
        $('#passwordField-update-button').button('reset');
        if (data.message == "success") {
            $("#updatePassword-request-server-status").text("Пароль змінено.");
        }
    }).fail(function (data) {
        $('#emailField-update-button').button('reset');
        $("#emailField-update-request-server-status").text(data.responseJSON.message);
    });
})