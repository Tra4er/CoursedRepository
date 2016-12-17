/**
 * Created by alena on 15.12.2016.
 */

$(function () {
    var titles = ['Дисципліна', 'Кількіть годин', 'Кількість кредитів', 'Вид семестрової атестації', 'Викладач', 'Cеместер'];
    insertTable(titles, "content-table-disciplines");
    fillTableDisciplinesForPlan();
});

function fillTableDisciplinesForPlan() {
    $.get("api/educationPlan/getOne", {educationPlanId: $.urlParam('planId')}, function (r) {
            $('.specialityId').attr('value', r.speciality['id']).text(r.speciality['fullName']);
            $('.plan-info .groupType').attr('value', r.groupType).text(localGroupUkr[r.groupType]);
            $('.plan-info .groupDegree').attr('value', r.groupDegree).text(localGroupUkr[r.groupDegree]);
            $('.courseNumber').attr('value', r.courseNumber).text(localGroupUkr[r.courseNumber]);

            $.each(r.disciplines, function (i, entity) {
                var htmlRow = "<tr id=" + entity.id + ">";
                htmlRow += "<td>" + entity.name + "</td>";
                htmlRow += "<td>" + entity.hours + "</td>";
                htmlRow += "<td>" + entity.credits + "</td>";
                htmlRow += "<td>" + localDisciplineUkr[entity.type] + "</td><td class='teachers'>";
                if (entity.teachers.length != 0) {
                    $.each(entity.teachers, function (i, teach) {
                        htmlRow += "<p>" + teach.lastName + "</p>";
                    })
                }
                else {
                    htmlRow += 'не призначено';
                }
                htmlRow += "</td><td>" + localGroupUkr[entity.semesterNumber] + "</td>";
                htmlRow += "<td><button id='" + entity.id + "' type='button' class='btn btn-default btn-sm' data-toggle='modal' data-target='#teacher-discipline-dialog'>Додати викладача</button></td>";

                htmlRow += "</tr>";
                $("#content-table-disciplines > tbody").append(htmlRow);
            });
        }
    );
    $('.plan-info .yearId').text($.urlParam('yearStr'));
    fillSelect("semesterNumber", semesterNumbers)
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
        //"semesterId" : $('#modal-body-form-discipline .semesterId').attr('value'),
        "hours": $('[name=hoursForm]').text(),
        "credits": $('[name=creditsForm]').val(),
        "courseNumber": $('#modal-body-form-discipline .courseNumber').attr('value'),
        "semesterNumber": $('#semesterNumber').val(),
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
            location.reload();
        },
        error: function (e) {
            alert('Помилка!');
            console.log(data)
        }
    });
});


$('#content-table-disciplines > tbody').on('click', 'tr > td > .btn-default', function(){
    var discId = $(this).closest('tr').attr("id");
    var info = $(this).closest('tr').children('td:first').text()

    $('#teacher-container').html("<h2 id='"+ discId +"'>" + info + "</h2> <br/>");

    $.getJSON("api/user/getAllTeachers", function(response){
        $.each(response, function(i, teach){
            var item = "<input type='button' id='" + teach.teacherEntity['id'] + "' class='btn btn-default col-xs-12' value = '"
                + teach.teacherEntity['lastName'] + " " + teach.teacherEntity['firstName'] + " " + teach.teacherEntity['patronymic'] + "'/>";
            $('#teacher-container').append(item);
        })
    });
});


$('#teacher-container').on('click', 'input', function(){
    var discId = $(this).parent().children('h2:first').attr('id');
    var tId= $(this).attr('id');
    alert('ну тут типо должно быть добавление, которого я найти не смогла');
    // $.post( "api/groups/connectWithTeacher", {groupId: discId, teacherId: tId})
    //     .done(function(){
    //         $('#curator-dialog').modal("hide");
    //         reloadCuratorsForGroup(gId);
    //     })
    //     .fail(function() {
    //         alert( "Помилка!" );
    //     });
});
