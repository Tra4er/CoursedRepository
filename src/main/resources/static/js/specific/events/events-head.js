/**
 * Created by Trach on 12/12/2016.
 */
// $(document).ready(init);
//
// function init(){
//     $("#plannedEvents").one("click", fillTable);
//
//     $("#updateButton").click(function() {
//         clearTable();
//         fillTable();
//     });
// }
//
// function clearTable() {
//     $("#table-heads").empty();
//     $("#content-table > tbody").empty();
// }
//
// function fillTable() {
//     var titles = ['id', 'Тип', 'Дата початку', 'Дата завершення'];
//     insertTable(titles, "content-table");
//     var entityParams = ['id', 'eventType', 'beginDate', 'expirationDate'];
//     fillTableFromWithLinks("content-table", "/api/events/getAll", entityParams, "eventType");
// }

$(function () {
    var titles = ['Дата створення', 'Початок', 'Кінець', 'Тип події'];
    insertTable(titles, "events-table");
    var entityParams = ['creationDate', 'beginDate', 'expirationDate', 'eventType'];
    fillTableFrom("events-table", "/api/events/getAll", entityParams);

    settingDateTimePiker();
    fillSelect("eventType", plannedEventType);
    fillSelectYear("yearId", "/api/years/getAll");

});

function settingDateTimePiker(){
    $('#beginDate').datetimepicker({
        locale: 'uk'
    });
    $('#endDate').datetimepicker({
        useCurrent: false,
        locale: 'uk'
    });
}

$("#beginDate").on("dp.change", function (e) {
    $('#endDate').data("DateTimePicker").minDate(e.date);
});

$("#endDate").on("dp.change", function (e) {
    $('#beginDate').data("DateTimePicker").maxDate(e.date);
});

$('#button-event-post').on('click', function(){
    var obj = JSON.stringify({
        "beginDate": $("#beginDate").data("DateTimePicker").date(),
        "expirationDate": $("#endDate").data("DateTimePicker").date(),
        // "beginDate": '2016-04-08T12:30',
        // "expirationDate": '2017-04-08T12:30',
        "eventType": $('#eventType').val(),
        "semesterId": $('#semesterId').val()
    });

    $.ajax({
        type: 'POST',
        url: 'api/events/create',
        contentType: "application/json",
        data: obj,
        success: function (data) {
            $('#add-event-dialog').modal("toggle");
            // addItem(data);
        },
        error: function (data) {
            alert("Помилка!");
            console.log(data)
        }
    });
});