/**
 * Created by Trach on 12/23/2016.
 */
$(document).ready(function () {
    $('#emailField-reset-button').click(function () {
        $('#emailField-reset-button').button('loading');
        sendResetAjax();
    });
});

function sendResetAjax() {
    $.ajax({
        type: 'POST',
        url: API + "/users/resetPassword",
        data: "email=" + $("#emailField-reset").val()
    }).done(function (data) {
        $('#emailField-reset-button').button('reset');
        if (data.message == "success") {
            $("#emailField-reset-request-server-status").text("Перевірте пошту.");
        }
    }).fail(function (data) {
        $('#emailField-reset-button').button('reset');
        $("#emailField-reset-request-server-status").text(data.responseJSON.message);
    });
};