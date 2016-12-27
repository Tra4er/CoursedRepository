/**
 * Created by Trach on 12/23/2016.
 */

$('#emailField-reset-button').click(function () {
    $('#emailField-reset-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/users/sendResetPasswordToken",
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
});