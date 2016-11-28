/**
 * Created by Hexray on 27.11.2016.
 */
var host = "http://localhost:8080/api";


$(document).ready(function () {
    createTable('content', 'yearsTable');
    loadYears();
    createInput();
});

function createTable(mainContainer, tableId) {
    $("#" + mainContainer).append("<table id = " + tableId + " class='table table-hover'></table>");

    $("#" + tableId).append('<thead><tr><th>Рік початку</th><th>Рік завершення</th></tr></thead><tbody></tbody>');
}

function loadYears() {
    $.getJSON(host + "/years/getAll", function(data){
        $.each(data, function (i, year) {
            $("#yearsTable > tbody").append('<tr><td>' + year.beginYear + '</td>' + '<td>' + (year.beginYear + 1) + '</td></tr>');
        });
    });
}

function createInput() {

}