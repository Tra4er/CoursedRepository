/**
 * Created by Trach on 12/12/2016.
 */
$(document).ready(init);

function init(){
    fillTable();

    $("#updateButton").click(function() {
        clearTable();
        fillTable();
    })
}

function fillTable(){
    var titles = ['id', 'Дата початку', 'Дата завершення', 'Тип'];
    insertTable(titles, "content-table");
    var entityParams = ['id', 'beginDate', 'expirationDate', 'eventType'];
    var requestAddress = API + "/events/getAll";
    $.getJSON(requestAddress, function(response){
        //Go through the each entity in the response
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
            //Go through the each parameter in the entity
            $.each(entity, function (paramName, paramValue) {
                //If a parameter belongs to params array(argument of function)
                if($.inArray(paramName, entityParams) !== -1) {
                    if(paramName.includes("Date")) {
                        // var date = new Date();
                        // date.setTime(Date.parse(paramValue));
                        paramValue = paramValue + "";
                        paramValue = paramValue.replace("T", " Час: ");
                    }
                    //Then we add the value of this parameter to the row
                    htmlRow += ("<td>" + paramValue + "</td>");
                }
            });
            htmlRow += "</tr>";
            $("#content-table> tbody").append(htmlRow);
        });
    });
}

function clearTable() {
    $("#table-heads").empty();
    $("#content-table> tbody").empty();
}