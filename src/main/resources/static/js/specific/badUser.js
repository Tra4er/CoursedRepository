/**
 * Created by Trach on 1/2/2017.
 */
$(document).ready(function () {
    $("#resendRegistrationToken-button").click(resendVerificationToken);
});

function resendVerificationToken() {
    $("#resendRegistrationToken-button").button('loading');
    $.ajax({
        type: 'GET',
        url: "/api/users/resendRegistrationToken",
        data: "existingToken=" + $("#resendRegistrationToken-token").val()
    }).done(function (data) {
        $("#resendRegistrationToken-button").button('reset');
        if (data.message == "success") {
            $("#badUser-request-result").text("Перевірте пошту, знову :)");
        }
    }).fail(function (data) {
        $('#resendRegistrationToken-button').button('reset');
        $("#badUser-request-result").text("Ти знову облажався: " + data.responseJSON.message);
    });
};