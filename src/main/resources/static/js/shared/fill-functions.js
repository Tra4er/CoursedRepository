/**
 * Created by Hexray on 04.12.2016.
 */

// Fills select-list of years using getJSON
function fillSelectYear(selectId, requestAddress) {
    $.getJSON(requestAddress, function (response) {
        var firstString = $("#" + selectId + " > option:first").text();
        var htmlElements = "<option value='0'> " + firstString + "</option>";
        //Go through the each entity in the response
        $.each(response, function (i, year) {
            htmlElements += "<option value='" + year.id + "'>" + year.beginYear + "-" + year.endYear + "</option>";
        });
        $("#" + selectId).html(htmlElements);
    });
}

// Fills select-list 'selectId' using getJSON and using alias 'param'
function fillSelectFrom(selectId, requestAddress, param) {
    $.getJSON(requestAddress, function (response) {
        var firstString = $("#" + selectId + " > option:first").text();
        var htmlElements = "<option value='0'> " + firstString + "</option>";
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            htmlElements += "<option value='" + entity.id + "'>" + entity[param] + "</option>";
        });
        $("#" + selectId).html(htmlElements);
    });
}