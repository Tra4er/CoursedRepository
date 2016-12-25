$(function () {
    // get all current planned events
    $.getJSON('/api/events/getAllFromCurrentYear', function (entity) {
        $.each(entity, function (i, item) {
            var id = item.id;
            var isEventActual = isActuallyEvent(item.beginDate, item.expirationDate);
            var body = '<div class="panel panel-' + isEventActual + '" id ="' + id + '" eventType="' +
            item.eventType + '"><div class="panel-heading" role="tab" id="heading' + id + '"><h4 class="panel-title">';
            if (isEventActual == 'success') {
                body += '<a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse' + id +
                    '" aria-expanded="true" aria-controls="collapseOne">' + plannedEventType[item.eventType] + ' (' +
                    dateToString(item.beginDate) + ' - ' + dateToString(item.expirationDate) + ')</a></h4></div><div id="collapse' +
                    id + '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne"><div class="panel-body"></div></div></div>';
            }
            else body += plannedEventType[item.eventType] + ' (' + dateToString(item.beginDate) +
                    ' - ' + dateToString(item.expirationDate) + ')</div>';
            $('#accordion-event').append(body);
        })
    });
});

$('#accordion-event').on('show.bs.collapse', '.panel', function (e) {
    var plannedEventId = e.currentTarget.id;
    // var id = $(this).attr('id');
    var $place = $(e.currentTarget).find('.panel-body');
    $place.empty();
    $.getJSON('/api/disciplines/getAllActualConnectedWithTeacher', {plannedEventId : plannedEventId}, function(response){
        $.each(response,function(i, item){
            var button = '<input type="button" id="' + item.id + '" semester="' + item.semesterNumber +'" course="'+ item.courseNumber +
                '" edPlanId="' + plannedEventId + '" class="btn btn-success col-xs-12 discipline-button" value = "' + item.name +
                ' (' + localGroupUkr[item.courseNumber] + '&nbsp;курс)"/>';
            $place.append(button);
        })
    });
});

$('#accordion-event').on('click', '.discipline-button', function (e){
    var discId = e.currentTarget.id;
    var edPlanId = $(this).attr('edPlanId');
    var semester = $(this).attr('semester');
    var course = $(this).attr('course');
    var info = $(this).attr('value');
    var eventType = $(this).closest('.panel').attr('eventType');
    $('#groups-container').html("<h2 id='"+ discId + "' eventType='" + eventType + "'>" + info + "</h2> <br/>");
    $.getJSON("api/groups/getAllForGrading", {disciplineId : discId, courseNumber: course, semesterNumber: semester}, function(response){
        var items = "";
        $.each(response, function(i, group){
            items += "<input type='button' id='" + group.id + "' class='btn btn-success col-xs-12' value = '"
                + group.speciality['groupsName'] + "-" + group.number + "'/>";
        })
        $('#groups-container').append(items);
    });
     $('#discipline-groups-dialog').modal('show');
});

$('#groups-container').on('click', 'input', function(){
    var $element = $(this).parent().children('h2').first()
    var discId = $element.attr('id');
    var discName = $element.text();
    var eventType = $element.attr('eventType');
    var groupId= $(this).attr('id');
    $.getJSON( "api/students/getAllFromGroup", {groupId: groupId})
        .done(function(responce){
            $('#discipline-groups-dialog').modal("hide");
            $('#accordion-event').addClass('col-xs-6');
            var event = plannedEventType[eventType];
            var htmlTable = '<div class="col-xs-6"><h2 disciplineId ="'+ discId + '">' + discName+ '</h2><table id="event-content-table" class="table table-hover table-striped"><thead><tr><th>№</th><th>ПІБ</th>';
            htmlTable += '<th>' + event + '</th></tr></thead><tbody>';
            var counter = 0;
            $.each(responce, function(i,student){
                htmlTable += '<tr><td studentId="'+ student.id +'">'+ ++counter +'</td><td>' + student.lastName + " " + student.firstName + '</td><td>';
                htmlTable += '<div class="material-switch pull-right"><input id="switch'+ student.id +'" name="' + student.id + '" type="checkbox"/><label for="switch'+ student.id +'" class="label-success"></label></div></td></tr>'
            });
            htmlTable +='</tbody></table> <input id="saveFirstAttestationGrade" type="button" class="btn btn-success" value="Зберегти відомість"/></div>';
            $('#accordion-event').after(htmlTable);
        })
        .fail(function() {
            alert( "Помилка!" );
        });
});

$('#content').on('click', '#saveFirstAttestationGrade', function(e){
    var obj = [];
    // var disciplineId = $(this).closest('h2').attr('disciplineId');
    var disciplineId = $(e.currentTarget).parent().children().first().attr('disciplineId');
    $('tbody > tr').each(function(i,v){
        var $td = $(this).children('td');
        var studentId = $td.first().attr('studentId');
        var firstTry = $td.last().find('input').prop('checked');
         // var   firstTry = switchToBool(frstTr);
        obj[i] = {'firstTry': firstTry, 'studentId': studentId, 'disciplineId': disciplineId, 'secondTry':'false'};
    }
    );
    var string = JSON.stringify(obj);
    $.ajax({
        type: "POST",
        url: "api/attestations/createManyFirst",
        contentType: "application/json",
        data: string,
        success: function (data) {
            alert('Перша атестація виставлена!')
        },
        error: function (e) {
            alert('Помилка!');
            console.log(e)
        }
    });
});

