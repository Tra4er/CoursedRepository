/**
 * Created by alena on 11.12.2016.
 */
$(function(){
    fillPlans();
    fillSelect("courseNumberS", courseNumbers);
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

function fillPlans() {
    // var spec = [{1: 'комп науки'}, {2: 'програмна'}];
    var spec = getSpecialities();
    $.getJSON(API + "/years/getAll", function(responseYears){
        //Go through the each entity in the response
        $.each(responseYears, function (key1, year) {
            var yearString = year.beginYear + "-" + year.endYear;
            var htmlCode = "<div class='row year-container' name='" + year.id + "'><h3 align='center'>на " + yearString + " рік</h3>";
            $.each(spec, function (key2, speciality){
                htmlCode += "<div class='speciality-container col-xs-12' name='" + spec[key2].id + "'><h4 align='center'>" + spec[key2].name + "</h4>";
                var general = "<div class='col-xs-6 group-type-container' name='GENERAL_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['GENERAL_FORM'] + "</h5>";
                var distance = "<div class='col-xs-6 group-type-container' name='DISTANCE_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['DISTANCE_FORM'] + "</h5>";
                $.each(year.educationPlans, function(key3, plan){
                    if (plan.speciality['id'] == spec[key2].id){
                        if (plan.groupType == 'GENERAL_FORM') {
                            general += "<a href='/disciplines?planId=" + plan.id + "&yearStr=" + yearString + "'><input type='button' class='btn btn-primary col-xs-12' value='" + localGroupUkr[plan.courseNumber] + " курс'/></a>";
                        }
                        else if (plan.groupType == 'DISTANCE_FORM')
                        {
                            distance += "<a href='/disciplines?planId=" + plan.id + "&yearStr=" + yearString + "'><input type='button' class='btn btn-primary col-xs-12' value='" + localGroupUkr[plan.courseNumber] + " курс'/></a>";
                        }
                    }
                });
                general += "<button type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button></div>";
                distance += "<button type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button></div>";
                htmlCode += general + distance + "</div>";
            });
            htmlCode += "</div>";
            $("#plan-content-container").append(htmlCode);
        });
    });
}

$('#plan-content-container').on('click', 'input', function () {
    var pId = "1";
    var yStr = "blabla"
    $.get( "/disciplines", { planId : pId, yearString: yStr});
    // alert('и тут мы переходим на страничку учебного плана либо он открывается в модальном окне');
});

$('#plan-content-container').on('click', '.add-plan-btn', function () {
    var yearId = $(this).closest('.year-container').attr('name');
    var yearText = $(this).closest('.year-container').children('h3').text();
    var specialityId = $(this).closest('.speciality-container').attr('name');
    var specialityText = $(this).closest('.speciality-container').children('h4').text();
    var groupType = $(this).closest('.group-type-container').attr('name');

    $('#plan-dialog-simple .modal-title').text('Навчальний план ' + yearText);
    $('#modal-body-form-plan-simple .yearId').attr('value', yearId);
    $('#modal-body-form-plan-simple .specialityId').attr('value', specialityId).text(specialityText);
    $('#modal-body-form-plan-simple .groupType').attr('value', groupType).text(localGroupUkr[groupType]);
});

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

//ToDo: реализовать сброс, подгрузку курсов
// Устанавливаем в 0 выпадающие списки как только начинает отображаться окно
// $('#plan-dialog').on('hidden.bs.modal', function (event) {
//     // resetSelect('modal-body-form-plan');
//     $('#modal-body-form-plan').each(function(key, value){
//          $("#" + value + " :first").attr("selected", "selected");
//      });
// });

$('#button-post-plan-simple').on('click', function() {

    var obj = JSON.stringify({"yearId" : $('#modal-body-form-plan-simple .yearId').attr('value'),
        "specialityId": $('#modal-body-form-plan-simple .specialityId').attr('value'),
        "groupType": $('#modal-body-form-plan-simple .groupType').attr('value'),
        "groupDegree": $('#modal-body-form-plan-simple .groupDegree').attr('value'),
        "courseNumber": $('#modal-body-form-plan-simple [name=courseNumber]').val()
        });

    $.ajax({
            type: "POST",
            url: "api/educationPlan/create",
            contentType: "application/json",
            data: obj,
            success: function (data) {
                $('#plan-dialog-simple').modal("toggle");
            },
            error: function (e) {
                alert('Помилка!');
                console.log(data)
            }
        });
});


// Отобразить все учебные планы
// $('#button-get-all-plans').on('click', function(){
//     $("#tbodyId").empty();
//     //fillTable();
//     //#plans-table
//     //api/educationPlan/getAll
// });