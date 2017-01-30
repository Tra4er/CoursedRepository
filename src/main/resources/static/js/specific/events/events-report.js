/**
 * Created by alena on 25.12.2016.
 */

$(function () {
    // alert($.urlParam('eventId'));
    fillHeaderOfReport();
    fillHeaderTable();
});

function fillHeaderOfReport()
{
    var $cont = $('#header-report');
    $.get('/api/events/' + $.urlParam('eventId'))
        .done(function(response){
            $cont.prepend('<h2>' + plannedEventType[response.data.eventType] + '</h2>');
        });

    $.get('/api/groups/' + $.urlParam('groupId'))
        .done(function(response){
            $cont.find('.groupName').text(response.data.speciality['groupsName'] + "-" + response.data.number);
        });
    //
    // $cont.append('Навчальний рік ' + "");
    // $cont.append('Семестр ' + "");
}

function fillHeaderTable () {
    var $tableHeader = $('table > thead > tr');
    $tableHeader.append('<th>№</th><th>ПІБ студента</th>');
    $.get('/api/disciplines/getAllDisciplinesFromPlannedEvent', {plannedEventId: $.urlParam('eventId'), groupId : $.urlParam('groupId')})
        .done(function(data){
            var arrayOfDiscipline = [];
            var arrayOfTeachers = [];
            $.each(data, function(i, discipline){
                var disc = discipline.name.split(".")
                $tableHeader.append('<th class="'+ discipline.id  + '">'+ disc['0'] +'</th>');
                arrayOfDiscipline.push(discipline.id);
                if(discipline.teachers.length != 0) arrayOfTeachers.push(discipline.teachers['0'].lastName);
                else arrayOfTeachers.push('-');
            });
            $tableHeader.append('<th>Всього А.</th><th>Всього Н.А.</th>');
            fillRowsReport(arrayOfDiscipline, arrayOfTeachers);

        })
        .fail(function(){
            alert( "Помилка!" );
        });
}

function fillRowsReport(disciplines, teachers){
    var $tableBody = $('table > tbody');
    var studentNumber = 0;
    $.get('/api/students', {groupId: $.urlParam('groupId')})
        .done(function(response){
            var allARow = 0;
            var allNARow = 0;
            var disciplinesCountA = [];
            var disciplinesCountNA = [];
            for(var i = 0; i < disciplines.length; i++) {
                disciplinesCountA.push(0);
                disciplinesCountNA.push(0);
            }
            $.each(response.data.content, function(i, student){
                var countA = 0;
                var countNA = 0;
                var htmlRow = '<tr class="'+ student.id  + '"><td>' + ++studentNumber + '</td><td>'+ student.lastName +'</td>';
                $.each(disciplines, function(j, discipline){
                    var mark = '-';

                    $.each(student.attestationGrades, function(k, at){
                        if(discipline == at.discipline['id']){
                            mark = at.firstTry;
                            if (mark) {
                                countA++;
                                disciplinesCountA[j]++;
                                htmlRow += '<td><button type="button" class="btn btn-success btn-sm col-xs-12"><span class="glyphicon glyphicon-ok"></span></button></td>';
                            }
                            else {
                                countNA++;
                                disciplinesCountNA[j]++;
                                htmlRow += '<td><button type="button" class="btn btn-danger btn-sm col-xs-12"><span class="glyphicon glyphicon-remove"></span></button></td>';
                            }
                            return false;
                        }
                    });
                    if (mark == '-') htmlRow += '<td><button type="button" class="btn btn-warning btn-sm col-xs-12"><span class="glyphicon glyphicon-minus"></span></button></td>';
                });
                htmlRow += '<td>' + countA + '</td><td>' + countNA + '</td></tr>';
                allARow += countA;
                allNARow += countNA;
                $tableBody.append(htmlRow);
            });

            var teachersRow = '<tr><td></td><td>Викладач</td>';
            var signRow = '<tr><td></td><td>Підпис</td>';
            var aRow = '<tr><td></td><td>Всього А.</td>';
            var naRow = '<tr><td></td><td>Всього Н.А.</td>';
            $.each(teachers, function(i, teach){
               teachersRow +=  '<td>' + teach + '</td>';
                signRow +=  '<td></td>';
                aRow += '<td>'+ disciplinesCountA[i] +'</td>';
                naRow += '<td>' + disciplinesCountNA[i] +'</td>';
            });
            aRow += '<td></td><td></td></tr>';
            naRow += '<td></td><td></td></tr>';
            teachersRow += '<td></td><td></td></tr>';
            signRow += '<td></td><td></td></tr>';
            $tableBody.append(aRow);
            $tableBody.append(naRow);
            $tableBody.append(teachersRow);
            $tableBody.append(signRow);

            $('#content').append('<div class="col-xs-12"><div class="form-group"><label class="control-label"> Всього А. по групі: ' +
                allARow + '</label></div><div class="form-group"><label class="control-label">Всього Н.А. по групі: ' +
                allNARow + '</label></div></div>');
        })
        .fail(function(){
            alert( "Помилка!" );
        });
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
