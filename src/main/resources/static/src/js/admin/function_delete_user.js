function deleteUserById(userId) {
    $.ajax({
        url: "http://localhost:8080/v1/users?id=" + userId,
        method: "DELETE",
        success: function (data) {
            turn_current_users();
        }
    });
}