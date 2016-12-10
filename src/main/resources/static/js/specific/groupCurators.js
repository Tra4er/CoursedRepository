/**
 * Created by alena on 10.12.2016.
 */
$(function(){
    var titles = ['Номер', 'Тип', 'Рівень', 'Номер курсу', 'Куратор'];
    insertTable(titles, "groupCurators-table");
    fillCuratorsTable();
});

function fillCuratorsTable() {
    $.getJSON(API + "/groups/getAll" , function(response){
        $.each(response, function (i, entity) {
            var htmlRow = "<tr>";
             htmlRow +="<td>" + entity.number + "</td>";
            htmlRow +="<td>" + groupType[entity.groupType] + "</td>";
            htmlRow +="<td>" + groupDegree[entity.groupDegree] + "</td>";
            htmlRow +="<td>" + courseNumbers[entity.courseNumber] + "</td><td>";
            if (entity.curators.length != 0){
                entity.curators.each(function (i, curator) {
                    htmlRow += "<p>" + curator.teacher['lastName'] + "</p>";
                })
            }
            else {
                htmlRow += 'не призначено';
            }
            htmlRow +="</td><td><button id='" + entity.id + "' type='button'   class='btn btn-default btn-sm' data-toggle='modal' data-target='#curator-dialog'>Додати куратора</button></td>";
            htmlRow += "</tr>";
            $("#groupCurators-table > tbody").append(htmlRow);
        });
    });
}

$('#groupCurators-table > tbody').on('click', 'tr > td > .btn-default', function(){
    var myId = $(this).attr("id");
    $('#teacher-container').html('тут додається куратор для групи з id = ' + myId);
});

