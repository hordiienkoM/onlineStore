$("#registration_button").click(function (e) {
    e.preventDefault();
    if(!validation_email()){
        $("#message_uncorrected_email").show();
        $("#new_user_email").val("");
    } else if (!validation_password()) {
        $("#message_uncorrected_password").show();
        $("#new_user_password").val("");
        $("#new_user_validation_password").val("");
    } else if (!validation_confirm_password()) {
        $("#message_passwords_not_match").show();
        $("#new_user_password").val("");
        $("#new_user_validation_password").val("");
    } else {
        alert("it's ok");
    }
});
