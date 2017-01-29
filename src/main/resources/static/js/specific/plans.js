/**
 * Created by alena on 11.12.2016.
 */
$(function(){
    fillOnePlan();
});

$('a[name=plan-show]').on('shown.bs.tab', function(e) {
if ($(e.target).attr('id') == 'one-plan') {
        fillOnePlan();
    }
    else if ($(e.target).attr('id') == 'all-plans') {
        fillPlans();
    }
});

function getSpecialities() {
    var spec = [];
    $.getJSON("api/specialities/getAll", function(response){
        $.each(response, function(key, speciality){
            spec.push({
                id: speciality.id,
                name : speciality.fullName
            });
        });
    });
    return spec;
}

function fillOnePlan(){
    var spec = getSpecialities();
    $("#one-plan-container").html(" ");
    $.getJSON("/api/years/current", function(response){
        var year = response.data;
        htmlFormForEducationPlan(year.beginYear, year.endYear, year.id, spec, year.educationPlans, 'one-plan-container');
    });
}

function fillPlans() {
    var spec = getSpecialities();
    $("#all-plans-container").html(" ");
    $.getJSON("/api/years", function(responseYears){
        $.each(responseYears.data, function (key1, year) {
            htmlFormForEducationPlan(year.beginYear, year.endYear, year.id, spec, year.educationPlans, 'all-plans-container');
        });
    });
}

function htmlFormForEducationPlan(beginYear, endYear, yearId, spec, educationPlans, containerId){
    var yearString = beginYear + "-" + endYear;
    var htmlCode = "<div class='row year-container' name='" + yearId + "'><h3 align='center'>на " + yearString + " рік</h3>";
    $.each(spec, function (key2, speciality){
        htmlCode += "<div class='speciality-container col-xs-12' name='" + spec[key2].id + "'><h4 align='center'>" + spec[key2].name + "</h4>";
        var general = "<div class='col-xs-6 group-type-container' name='GENERAL_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['GENERAL_FORM'] + "</h5>";
        var distance = "<div class='col-xs-6 group-type-container' name='DISTANCE_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['DISTANCE_FORM'] + "</h5>";
        var generalCounter = 0;
        var distanceCounter = 0;
        $.each(educationPlans, function(key3, plan){
            if (plan.speciality['id'] == spec[key2].id){
                if (plan.groupType == 'GENERAL_FORM') {
                    general += "<a href='/disciplines?planId=" + plan.id + "&yearStr=" + yearString + "' name='" + plan.courseNumber + "'><input type='button' class='btn btn-primary col-xs-12' value='" + localGroupUkr[plan.courseNumber] + " курс'/></a>";
                    generalCounter++;
                }
                else if (plan.groupType == 'DISTANCE_FORM')
                {
                    distance += "<a href='/disciplines?planId=" + plan.id + "&yearStr=" + yearString + "' name='" + plan.courseNumber + "'><input type='button' class='btn btn-primary col-xs-12' value='" + localGroupUkr[plan.courseNumber] + " курс'/></a>";
                    distanceCounter++;
                }
            }
        });
        if(distanceCounter < 6){
            distance += "<button name='"+ yearString +"' type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button>";
        };
        if (generalCounter < 6){
            general += "<button name='"+ yearString +"' type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button>";
        }
        general += "</div>"
        distance += "</div>";
        htmlCode += general + distance + "</div>";
    });
    htmlCode += "</div>";
    $("#" + containerId).append(htmlCode);
}

