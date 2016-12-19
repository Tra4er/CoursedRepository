$(document).ready(init);

var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
var person;

function init(){
    console.log("Init");

    // get person
    $("#registration-student").click(function () {
        person = "Student";
    });
    $("#registration-teacher").click(function () {
        person = "Teacher";
    });

    // Student check
    $("#emailFieldStudent").focusout(checkEmail);
    $("#passwordFieldStudent").focusout(checkPassword);
    $("#confirmPasswordFieldStudent").focusout(checkConfirmPassword);
    $("#registration-student-form").submit(Validate);

    // Teacher check
    $("#emailFieldTeacher").focusout(checkEmail);
    $("#passwordFieldTeacher").focusout(checkPassword);
    $("#confirmPasswordFieldTeacher").focusout(checkConfirmPassword);
    $("#registration-teacher-form").submit(Validate);
}

function checkEmail(){
    console.log("checkEmail  " + $("#emailField" + person));

    var email = $("#emailField" + person).val();
    var availableEmail = false;
    if(emailRegex.test(email)) {
        $.ajax({ url: "/api/users/checkEmail", async: false, type: "get", data: "email=" + email})
            .done(function(response) {
                if(response){
                    $("#checkEmailResult" + person).text("Емейл існує");
                    availableEmail = false;
                } else {
                    $("#checkEmailResult" + person).text("");
                    availableEmail = true;
                }
            });
    } else {
        $("#checkEmailResult" + person).text("Емейл введено невірно.");
        availableEmail = false;
    }
    return availableEmail;
}

function checkPassword() {
    console.log("checkPassword");

    var password = $("#passwordField" + person).val();
    if(password == "") {
        $("#emptyPasswordResult" + person).text("Це поле не може бути пустим.");
        return false;
    } else {
        $("#emptyPasswordResult" + person).text("");
        return true;
    }
}

function checkConfirmPassword() {
    console.log("checkConfirmPassword");

    var password = $("#passwordField" + person).val();
    var confirmPassword = $("#confirmPasswordField" + person).val();
    if(password != confirmPassword) {
        $("#confirmPasswordResult" + person).text("Паролі не співпадають.")
        return false;
    } else {
        $("#confirmPasswordResult" + person).text("")
        return true;
    }
}

function Validate(){
    console.log("Validate");

    if(!checkEmail()){
        $("#emailField" + person).focus();
        return false;
    } else if(!checkPassword()) {
        $("#passwordField" + person).focus();
        return false;
    } else if(!checkConfirmPassword()) {
        $("#confirmPasswordField" + person).focus();
        return false;
    }
    return true;
}
