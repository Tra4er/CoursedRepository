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

// Заполняет select из словаря js
function fillSelect(selectId, dictionaryJS) {
    var items = "";
    $.each(dictionaryJS, function(i, entity)
    {
        items += "<option value='" + i + "'>" + entity + "</option>";
    });
    $("#" + selectId).append(items);
}

// заполняет семестры для выбранного года
$("#yearId").on('change', function () {
    var firstString = $("#semesterId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    if ($("#yearId > option:selected").attr("value") != '0') {
        $.getJSON(API + "/years/getSemestersFromYear/" + $("#yearId > option:selected").attr("value"), function (response) {
            $.each(response, function (i, entity) {
                var sem = getSemester(entity['semesterNumber']);
                items += "<option value='" + entity.id + "'>" + sem + "</option>";
            });
            $("#semesterId").html(items);
        });
    }
    else
    {
        $("#semesterId").html(items);
    }
});