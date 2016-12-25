/**
 * Created by alena on 15.12.2016.
 */

$(function () {
    var titles = ['№', 'Дисципліна', 'Кількіть годин', 'Кількість кредитів', 'Вид семестрової атестації', 'ПІБ викладачів', 'Опції'];
    insertTable(titles, "FIRST");
    insertTable(titles, "SECOND");
    fillTableDisciplinesForPlan();
});

function fillTableDisciplinesForPlan() {
    $.get("/api/educationPlans/getOne", {educationPlanId: $.urlParam('planId')}, function (r) {
            $('.specialityId').attr('value', r.speciality['id']).text(r.speciality['fullName']);
            $('.plan-info .groupType').attr('value', r.groupType).text(localGroupUkr[r.groupType]);
            $('.plan-info .groupDegree').attr('value', r.groupDegree).text(localGroupUkr[r.groupDegree]);
            $('.courseNumber').attr('value', r.courseNumber).text(localGroupUkr[r.courseNumber]);
             var countFirst = 0;
             var countSecond = 0;
            $.each(r.disciplines, function (i, entity) {
                addDisciplineIntoTable(entity);
            });
        }
    );
    $('.plan-info .yearId').text($.urlParam('yearStr'));
    fillSelect("semesterNumber", semesterNumbers);
    fillSelect("disciplineType", localDisciplineUkr);
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

$('#button-post-discipline').on('click', function () {
    var obj = JSON.stringify({
        "name": $('#nameForm').val(),
        "type": $('#disciplineType').val(),
        "hours": $('[name=hoursForm]').text(),
        "credits": $('[name=creditsForm]').val(),
        "courseNumber": $('#modal-body-form-discipline .courseNumber').attr('value'),
        "semesterNumber": $('#semesterNumber').val(),
        "educationPlanId": $.urlParam('planId')
    });

    $.ajax({
        type: "POST",
        url: "/api/disciplines/create",
        contentType: "application/json",
        data: obj,
        success: function (data) {
            $('#discipline-dialog').modal("toggle");
            // location.reload();
            addDisciplineIntoTable(data);
        },
        error: function (e) {
            alert('Помилка!');
            console.log(data)
        }
    });
});

function addDisciplineIntoTable(discipline)
{
    var number = $("#" + discipline.semesterNumber +" > tbody").children('tr').last().children('td').first().text();
    if (number == undefined) number = 0;
    var htmlRow = "<tr id=" + discipline.id + ">";
    htmlRow += "<td>" + ++number + "</td>";
    htmlRow += "<td>" + discipline.name + "</td>";
    htmlRow += "<td>" + discipline.hours + "</td>";
    htmlRow += "<td>" + discipline.credits + "</td>";
    htmlRow += "<td>" + localDisciplineUkr[discipline.type] + "</td><td class='teachers'>";
    if (discipline.teachers == null || discipline.teachers.length == 0) {
        htmlRow += 'не призначено';
    }
    else{
        $.each(discipline.teachers, function (i, teach) {
            htmlRow += "<p>" + teach.lastName + "&nbsp;" + teach.firstName.substring(0,1) + ".&nbsp;" + teach.patronymic.substring(0,1) + ".</p>";
        })
    }
    htmlRow += "<td><button id='" + discipline.id + "' type='button' class='btn btn-default btn-sm disc-teach-btn' data-toggle='modal' data-target='#teacher-discipline-dialog'>Додати викладача</button></td></tr>";
    $("#" + discipline.semesterNumber +" > tbody").append(htmlRow);
}

$('tbody').on('click', 'tr .disc-teach-btn', function(){
    var discId = $(this).closest('tr').attr("id");
    var info = $(this).closest('tr').children('td:first').next().text();
    $('#teacher-container').html("<h2 id='"+ discId +"'>" + info + "</h2> <br/>");
    $.getJSON("/api/teachers/getAllWithoutDiscipline", {disciplineId : discId}, function(response){
        var $tc = $('#teacher-container');
        $.each(response, function(i, teach){
            var item = "<input type='button' id='" + teach.id + "' class='btn btn-default col-xs-12' value = '"
                + teach.lastName + " " + teach.firstName + " " + teach.patronymic + "'/>";
            $tc.append(item);
        })
    });
});

$('#teacher-container').on('click', 'input', function(){
    var discId = $(this).parent().children('h2').first().attr('id');
    var tId= $(this).attr('id');
    $.post( "/api/disciplines/connectTeacherWithDiscipline", {disciplineId: discId, teacherId: tId})
        .done(function(){
            $('#teacher-discipline-dialog').modal("hide");
            reloadTeachersForDiscipline(discId);
        })
        .fail(function() {
            alert( "Помилка!" );
        });
});

function reloadTeachersForDiscipline(disciplineId)
{
    $.getJSON('/api/teachers/getAllWithDiscipline', {disciplineId : disciplineId}, function(response){
        var htmlRow = "";
        if (response.length != 0){
            $.each( response, function (i, teacher) {
                htmlRow += "<p>" + teacher.lastName + "&nbsp;" + teacher.firstName.substring(0,1) + ".&nbsp;" + teacher.patronymic.substring(0,1) + ".</p>";
            })
        }
        else {
            htmlRow += 'не призначено';
        }
        $("#" + disciplineId + " > td:last").prev().html(htmlRow);
    });
}

// очистка формы при ее закрытии
$('#discipline-dialog').on('hidden.bs.modal', function(){
    $(this).find('input').val('');
    $(this).find('p[name=hoursForm]').empty();
    $(this).find('select').val('0');
});
