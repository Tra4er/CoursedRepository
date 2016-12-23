$(document).ready(init);

var namesRegex = /^[А-ЯІЄҐ][а-яієґ]{1,15}/;
var emailRegex = /^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
var passRegex = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})/;
var phoneNumberRegex = /^(\+380)[0-9]{9}/;
var person;

var emptyError = "Це поле не може бути пустим.";
var incorrectError = "Некоректно введене поле.";

function init(){

    // get person
    $("#registration-student").click(function () {
        person = "student";
    });
    $("#registration-teacher").click(function () {
        person = "teacher";
    });

    // Student check

    $("#firstNameField-student").focusout(checkFirstName);
    $("#lastNameField-student").focusout(checkLastName);
    $("#patronymicField-student").focusout(checkPatronymic);
    $("#phoneNumber-student").focusout(checkPhoneNumber);
    $("#emailField-student").focusout(checkEmail);
    $("#passwordField-student").focusout(checkPassword);
    $("#confirmPasswordField-student").focusout(checkConfirmPassword);

    // Teacher check
    $("#firstNameField-teacher").focusout(checkFirstName);
    $("#lastNameField-teacher").focusout(checkLastName);
    $("#patronymicField-teacher").focusout(checkPatronymic);
    $("#phoneNumber-teacher").focusout(checkPhoneNumber);
    $("#emailField-teacher").focusout(checkEmail);
    $("#passwordField-teacher").focusout(checkPassword);
    $("#confirmPasswordField-teacher").focusout(checkConfirmPassword);
}

function checkFirstName() {
    var name = $("#firstNameField-" + person).val();
    var resultMessage = $("#result-firstNameField-" + person);
    if(name == "") {
        resultMessage.text(emptyError);
        return false;
    } else if(!namesRegex.test(name)) {
        resultMessage.text(incorrectError);
        return false;
    } else {
        resultMessage.text("");
        return true;
    }
}

function checkLastName() {
    var name = $("#lastNameField-" + person).val();
    var resultMessage = $("#result-lastNameField-" + person);
    if(name == "") {
        resultMessage.text(emptyError);
        return false;
    } else if(!namesRegex.test(name)) {
        resultMessage.text(incorrectError);
        return false;
    } else {
        resultMessage.text("");
        return true;
    }
}

function checkPatronymic() {
    var name = $("#patronymicField-" + person).val();
    var resultMessage = $("#result-patronymicField-" + person);
    if(name == "") {
        resultMessage.text(emptyError);
        return false;
    } else if(!namesRegex.test(name)) {
        resultMessage.text(incorrectError);
        return false;
    } else {
        resultMessage.text("");
        return true;
    }
}

function checkPhoneNumber() {
    var number = $("#phoneNumber-" + person).val();
    var resultMessage = $("#result-phoneNumber-" + person);
    if(number == "+380") {
        resultMessage.text("Це поле обов'язкове");
        return false;
    } else if(!phoneNumberRegex.test(number)) {
        resultMessage.text(incorrectError);
        return false;
    } else {
        resultMessage.text("");
        return true;
    }
}

function checkEmail(){
    var email = $("#emailField-" + person).val();
    var resultMessage = $("#result-emailField-" + person);
    var availableEmail = false;
    if(email == "") {
        resultMessage.text(emptyError);
        availableEmail = false;
    } else if(!emailRegex.test(email)) {
        resultMessage.text("Емейл введено невірно.");
        availableEmail = false;
    } else { // Passed regex test
        $.ajax({ url: "/api/users/checkEmail", async: false, type: "get", data: "email=" + email})
            .done(function(response) {
                if(response){
                    resultMessage.text("Емейл існує");
                    availableEmail = false;
                } else {
                    resultMessage.text("");
                    availableEmail = true;
                }
            });
    }
    return availableEmail;
}

function checkPassword() {
    var password = $("#passwordField-" + person).val();
    var resultMessage = $("#result-passwordField-" + person);
    if(password == "") {
        resultMessage.text(emptyError   );
        return false;
    } else if (!passRegex.test(password)) {
        resultMessage.text("Занадто простий пароль.");
        return false;
    } else { // Passed regex test
        resultMessage.text("");
        return true;
    }
}

function checkConfirmPassword() {
    var password = $("#passwordField-" + person).val();
    var confirmPassword = $("#confirmPasswordField-" + person).val();
    var resultMessage = $("#result-confirmPasswordField-" + person);
    if(password != confirmPassword) {
        resultMessage.text("Паролі не співпадають.")
        return false;
    } else {
        resultMessage.text("")
        return true;
    }
}

function Validate(){
    if (!checkFirstName()) {
        $("#firstNameField-" + person).focus();
        return false;
    } else if(!checkLastName()) {
        $("#lastNameField-" + person).focus();
        return false;
    } else if (!checkPatronymic()) {
        $("#patronymicField-" + person).focus();
        return false;
    } else if(!checkEmail()){
        $("#emailField-" + person).focus();
        return false;
    } else if(!checkPassword()) {
        $("#passwordField-" + person).focus();
        return false;
    } else if(!checkConfirmPassword()) {
        $("#confirmPasswordField-" + person).focus();
        return false;
    }
    return true;
}
