$(document).ready(init);


function init(){
    console.log("init");

    $("#emailField").focusout(checkEmail);
    $("#passwordField").focusout(chechEmptyPassword);
    $("#confirmPasswordField").focusout(checkConfirmPassword);

}

function checkEmail(){
    var email = $("#emailField").val();
    $.ajax({ url: "/api/user/checkEmail", type: "get", data: "email="+email})
        .done(function(response) {
            if(response){
                $("#checkEmailResult").text("Email exist");
            }
            else{
                $("#checkEmailResult").text("");
            }
        });
}

function chechEmptyPassword() {
    var password = $("#passwordField").val();
    if(password == "") {
        $("#emptyPasswordResult").text("This field can't be empty.");
    } else {
        $("#emptyPasswordResult").text("");
    }
}

function checkConfirmPassword() {
    var password = $("#passwordField").val();
    var confirmPassword = $("#confirmPasswordField").val();
    if(password != confirmPassword) {
        $("#confirmPasswordResult").text("Passwords do not match.")
    } else {
        $("#confirmPasswordResult").text("")
    }
}