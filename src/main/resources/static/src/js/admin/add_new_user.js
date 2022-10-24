function add_new_user(username) {
    $.ajax({
        url: "http://localhost:8080/v1/users/user_blank?username=" + username,
        type: "POST",
        success: function () {
            $("#add_user_error").text("")
            alert("User " + username + " has been added");
            $("#next_user_username").val("")
        },
        error: function (data) {
            let error_element = $("#add_user_error")
            let error_info = JSON.parse(data.responseText);
            $("#next_user_username").val("")
            error_element.text(error_info.error)
        }
    });
}