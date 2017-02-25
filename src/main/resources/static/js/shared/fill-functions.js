/**
 * Created by Hexray on 04.12.2016.
 */

// Fills select-list of years using getJSON
function fillSelectYear(selectId, requestAddress) {
    $.getJSON(requestAddress, function (response) {
        var firstString = $("#" + selectId + " > option:first").text();
        var htmlElements = "<option value='0'> " + firstString + "</option>";
        //Go through the each entity in the response
        $.each(response.data.content, function (i, year) {
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
        $.each(response.data.content, function (i, entity) {
            htmlElements += "<option value='" + entity.id + "'>" + entity[param] + "</option>";
        });
        $("#" + selectId).html(htmlElements);
    });
}

// Fills select using dictionary res
function fillSelect(selectId, dictionaryJS) {
    var items = "";
    $.each(dictionaryJS, function(i, entity)
    {
        items += "<option value='" + i + "'>" + entity + "</option>";
    });
    $("#" + selectId).append(items);
}

// Fills semesters for the selected year
$("#yearId").on('change', function () {
    var firstString = $("#semesterId > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    var sem = "";
    if ($("#yearId > option:selected").attr("value") != '0') {
        $.getJSON("api/years/" + $("#yearId > option:selected").attr("value") + "/semesters?page=0&size=2", function (response) {
            $.each(response.data.content, function (i, entity) {
                //ToDo that is because we fill semesters in two different pages
                if (typeof localStudentUkr != 'undefined')
                    sem = localStudent(entity.semesterNumber);
                else
                    sem = localGroup(entity.semesterNumber);
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