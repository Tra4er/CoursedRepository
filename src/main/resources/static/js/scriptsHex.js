/**
 * Created by Hexray on 27.11.2016.
 */
const HOST = 'http://localhost:8080/';
const API = HOST + "api";

$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, HOST + "html/frontLayout.html", "contentTable");

    var entityParams = ['id', 'beginYear', 'endYear'];
    fillTableFrom("contentTable", API + "/years/getAll", entityParams);
});

$("#buttonYearPost").click(function () {


    $.post(API + "/years/create",
        {
            yearBegin: $("#yearBegin").val(),
            yearEnd: $("#yearEnd").val()
        });
});

//Loads '#tableId' structure from 'address', puts it into the 'mainContainer' and fills its <thead> with 'titleArray'
function insertTable(titleArray, address, tableId) {
    $.each(titleArray, function (i, title) {
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

