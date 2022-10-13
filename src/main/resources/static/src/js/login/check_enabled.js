$("#login_button").click(function (e) {
    e.preventDefault();
    check_enabled();
});

function check_enabled() {
    let username = $("#username").val();
    $.ajax({
        url: "http://localhost:8080/v1/users/enable",
        type: "GET",
        async: false,
        dataType: "json",
        data: {
            "username": username
        },
        success: function (data) {
            if (data === true) {
                let button = $("#login_button");
                button.unbind("click");
                button.trigger("click");
            } else {
                window.location = "/confirm_registration";
            }
        },
        error: function (data, textStatus, jqXHR) {
            $("#error_message").show()

        }
    })
}
