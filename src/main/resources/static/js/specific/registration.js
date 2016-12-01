/**
 * Created by Алена on 01.12.2016.
 */
$(function () {
    fillSelectYear("YearId", API + "/years/getAll");
    fillSelectFrom("SpecialityId", API + "/specialities/getAll", "fullName");
});

// Заполнить выпадающи список годов
function fillSelectYear(selectId, requestAddress) {
    $.getJSON(requestAddress, function (response) {
        var htmlElements = "<option value='0'>Обрати рік</option>";
        //Go through the each entity in the response
        $.each(response, function (i, year) {
            htmlElements += "<option value='" + year.id + "'>" + year.beginYear + "-" + year.endYear + "</option>";
        });
        $("#" + selectId).html(htmlElements);
    });
}

// заполнить выпадающий список на основе полученных данных
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