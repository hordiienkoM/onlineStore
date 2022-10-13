$("#confirm_button").click(function (e) {
    e.preventDefault();
    let pass = $("#received_password").val();
    if (!validationConfirmPassword(pass)) {
        hide_validation_errors();
        $("#message_uncorrected_token").show();
    } else {
        sendTokenToServer(pass)
    }
});

function validationConfirmPassword(pass) {
    let validate = /^[0-9]{6}$/;
    return validate.test(pass);
}

function sendTokenToServer(pass) {
    let username = $("#new_user_email").val();
    let data = {
        "username": username,
        "token": pass
    }
    $.ajax({
        url: "http://localhost:8080/v1/users/confirm",
        type: "POST",
        data: JSON.stringify(data),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
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

function hide_validation_errors() {
    $("#message_uncorrected_token").hide();
    $("#message_token_not_match").hide;
}