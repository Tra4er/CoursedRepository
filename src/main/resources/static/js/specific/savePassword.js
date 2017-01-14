/**
 * Created by Trach on 12/30/2016.
 */
$(document).ready(function () {
});

$("#passwordField-save-button").click(function () {
    $('#passwordField-save-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/account/savePassword",
        contentType: "application/json",
        data: JSON.stringify($("#savePassword-form").serializeObject()),
    }).done(function (response) {
        $('#passwordField-save-button').button('reset');
        if (response.status == "success") {
            $("#savePassword-request-server-status").text("Пароль змінено.");
        }
    }).fail(function (response) {
        $('#emailField-save-button').button('reset');
        $("#emailField-save-request-server-status").text(response.data);
    });
})