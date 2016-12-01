/**
 * Created by Алена on 01.12.2016.
 */
$(function () {
    fillSelectYear("YearId", API + "/years/getAll");

    fillSelectFrom("SpecialityId", API + "/specialities/getAll", "fullName");
});

function fillSelectYear(selectId, requestAddress) {
    $.getJSON(requestAddress, function(response){
        var htmlElements = "<option value='0'>Обрати рік</option>";
        //Go through the each entity in the response
        $.each(response, function (i, year) {
            htmlElements += "<option value='" + year.id + "'>" + year.beginYear + "-" + year.endYear + "</option>";
            });
            $("#" + selectId).html(htmlElements);
        });
    };

function fillSelectFrom(selectId, requestAddress, param) {
    $.getJSON(requestAddress, function(response){
        var firstString = $("#" + selectId + ">option:first).text");
        var htmlElements = "<option value='0'>firstString</option>";
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            htmlElements += "<option value='" + entity.id + "'>" + entity.param +"</option>";
        });
        $("#" + selectId).html(htmlElements);
    });
};

function fillSelectFrom2(selectId, requestAddress, params) {
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
