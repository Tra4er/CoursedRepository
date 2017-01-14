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
});

$('#emailField-send-button').click(function () {
    $('#emailField-send-button').button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/account" + sendProblemTo,
        data: "email=" + $("#emailField").val()
    }).done(function (response) {
        $('#emailField-send-button').button('reset');
        if (response.status == "success") {
            $("#emailField-send-request-server-status").text("Перевірте пошту.");
        }
    }).fail(function (response) {
        $('#emailField-send-button').button('reset');
        $("#emailField-send-request-server-status").text(response.data);
    });
});