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
                    convertArrayToDateString(item.beginDate) + ' - ' + convertArrayToDateString(item.expirationDate) + ')</a></h4></div><div id="collapse' +
                    id + '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne"><div class="panel-body"></div></div></div>';
            }
            else body += plannedEventType[item.eventType] + ' (' + convertArrayToDateString(item.beginDate) +
                    ' - ' + convertArrayToDateString(item.expirationDate) + ')</div>';
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
            $('#accordion-event').addClass('col-xs-3');
            var event = plannedEventType[eventType];
            var htmlTable = '<div class="col-xs-9"><h2>' + discName+ '</h2><table id="event-content-table" class="table table-hover table-striped"><thead><tr><th>№</th><th>ПІБ</th>';
            htmlTable += '<th>' + event + '</th></tr></thead><tbody>';
            var counter = 0;
            $.each(responce, function(i,student){
                htmlTable += '<tr><td>'+ ++counter +'</td><td>' + student.lastName + " " + student.firstName + '</td><td>';
                htmlTable += '<div class="material-switch pull-right"><input id=switch"'+ student.id +'" name="someSwitchOption001" type="checkbox"/><label for="someSwitchOptionSuccess" class="label-success"></label></div></td></tr>'
            });
            htmlTable +='</tbody></table></div>';
            $('#accordion-event').after(htmlTable);
            //fillTableAtestation(responce, discName, discId, eventType);
        })
        .fail(function() {
            alert( "Помилка!" );
        });
});

//noinspection JSAnnotator
// function fillTableAtestation(group, discName, discId, eventType) {
//
// }
// $.getJSON('/api/disciplines/getAllActualConnectedWithTeacher', function(){
//
// });

function isActuallyEvent(start, end) {
    if (isPast(start) && isPast(end)) return 'danger';
    if (isPast(start) && isFuture(end)) return 'success';
    else return 'info';
}

// check if date in the past
function isPast(a) {
    var now = new Date();
    var year = now.getUTCFullYear();
    var month = now.getMonth() + 1;
    var date = now.getUTCDate();
    var hours = now.getUTCHours();
    var minutes = now.getUTCMinutes();
    if (a[0] < year) return true;
    else if (a[0] == year) {
        if (a[1] < month) return true;
        else if (a[1] == month) {
            if (a[2] < date) return true;
            else if (a[2] == date) {
                if (a[3] < hours) return true;
                else if (a[3] == hours) {
                    if (a[4] < minutes) return true;
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }
    else return false;
}
// check if date in the future
function isFuture(a) {
    var now = new Date();
    var year = now.getUTCFullYear();
    var month = now.getMonth() + 1;
    var date = now.getUTCDate();
    var hours = now.getUTCHours();
    var minutes = now.getUTCMinutes();
    if (a[0] > year) return true;
    else if (a[0] == year) {
        if (a[1] > month) return true;
        else if (a[1] == month) {
            if (a[2] > date) return true;
            else if (a[2] == date) {
                if (a[3] > hours) return true;
                else if (a[3] == hours) {
                    if (a[4] > minutes) return true;
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }
    else return false;
}

function convertArrayToDateTimeString(entity) {
    var dateTimeString = entity[2] + '.' + entity[1] + '.' + entity[0] +
        ".&nbsp;" + entity[3] + ':' + entity[4];
    return dateTimeString;
}

function convertArrayToDateString(entity) {
    var dateTimeString = entity[2] + '.' + entity[1] + '.' + entity[0];
    return dateTimeString;
}


