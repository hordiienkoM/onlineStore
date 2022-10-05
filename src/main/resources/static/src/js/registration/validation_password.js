function validation_password() {
    let pattern = /^[a-zA-Z0-9_-]{5,15}$/;
    let password = $("#new_user_password").val();
    return pattern.test(password);
}

function validation_confirm_password() {
    let pass = $("#new_user_password").val();
    let validationPass = $("#new_user_validation_password").val();
    return pass === validationPass;
}