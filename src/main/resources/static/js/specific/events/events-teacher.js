$(function () {
    // get all current planned events
    $.getJSON('/api/events/getAllFromCurrentYear', function (entity) {
        $.each(entity, function (i, item) {
            var id = item.id;
            var isEventActual = isActuallyEvent(item.beginDate, item.expirationDate);
            var body = '<div class="panel panel-' + isEventActual + '" id ="' + id +
                '"><div class="panel-heading" role="tab" id="heading' + id + '"><h4 class="panel-title">';
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
    $.getJSON('/api/discipline/getAllActualConnectedWithTeacher', {plannedEventId : plannedEventId}, function(response){
        $.each(response,function(i, item){
            var button = '<input type="button" id="' + item.id + '" semester="' + item.semesterNumber +'" course="'+ item.courseNumber +
                '" edPlanId="' + plannedEventId + '" class="btn btn-success col-xs-12 discipline-button" value = "' + item.name +
                ' (' + localGroupUkr[item.courseNumber] + ' курс)"/>';
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
    $('#groups-container').html("<h2 id='"+ discId +"'>" + info + "</h2> <br/>");

    $.getJSON("api/groups/getAllForGrading", {disciplineId : discId, courseNumber: course, semesterNumber: semester}, function(response){
        var items = "";
        $.each(response, function(i, group){
            items += "<input type='button' id='" + group.id + "' class='btn btn-default col-xs-12' value = '"
                + group.speciality['groupsName'] + "-" + group.number + "'/>";
            // $tc.append(item);
        })
        $('#groups-container').append(items);
    });
     $('#discipline-groups-dialog').modal('show');
});

// $.getJSON('/api/discipline/getAllActualConnectedWithTeacher', function(){
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


