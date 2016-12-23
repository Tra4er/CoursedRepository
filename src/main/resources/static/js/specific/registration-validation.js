$(document).ready(init);

var emailRegex = /^[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
var passRegex = /((?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})/;
var phoneNumberRegex = /^(\+380)[0-9]{9}/;
var person;

function init(){

    // get person
    $("#registration-student").click(function () {
        person = "student";
    });
    $("#registration-teacher").click(function () {
        person = "teacher";
    });

    // Student check
    $("#phoneNumber-student").focusout(checkPhoneNumber);
    $("#emailField-student").focusout(checkEmail);
    $("#passwordField-student").focusout(checkPassword);
    $("#confirmPasswordField-student").focusout(checkConfirmPassword);

    // Teacher check
    $("#phoneNumber-teacher").focusout(checkPhoneNumber);
    $("#emailField-teacher").focusout(checkEmail);
    $("#passwordField-teacher").focusout(checkPassword);
    $("#confirmPasswordField-teacher").focusout(checkConfirmPassword);
}

function checkFirstName() {
}

function checkPhoneNumber() {
    var number = $("#phoneNumber-" + person).val();
    var resultMessage = $("#result-phoneNumber-" + person);
    if(number == "+380") {
        resultMessage.text("Це поле обов'язкове.")
    } else if(!phoneNumberRegex.test(number)) {
        resultMessage.text("Номер телефону введено невірно.");
    } else {
        resultMessage.text("");
    }
}

function checkEmail(){
    var email = $("#emailField-" + person).val();
    var resultMessage = $("#result-emailField-" + person);
    var availableEmail = false;
    if(email == "") {
        resultMessage.text("Це поле не може бути пустим.");
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
        resultMessage.text("Це поле не може бути пустим.");
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
    if(!checkEmail()){
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
