/**
 * Created by Trach on 1/2/2017.
 */
$(document).ready(function () {
    $("#sendNewRegistrationToken-button").click(sendNewVerificationToken);
});

function sendNewVerificationToken() {
    $("#sendNewRegistrationToken-button").button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/users/" + $("#username").text() + "/sendNewRegistrationToken?existingToken=" + $("#sendNewRegistrationToken-token").text()
    }).done(function (data) {
        $("#sendNewRegistrationToken-button").button('reset');
        if (data.message == "success") {
            $("#badUser-request-result").text("Перевірте пошту, знову :)");
        }
    }).fail(function (data) {
        $('#sendNewRegistrationToken-button').button('reset');
        $("#badUser-request-result").text("Ти знову облажався: " + data.responseJSON.message);
    });
};