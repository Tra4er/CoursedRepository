/**
 * Created by Trach on 1/2/2017.
 */
$(document).ready(function () {
    $("#sendNewRegistrationToken-button").click(sendNewVerificationToken);
});

function sendNewVerificationToken() {
    $("#sendNewRegistrationToken-button").button('loading');
    $.ajax({
        type: 'GET',
        url: "/api/users/sendNewRegistrationToken",
        data: "existingToken=" + $("#sendNewRegistrationToken-token").val() // TODO Test
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