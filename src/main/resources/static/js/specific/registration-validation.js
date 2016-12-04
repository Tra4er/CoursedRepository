$(document).ready(init);

var emailRegex = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;

function init(){

    $("#emailField").focusout(checkEmail);
    $("#passwordField").focusout(checkPassword);
    $("#confirmPasswordField").focusout(checkConfirmPassword);

    $("#submit").submit(Validate);

}

function checkEmail(){
    var email = $("#emailField").val();
    var availableEmail = false;
    if(emailRegex.test(email)) {
        $.ajax({ url: "/api/user/checkEmail", async: false, type: "get", data: "email="+email})
            .done(function(response) {
                if(response){
                    $("#checkEmailResult").text("Емейл існує");
                    availableEmail = false;
                } else {
                    $("#checkEmailResult").text("");
                    availableEmail = true;
                }
            });
    } else {
        $("#checkEmailResult").text("Емейл введено невірно.");
        availableEmail = false;
    }
    return availableEmail;
}

function checkPassword() {
    var password = $("#passwordField").val();
    if(password == "") {
        $("#emptyPasswordResult").text("Це поле не може бути пустим.");
        return false;
    } else {
        $("#emptyPasswordResult").text("");
        return true;
    }
}

function checkConfirmPassword() {
    var password = $("#passwordField").val();
    var confirmPassword = $("#confirmPasswordField").val();
    if(password != confirmPassword) {
        $("#confirmPasswordResult").text("Паролі не співпадають.")
        return false;
    } else {
        $("#confirmPasswordResult").text("")
        return true;
    }
}

function Validate(){
    if(!checkEmail()){
        $("#emailField").focus();
        return false;
    } else if(!checkPassword()) {
        $("#passwordField").focus();
        return false;
    } else if(!checkConfirmPassword()) {
        $("#confirmPasswordField").focus();
        return false;
    }
    return true;
}
