/**
 * Created by alena on 11.12.2016.
 */
$(function(){

// заполнить селекты в модальном окне добавления плана
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelect("courseNumber", courseNumbers);
    fillSelect("groupType", groupType);
    fillSelect("groupDegree", groupDegree);
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");

    fillPlans();
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
            var htmlCode = "<div class='row year-container' name='" + year.id + "'><h3 align='center'>на " + year.beginYear + "-" + year.endYear + " рік</h3>";
            $.each(spec, function (key2, speciality){
                htmlCode += "<div class='speciality-container col-xs-12' name='" + spec[key2].id + "'><h4 align='center'>" + spec[key2].name + "</h4>";
                var general = "<div class='col-xs-6 group-type-container' name='GENERAL_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['GENERAL_FORM'] + "</h5>";
                var distance = "<div class='col-xs-6 group-type-container' name='DISTANCE_FORM'><h5 align='center' class='col-xs-12'>" + localGroupUkr['DISTANCE_FORM'] + "</h5>";
                $.each(year.educationPlans, function(key3, plan){
                    if (plan.speciality['id'] == spec[key2].id){

                        if (plan.groupType == 'GENERAL_FORM') {
                            general += "<input type='button' class='btn btn-success col-xs-12' id='" + plan.id + "' value='" + localGroupUkr[plan.courseNumber] +"' курс'/>";
                        }
                        else if (plan.groupType == 'DISTANCE_FORM')
                        {
                            distance += "<input type='button' class='btn btn-success col-xs-12' id='" + plan.id + "' value='" + localGroupUkr[plan.courseNumber] + "' курс'/>";
                        }
                    }

                });
                general += "<br/><br/><button type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button><br/><br/></div>";
                distance += "<br/><br/><button type='button' class='btn btn-success col-xs-12 add-plan-btn' data-toggle='modal' data-target='#plan-dialog-simple'><span class='glyphicon glyphicon-plus'></span></button><br/><br/></div>";
                htmlCode += general + distance + "</div>";
            });
            htmlCode += "</div><br/><br/><br/><br/>";
            $("#plan-content-container").append(htmlCode);
        });
    });
}

$('#plan-content-container').on('click', 'input', function () {
    alert('и тут мы переходим на страничку учебного плана либо он открывается в модальном окне');
});

$('#plan-content-container').on('click', '.add-plan-btn', function () {
    //yearId
    //courseNumber
    //groupType
    //groupDegree
    //specialityId
    var yearId = $(this).closest('.year-container').attr('name');
    var groupType = $(this).closest('.group-type-container').attr('name');
    var specialityId = $(this).closest('.speciality-container').attr('name');
    $('#modal-body-form-plan-simple .yearId').attr('id', yearId).text(yearId);
    $('#modal-body-form-plan-simple .groupType').attr('id', groupType).text(groupType);
    $('#modal-body-form-plan-simple .specialityId').attr('id', specialityId).text(specialityId);

    //alert('и тут мы добавляем этот план для года ' + yearId + ' ' +groupType +' '+ specialityId)

});


//ToDo: реализовать сброс, подгрузку курсов и нормальных именований.

// Устанавливаем в 0 выпадающие списки как только начинает отображаться окно
// $('#plan-dialog').on('hidden.bs.modal', function (event) {
//     // resetSelect('modal-body-form-plan');
//     $('#modal-body-form-plan').each(function(key, value){
//          $("#" + value + " :first").attr("selected", "selected");
//      });
// });

// Отправляем данные по клику
$('#button-post-plan').on('click', function(){
    var form = $('#modal-body-form-plan');
    sendAjaxPost(form, 'api/educationPlan/create', 'plan-dialog');
});

// Отобразить все учебные планы
$('#button-get-all-plans').on('click', function(){
    $("#tbodyId").empty();
    //fillTable();
    //#plans-table
    //api/educationPlan/getAll
});

//api/educationPlan/create

function downloadYears() {
    // получить все года
    // заполнить выпадающий список, или в виде кнопок
}

function downloadPlanByYear(){
    // получить учебные планы по id года

    // $.getJSON("api/user/getAllPlans", function(response){
    //     var item = "<h2>Навчальний план на ...</h2>"
    //     $.each(response, function(i, plan){
    //         var item = "<input type='button' id='" + teach.teacherEntity['id'] + "' class='btn btn-default col-xs-12' value = '"
    //             + teach.teacherEntity['lastName'] + " " + teach.teacherEntity['firstName'] + " " + teach.teacherEntity['patronymic'] + "'/>";
    //         $('#teacher-container').append(item);
    //     })
    //     ('#plans-container').append()
    // });
    //
};

function ShowByType() {

}

function ShowByDegree() {

}

function ShowByCourse() {

}

