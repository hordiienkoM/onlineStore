$("#confirm_button").click(function (e) {
    e.preventDefault();
    let pass = $("#received_password").val();
    if (!validationConfirmPassword(pass)) {
        hide_validation_errors();
        $("#message_uncorrected_token").show();
    } else {
        sendPassToServer(pass)
    }
});

function validationConfirmPassword(pass) {
    let validate = /^[0-9]{6}$/;
    return validate.test(pass);
}

function sendPassToServer(pass) {
    let username = $("#new_user_email").val();
    $.ajax({
        url: "http://localhost:8080/v1/users/confirm",
        type: "POST",
        data: {
            "username": username,
            "token": pass
        },
        dataType: "text",
        success: function (data) {
            hide_validation_errors()
            $("#confirm_form").hide();
            $("#registration_completed_message").show();
        },
        error: function (e) {
            hide_validation_errors()
            $("#message_token_not_match").show()
        }
    })
}

function hide_validation_errors(){
    $("#message_uncorrected_token").hide();
    $("#message_token_not_match").hide;
}