/**
 * Created by Trach on 12/18/2016.
 */
$(document).ready(init);

function init(){
    fillEventTable();
}

function fillEventTable() {
    var titles = ['id', 'Тип', 'Дата початку', 'Дата завершення'];
    insertTable(titles, "content-table-event");
    var entityParams = ['id', 'eventType', 'beginDate', 'expirationDate'];
    fillTableFrom("content-table-event", API + "/events/getEvent?eventId=" + $("#eventId").text(), entityParams);
}