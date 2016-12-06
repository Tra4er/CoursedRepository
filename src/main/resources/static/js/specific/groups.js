/**
 * Created by Hexray on 04.12.2016.
 */
var courseNumbers = new Object();

courseNumbers["FIRST"] = "Перший";
courseNumbers["SECOND"] = "Другий";
courseNumbers["THIRD"] = "Третій";
courseNumbers["FOURTH"] = "Четвертий";
courseNumbers["FIFTH"] = "П'ятий";
courseNumbers["SIXTH"] = "Шостий";

courseNumbers["Перший"] = "FIRST";
courseNumbers["Другий"] = "SECOND";
courseNumbers["Третій"] = "THIRD";
courseNumbers["Четвертий"] = "FOURTH";
courseNumbers["П'ятий"] = "FIFTH";
courseNumbers["Шостий"] = "SIXTH";

var semesterNumbers = new Object();

semesterNumbers["FIRST"] = "Перший";
semesterNumbers["SECOND"] = "Другий";

semesterNumbers["Перший"] = "FIRST";
semesterNumbers["Другий"] = "SECOND";


$(document).ready(function () {
    var titles = ['id', 'Назва', 'Тип', 'Рівень', 'Номер курсу'];
    insertTable(titles, "content-table");

    var entityParams = ['id', 'number', 'groupType', 'groupDegree', 'courseNumber'];
    fillTableFrom("content-table", API + "/groups/getAll", entityParams);

});

//It sends serialized
$('#button-group-post').click(function(){
    var form = $('#modal-body-form');
    sendAjaxPost(form, 'api/groups/create');
});

$('#add-dialog').on('show.bs.modal', function() {
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");
});
