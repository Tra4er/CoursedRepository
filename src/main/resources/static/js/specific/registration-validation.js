$(document).ready(init);

var emailRegex = /^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
var passRegex = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})/;
var person;

function init(){

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

    // Teacher check
    $("#emailFieldTeacher").focusout(checkEmail);
    $("#passwordFieldTeacher").focusout(checkPassword);
    $("#confirmPasswordFieldTeacher").focusout(checkConfirmPassword);
}

function checkFirstName() {
}

function checkEmail(){
    var email = $("#emailField" + person).val();
    var availableEmail = false;
    if(email == "") {
        $("#checkEmailResult" + person).text("Це поле не може бути пустим.");
        availableEmail = false;
    } else if(!emailRegex.test(email)) {
        $("#checkEmailResult" + person).text("Емейл введено невірно.");
        availableEmail = false;
    } else { // Passed regex test
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
    }
    return availableEmail;
}

function checkPassword() {
    var password = $("#passwordField" + person).val();
    if(password == "") {
        $("#passwordResult" + person).text("Це поле не може бути пустим.");
        return false;
    } else if (!passRegex.test(password)) {
        $("#passwordResult" + person).text("Занадто простий пароль.");
        return false;
    } else { // Passed regex test
        $("#passwordResult" + person).text("");
        return true;
    }
}

function checkConfirmPassword() {
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
