/**
 * Created by Hexray on 27.11.2016.
 */
$(document).ready(function () {
    var titles = ['id', 'Рік початку', 'Рік завершення'];
    insertTable(titles, "content-table");
    fillTableYearsFrom("content-table", "/api/years?page=0&size=5", 0);
});

function fillTableYearsFrom(tableId, requestAddress, page) {
    var params = ['id', 'beginYear', 'endYear'];
    $.getJSON(requestAddress, function(response){
        var htmlRow = "";
        $.each(response.data.content, function (i, entity) {
            htmlRow += "<tr>";
            $.each(entity, function (paramName, paramValue) {
                if($.inArray(paramName, params) !== -1) {
                    htmlRow += ("<td>" + paramValue + "</td>");
                }
            });
            htmlRow += "</tr>";
        });

        $("#" + tableId + "> tbody").html(htmlRow);
        var totalPages = response.data['totalPages'];
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
        $('.pagination').html(item);
    });
}

//It sends serialized
$('#button-year-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, '/api/years', 'add-dialog');
});

// очистка формы при ее закрытии
$('#add-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
});

$('.pagination').on('click', 'li:not(.disabled)', function(){
        var entityParams = ['id', 'beginYear', 'endYear'];
        var active = $(this).siblings('.active').children('a').text();
        var page = active;
        switch ($(this).children('a').attr('aria-label')) {
            case 'Previous':
                page -= 2;
                break;
            case 'Next':
                break;
            default:
                var page = $(this).children('a').text() - 1;
        }
        fillTableYearsFrom("content-table", "/api/years?page=" + page + "&size=" + $('#numberOnPage').val(), page);
});

$('#numberOnPage').on('change', function(){
    fillTableYearsFrom("content-table", "/api/years?page=0&size=" + $(this).val(), 0);
});

// function addItem(item){
//     var htmlRow = "<tr><td>" + item.id + "</td><td>" + item.beginYear + "</td><td>" + item.endYear + "</td></tr>";
//     $("#content-table > tbody").append(htmlRow);
// }

function reloadPage(){
    var active = $('.pagination').children('.active').children('a').text() - 1;
    fillTableYearsFrom("content-table", "/api/years?page=" + active + "&size=" + $('#numberOnPage').val(), 0);
}