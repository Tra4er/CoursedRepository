/**
 * Created by Trach on 12/23/2016.
 */
var sendProblemTo;

$(document).ready(function () {
    var userProblem = $("#userProblem").text();
    switch(userProblem) {
        case 'ForgotPassword' :
            sendProblemTo = "/sendResetPasswordToken";
            break;
        case 'ResendRegistrationToken' :
            sendProblemTo = "/resendRegistrationToken";
            break;
    }

    $('#emailField-send-button').click(sendEmail);
});

function sendEmail() {
    $('#emailField-send-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/users/" + $("#emailField").val() + sendProblemTo
    }).done(function (data) {
        $('#emailField-send-button').button('reset');
        if (data.message == "success") {
            $("#emailField-send-request-server-status").text("Перевірте пошту.");
        }
    }).fail(function (data) {
        $('#emailField-send-button').button('reset');
        $("#emailField-send-request-server-status").text(data.responseJSON.message);
    });
};