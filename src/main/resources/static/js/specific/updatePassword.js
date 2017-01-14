/**
 * Created by Trach on 12/26/2016.
 */
$(document).ready(function () {
});

$("#passwordField-update-button").click(function () {
    $('#passwordField-update-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/account/updatePassword",
        contentType: "application/json",
        data: JSON.stringify($("#updatePassword-form").serializeObject()),
    }).done(function (response) {
        $('#passwordField-update-button').button('reset');
        if (response.status == "success") {
            $("#updatePassword-request-server-status").text("Пароль змінено.");
        }
    }).fail(function (response) {
        $('#emailField-update-button').button('reset');
        $("#emailField-update-request-server-status").text(response.data);
    });
})