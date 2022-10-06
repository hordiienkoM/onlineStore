function registration(username, password) {
    let user = {
        "username": username,
        "password": password
    }
    $.ajax({
        url: "http://localhost:8080/v1/users",
        type: "POST",
        data: JSON.stringify(user),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: "text",
        success: function (data) {
            alert(data);
            $("#registration_form").hide();
            $("#confirm_form").show();
        },
        error: function (e) {
            let element_error = $("#message_registration_error")
            element_error.text(e.responseText);
            element_error.show()
        }
    })
}