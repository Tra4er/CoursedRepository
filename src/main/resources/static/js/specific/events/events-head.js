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
    var titles = ['Подія', 'Дата створення', 'Початок', 'Кінець',];
    insertTable(titles, "events-table");
    //var entityParams = ['creationDate', 'beginDate', 'expirationDate', 'eventType'];
    // fillLocalizedTableFrom("events-table", API + "/events/getAll", entityParams, plannedEventType);
    fillTablePlannedEvent();

    settingDateTimePiker();
    fillSelect("eventType", plannedEventType);
    fillSelectYear("yearId", "/api/years/getAll");

});

function fillTablePlannedEvent() {
    var $tableBody = $("#events-table > tbody");
    $.getJSON("api/events/getAll", function (response) {
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
            htmlRow += ("<td><button id='" + entity.id + "' type='button' class='btn btn-" +
            isActuallyEvent(entity.beginDate, entity.expirationDate) +
            " btn-sm col-xs-12' data-toggle='modal' data-target='#events-groups-dialog'>" +
            plannedEventType[entity.eventType] + "</button></td>");
            htmlRow += ("<td>" + dateToString(entity.creationDate) + "</td>");
            htmlRow += ("<td>" + dateToString(entity.beginDate) + "</td>");
            htmlRow += ("<td>" + dateToString(entity.expirationDate) + "</td>");
            htmlRow += "</tr>";
            $tableBody.append(htmlRow);
        });
    });
}

function settingDateTimePiker() {
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

$('#button-event-post').on('click', function () {
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