function user_buttons_manager() {
    listen_user_delete_buttons()
}

//listen functions
function listen_user_delete_buttons() {
    $(".user_delete").click(function (e) {
        e.preventDefault();
        let user_id = $(this).val();
        deleteUserById(user_id);
    });
}