/**
 * Created by alena on 25.12.2016.
 */

function dateToString(dateISO){
    var mscUTC = Date.parse(dateISO);
    var dateObj = new Date(mscUTC);
    var options = {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
    };
    return dateObj.toLocaleString("uk", options);
}

function isActuallyEvent(start, end) {
    var startUTC = Date.parse(start);
    var endUTC = Date.parse(end);
    var nowUTC = Date.now();
    if (startUTC < nowUTC && endUTC < nowUTC) return 'danger';
    else if (startUTC < nowUTC && endUTC > nowUTC) return 'success';
    else return 'info';
}
