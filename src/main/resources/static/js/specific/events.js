/**
 * Created by Trach on 12/10/2016.
 */
$(document).ready(init);

function init(){
    var titles = ['id', 'Дата початку', 'Дата завершення', 'Тип'];
    insertTable(titles, "content-table");

    fillTable();
}
function fillTable(){
    var entityParams = ['id', 'beginDate', 'expirationDate', 'eventType'];
    fillTableFrom("content-table", API + "/events/getAll", entityParams);
}