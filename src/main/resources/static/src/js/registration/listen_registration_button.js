$("#registration_button").click(function (e) {
    e.preventDefault();
    hide_all_registration_error();
    let email = $("#new_user_email");
    let password = $("#new_user_password");
    let validationPassword = $("#new_user_validation_password");
    if(!validation_email()){
        $("#message_uncorrected_email").show();
        email.val("");
    } else if (!validation_password()) {
        $("#message_uncorrected_password").show();
        password.val("");
        validationPassword.val("");
    } else if (!validation_confirm_password()) {
        $("#message_passwords_not_match").show();
        password.val("");
        validationPassword.val("");
    } else {
        registration(email.val(), password.val());
        hide_all_registration_error();
    }
});