$('.plan-content-container').on('click', '.add-plan-btn', function () {
    var yearId = $(this).closest('.year-container').attr('name');
    var yearText = $(this).closest('.year-container').children('h3').text();
    var specialityId = $(this).closest('.speciality-container').attr('name');
    var specialityText = $(this).closest('.speciality-container').children('h4').text();
    var groupType = $(this).closest('.group-type-container').attr('name');

    $('#plan-dialog-simple .modal-title').text('Навчальний план ' + yearText);
    $('#modal-body-form-plan-simple .yearId').attr('value', yearId);
    $('#modal-body-form-plan-simple .specialityId').attr('value', specialityId).text(specialityText);
    $('#modal-body-form-plan-simple .groupType').attr('value', groupType).text(localGroupUkr[groupType]);

    var $aEl = $(this).closest('.group-type-container').children('a');
    var myArray = [];
    $aEl.each(function(i, elem){
         myArray.push($(elem).attr('name'));
    });
    var firstString = $("#courseNumberS > option:first").text();
    var items = "<option value='0'> " + firstString + "</option>";
    $.each(courseNumbers, function(i, entity)
    {
        if(!in_array(i, myArray)){
            items += "<option value='" + i + "'>" + entity + "</option>";
        }
    });
    $('#courseNumberS').html(items);
});

function in_array(value, array)
{
    for(var i = 0; i < array.length; i++)
    {
        if(array[i] == value) return true;
    }
    return false;
}

$('#courseNumberS').on('change', function(){
   var value = $('#courseNumberS').val();
    if (value == 'FIRST' || value == 'SECOND' || value == 'THIRD' || value == 'FOURTH'){
        $('#modal-body-form-plan-simple .groupDegree').attr('value', 'BACHELOR').text(localGroupUkr["BACHELOR"]);
    }
    else if (value == 'FIFTH' || value == 'SIXTH'){
        $('#modal-body-form-plan-simple .groupDegree').attr('value', 'MASTER').text(localGroupUkr["MASTER"]);
    }
    else
    {
        $('#modal-body-form-plan-simple .groupDegree').attr('value', '0').text('не обрано курс');
    }
});

$('#button-post-plan-simple').on('click', function() {
    var yearId = $('#modal-body-form-plan-simple .yearId').attr('value');
    var specialityId = $('#modal-body-form-plan-simple .specialityId').attr('value');
    var groupType = $('#modal-body-form-plan-simple .groupType').attr('value');
    var groupDegree = $('#modal-body-form-plan-simple .groupDegree').attr('value');
    var courseNumber = $('#modal-body-form-plan-simple [name=courseNumber]').val();

    var obj = JSON.stringify({"yearId" : yearId,
        "specialityId": specialityId,
        "groupType": groupType,
        "groupDegree": groupDegree,
        "courseNumber": courseNumber
        });

    $.ajax({
            type: "POST",
            url: "api/educationPlans",
            contentType: "application/json",
            data: obj,
            success: function (response) {
                $('#plan-dialog-simple').modal("toggle");
                addCourseButton(yearId, specialityId, groupType, courseNumber, response.data.id);
            },
            error: function (response) {
                alert('Помилка!');
                console.log(response.message)
            }
        });
});

// динамическое добавление кнопки
function addCourseButton (yearId, specialityId, groupType, courseNumber, planId){
    var $groupTypeContainer = $('.plan-content-container .year-container[name=' +yearId+ '] .speciality-container[name='+ specialityId +'] .group-type-container[name='+ groupType +']');
    var $buttonInContainer = $groupTypeContainer.children('button');
    var $aInContainer = $groupTypeContainer.children('a');
    var yearString = $buttonInContainer.last().attr('name');
    var htmlInfo = "<a href='/disciplines?planId=" + planId + "&yearStr=" + yearString + "' name='" + courseNumber + "'><input type='button' class='btn btn-primary col-xs-12' value='" + localGroupUkr[courseNumber] + " курс'/></a>"
    if ($aInContainer.length == 0)
    {
        $buttonInContainer.before(htmlInfo);
    }
    else if ($aInContainer.length < 5)
    {

        $aInContainer.last().after(htmlInfo)
    }
    else if ($aInContainer.length >= 5)
    {
        $buttonInContainer.replaceWith(htmlInfo);
    }
}