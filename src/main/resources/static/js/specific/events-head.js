/**
 * Created by Trach on 12/12/2016.
 */
$(document).ready(init);

function init(){
    $("#plannedEvents").one("click", fillTable);

    $("#updateButton").click(function() {
        clearTable();
        fillTable();
    });
}

function clearTable() {
    $("#table-heads").empty();
    $("#content-table> tbody").empty();
}

function fillTable() {
    var titles = ['id', 'Тип', 'Дата початку', 'Дата завершення'];
    insertTable(titles, "content-table");
    var entityParams = ['id', 'eventType', 'beginDate', 'expirationDate'];
    fillTableFromWithLinks("content-table", API + "/events/getAll", entityParams, "eventType");
}
