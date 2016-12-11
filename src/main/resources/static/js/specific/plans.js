/**
 * Created by alena on 11.12.2016.
 */
$(function(){
    fillSelectYear("yearIdCrit", API + "/years/getAll");
    fillSelect("courseNumberCrit", courseNumbers);
    fillSelect("groupTypeCrit", groupType);
    fillSelect("groupDegreeCrit", groupDegree);
    fillSelectFrom("specialityIdCrit", API + "/specialities/getAll", "fullName");
});

// Заполним выпадающие списки как только начинает отображаться окно
$('#plan-dialog').on('show.bs.modal', function (event) {
    fillSelectYear("yearId", API + "/years/getAll");
    fillSelect("courseNumber", courseNumbers);
    fillSelect("groupType", groupType);
    fillSelect("groupDegree", groupDegree);
    fillSelectFrom("specialityId", API + "/specialities/getAll", "fullName");

});

// Отправляем данные по клику
$('#button-post-plan').on('click', function(){
    var form = $('#modal-body-form-plan');
    sendAjaxPost(form, 'api/...', 'plan-dialog');
});



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

