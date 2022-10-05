function validation_email() {
    let email = $("#new_user_email").val()
    let pattern = /^\S+@\S+\.\S+$/;
    return pattern.test(email);
}