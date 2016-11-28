/**
 * Created by Hexray on 27.11.2016.
 */
var host = "http://localhost:8080/api";



$(document).ready(function () {
    var titles = ['Рік початку', 'Рік завершення'];
    createTable('content', 'yearsTable', titles);

    var entityParams = ['beginYear', 'endYear'];
    fillTableFrom('yearsTable', host + "/years/getAll", entityParams);

    createInput();
});

function createTable(mainContainer, tableId, titleArray) {
    $("#" + mainContainer).append("<table id = " + tableId + " class='table table-hover'></table>");

    $("#" + tableId).append('<thead><tr></tr></thead><tbody></tbody>');

    $.each(titleArray, function (i, title) {
        //TODO: check if it really works correct
        $("#" + tableId + "> thead > tr").append('<th>'+ title +'</th>');
    });

}

function fillTableFrom(tableId, requestAddress, params) {
    $.getJSON(requestAddress, function(response){
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
            //Go through the each parameter in the entity
            $.each(entity, function (paramName, paramValue) {
                //If a parameter belongs to params array(argument of function)
                if($.inArray(paramName, params) !== -1) {
                    //Then we add the value of this parameter to the row
                    htmlRow += ("<td>" + paramValue + "</td>");
                }
            });
            htmlRow += "</tr>";
            $("#" + tableId + "> tbody").append(htmlRow);
        });
    });
}

function createInput() {

}