/**
 * Created by alena on 25.12.2016.
 */

$(function () {
    alert($.urlParam('eventId'));

});

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    else {
        return results[1] || 0;
    }
};
