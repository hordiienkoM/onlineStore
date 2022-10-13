function getCurrentUserId() {
    let id;
    $.ajax({
        async: false,
        url: "http://localhost:8080/v1/sessions",
        method: "get",
        success: function (data) {
            id = data
        }
    });
    return id;
}