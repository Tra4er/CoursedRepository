/**
 * Created by Hexray on 04.12.2016.
 */

// Fills select-list of years using getJSON
function fillSelectYear(selectId, requestAddress) {
    $.getJSON(requestAddress, function (response) {
        var firstString = $("#" + selectId + " > option:first").text();
        var htmlElements = "<option value='0'> " + firstString + "</option>";
        //Go through the each entity in the response
        $.each(response.data, function (i, year) {
            htmlElements += "<option value='" + year.id + "'>" + year.beginYear + "-" + year.endYear + "</option>";
        });
        $("#" + selectId).html(htmlElements);
    });

}

// markedColumn is a column in which text will be links
// Also you must add in 'params' "id"!!!
function fillTableFromWithLinks(tableId, requestAddress, params, markedColumn) { // TODO mess with order!!!
    $.getJSON(requestAddress, function(response){
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
            var id;
            //Go through the each parameter in the entity
            $.each(entity, function (paramName, paramValue) {
                //If a parameter belongs to params array(argument of function)
                if($.inArray(paramName, params) !== -1) {
                    //Then we add the value of this parameter to the row
                    if(paramName === "id") {
                        id = paramValue;
                    }
                    if(paramName === markedColumn) {
                        paramValue = "<a href='http://localhost:8080/events/" + id + "'>" + paramValue + "</a>";
                    }
                    htmlRow += ("<td>" + paramValue + "</td>");
                }
            });
            htmlRow += "</tr>";
            $("#" + tableId + "> tbody").append(htmlRow);
        });
    });
}

// Fills select-list 'selectId' using getJSON and using alias 'param'
function fillSelectFrom(selectId, requestAddress, param) {
    $.getJSON(requestAddress, function (response) {
        var firstString = $("#" + selectId + " > option:first").text();
        var htmlElements = "<option value='0'> " + firstString + "</option>";
        //Go through the each entity in the response
        $.each(response.data, function (i, entity) {
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
        $.getJSON("api/years/getSemestersFromYear/" + $("#yearId > option:selected").attr("value"), function (response) {
            $.each(response, function (i, entity) {
                //ToDo that is because we fill semesters in two different pages
                if (typeof localStudentUkr != 'undefined')
                    sem = localStudent(entity['semesterNumber']);
                else
                    sem = localGroup(entity['semesterNumber']);
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