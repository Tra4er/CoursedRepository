/**
 * Created by alena on 15.12.2016.
 */

$(function () {
    var titles = ['Дисципліна', 'Кількіть годин', 'Кількість кредитів', 'Вид семестрової атестації', 'Викладач', 'Cеместер'];
    insertTable(titles, "content-table-disciplines");

    //alert($.urlParam('planId'));
    fillTableDisciplinesForPlan();
});

function fillTableDisciplinesForPlan() {
    $.get("api/educationPlan/getOne", {educationPlanId: $.urlParam('planId')}, function (r) {
            $('.plan-info .yearId').attr('value', r.year['id']).text(r.year['beginYear'] + "-" + r.year['endYear']);

            $('.specialityId').attr('value', r.speciality['id']).text(r.speciality['fullName']);
            $('.plan-info .groupType').attr('value', r.groupType).text(localGroupUkr[r.groupType]);
            $('.plan-info .groupDegree').attr('value', r.groupDegree).text(localGroupUkr[r.groupDegree]);
            $('.courseNumber').attr('value', r.courseNumber).text(localGroupUkr[r.courseNumber]);

            var items = "<option value='0'> " + $("#modal-body-form-discipline #semesterId > option:first").text() + "</option>";
            $.each(r.year.semesters, function (i, entity) {
                items += "<option value='" + entity.id + "'>" + localGroup(entity['semesterNumber']) + "</option>";
            });
            $("#modal-body-form-discipline #semesterId").html(items);

            fillSelect("disciplineType", localDisciplineUkr);

            // и тут же дисциплины заполнить еще
        }
    )
}

$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    else {
        return results[1] || 0;
    }
};

// переводит кредиты в часы с коэфициентом 30
$('[name=creditsForm]').on('input', function () {
    $('[name=hoursForm]').text($(this).val() * 30);
});

$('#button-post-discipline').on('click', function(){
    var obj = JSON.stringify({
        "name": $('#nameForm').val(),
        "type": $('#disciplineType').val(),
        //"semesterId" : $('#modal-body-form-discipline .semesterId').attr('value'),
        "hours": $('[name=hoursForm]').text(),
        "credits": $('[name=creditsForm]').val(),
        "courseNumber": $('#modal-body-form-discipline .courseNumber').attr('value'),
        "semesterNumber": $('#semesterId').val(),
        "specialityId": $('.specialityId').attr('value'),
        "educationPlanId": $.urlParam('planId')
    });

    $.ajax({
        type: "POST",
        url: "api/discipline/create",
        contentType: "application/json",
        data: obj,
        success: function (data) {
            $('#discipline-dialog').modal("toggle");
        },
        error: function (e) {
            alert('Помилка!');
            console.log(data)
        }
    });
});