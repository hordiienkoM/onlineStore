const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
let data = {
    "username": urlParams.get("username"),
    "token": urlParams.get("token")
}

$.ajax({
    url: "http://localhost:8080/v1/users/confirm",
    type: "POST",
    async: false,
    data: JSON.stringify(data),
    headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
    },
    dataType: "text",
    success: function () {
        $("#confirmation_progress").hide();
        $("#registration_completed_message").show();
    },
    error: function (e) {
        alert(JSON.stringify(e))
    }
});