/**
 * Created by Hexray on 01.12.2016.
 */
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
function fillTableFrom(tableId, requestAddress, params, page, ulId) {
    $.getJSON(requestAddress, function(response){
        var htmlRow = "";
        //Go through the each entity in the response
        $.each(response.data.content, function (i, entity) {
            htmlRow += "<tr>";
            //Go through the each parameter in the entity
            $.each(entity, function (paramName, paramValue) {
                //If a parameter belongs to params array(argument of function)
                if($.inArray(paramName, params) !== -1) {
                    //Then we add the value of this parameter to the row
                    htmlRow += ("<td>" + paramValue + "</td>");
                }
            });
            htmlRow += "</tr>";

        });
        $("#" + tableId + "> tbody").html(htmlRow);
        createPagination(ulId, response.data['totalPages'], page);
    });
}

//It fills table's '#tableId' rows via the getting JSON from 'requestAddress' which contains selected keys in 'params'
//Parameter 'local' is a map which is responsible for localization of paramValue parameters
function fillLocalizedTableFrom(tableId, requestAddress, params, local, page, ulId) {
    $.getJSON(requestAddress, function(response){
        var htmlRow = "";
        //Go through the each entity in the response
        $.each(response.data.content, function (i, entity) {
            htmlRow += "<tr>";
            //Go through the each parameter in the entity
            $.each(entity, function (paramName, paramValue) {
                //If a parameter belongs to params array(argument of function)
                if($.inArray(paramName, params) !== -1) {
                    //Then we add the value of this parameter to the row
                    if (typeof local[paramValue] != 'undefined')
                    {
                        htmlRow += ("<td>" + local[paramValue] + "</td>");
                    }
                    else
                    {
                        htmlRow += ("<td>" + paramValue + "</td>");
                    }
                }
            });
            htmlRow += "</tr>";
            createPagination(ulId, response.data['totalPages'], page);
        });
        $("#" + tableId + "> tbody").html(htmlRow);
    });
}

//Sends JSON which was extracted and generated from 'elem'. In success case closes a modal window
function sendAjaxPost(element, url, modalId) {
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: JSON.stringify(element.serializeObject()),
        success: function (data) {
            $('#' + modalId).modal("toggle");
            // reload table for active page
            var active = $('.pagination').children('.active').children('a').text() - 1;
            loadTable(active, $('#numberOnPage').val());
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
    var obj = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (obj[this.name] !== undefined) {
            if (!obj[this.name].push) {
                obj[this.name] = [obj[this.name]];
            }
            obj[this.name].push(this.value || '');
        } else {
            obj[this.name] = this.value || '';
        }
    });
    return obj;
};


// insert pagination list
function createPagination(ulId, totalPages, page){
    var item = '<li';
    if (page == 0) item += ' class="disabled"';
    item += '><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>';
    for (var i = 0; i < totalPages; i++) {
        if (i == page) item += '<li class="active"><a href="#">' + (i + 1) + '</a></li>';
        else item += '<li><a href="#">' + (i + 1) + '</a></li>';
    }
    item += '<li';
    if(totalPages - 1 == page) item += ' class="disabled"';
    item += '><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>';
    $('#' + ulId).html(item);
};

// change number of Page in Table
$('.pagination-table').on('click', 'li:not(.disabled)', function(){
    var active = $(this).siblings('.active').children('a').text();
    var number = $(this).children('a').text();
    var page = active;
    switch ($(this).children('a').attr('aria-label')) {
        case 'Previous':
            page -= 2;
            break;
        case 'Next':
            break;
        default:
            var page = number - 1;
    }
    loadTable(page, $('#numberOnPage').val());
});

// change number of rows in table
$('#numberOnPage').on('change', function(){
    loadTable(0, $(this).val());
});
