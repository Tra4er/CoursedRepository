/**
 * Created by Hexray on 01.12.2016.
 */
const HOST = 'http://localhost:8080/';
const API = "api";

var csrf_token;
var csrf_header;

//OnDocumentReady
$(function(){
    csrf_token = $("meta[name='_csrf']").attr("content");
    csrf_header = $("meta[name='_csrf_header']").attr("content");
});

//It handles each ajax request and adds csrf token into the its header
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(csrf_header, csrf_token);
});

//It fills '#tableId' structure of <thead> with 'titleArray'
function insertTable(titleArray, tableId) {
    $.each(titleArray, function (i, title) {
        $("#" + tableId + "> thead > tr").append('<th>'+ title +'</th>');
    });
}

//It fills table's '#tableId' rows via the getting JSON from 'requestAddress' which contains selected keys in 'params'
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

//Sends JSON which was extracted and generated from 'elem'
function sendAjaxPost(element, url, modalId) {
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(element.serializeObject()),
        success: function () {
            alert("Успішно");
            $('#' + modalId).modal("toggle");
        },
        error: function (data) {
            alert("Помилка!");
            console.log(data)
        }
    });
}

//It extracts fields from the element to prepare them to be converted to the JSON
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};