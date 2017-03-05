/**
 * Created by Trach on 1/2/2017.
 */
$(document).ready(function () {
});

$("#sendNewRegistrationToken-button").click(function () {
    $("#sendNewRegistrationToken-button").button('loading');
    $.ajax({
        type: 'POST',
        url: "/api/account/sendNewRegistrationToken",
        data: "existingToken=" + $("#sendNewRegistrationToken-token").text()
    }).done(function (response) {
        $("#sendNewRegistrationToken-button").button('reset');
        $("#badUser-request-result").text("Перевірте пошту, знову :)");
    }).fail(function (response) {
        $("#sendNewRegistrationToken-button").button('reset');
        $("#badUser-request-result").text("Ти знову облажався: " + response.data);
    });
});