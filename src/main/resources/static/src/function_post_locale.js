$.ajax({
    url: "http://localhost:8080/v1/users/locale",
    type: "POST",
    success: function () {
    },
    error: function (data) {
        alert(JSON.stringify(data.error))
    }
});