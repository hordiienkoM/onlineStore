let current_users_page = 0;
let page_users_size = 3;
let sort_users_field = "id";
let last_users_page = false;
let first_users_page = false;

//functions turn page
function turn_next_users() {
    current_users_page++;
    get_users_page();
}

function turn_previous_users() {
    current_users_page--;
    get_users_page();
}

function turn_current_users() {
    get_users_page();
}

turn_next_users();

//functions listen buttons
$(document).ready(function () {
    $("#next_users_page").click(function () {
        if (last_users_page !== true) {
            turn_next_users();
        }
    })
})

$(document).ready(function () {
    $("#previous_users_page").click(function () {
        if (first_users_page !== true) {
            turn_previous_users();
        }
    })
})

$(document).ready(function () {
    $("#button_add_user").click(function () {
        let username = $("#next_user_username").val();
        add_new_user(username);
    })
})

//functions with logic to work with a users page
function get_users_page() {
    $('#users_table tbody').empty();
    $("#current_users_page").replaceWith("<a id='current_users_page'> " + current_users_page + " </a>");
    $.ajax({
        url: "http://localhost:8080/v1/users",
        method: "get",
        async: false,
        data: {
            "page": (current_users_page - 1),
            "size": page_users_size,
            "sortField": sort_users_field
        },
        dataType: "json",
        success: function (data) {
            show_users_page(data);
            user_buttons_manager();
        }
    });
}

function show_users_page(data) {
    for (let i = 0; i < data.content.length; i++) {
        const user_id = data.content[i].id;
        const username = data.content[i].username;
        const enabled = data.content[i].enabled;
        const ordersAmount = data.content[i].ordersAmount;
        const value_user_id = "value = \"" + user_id + "\"";
        const new_row = '<tr>' +
            '<td class="username">' + username + '</td>' +
            '<td>' + enabled + '</td>' +
            '<td>' + ordersAmount + '</td>' +
            '<td><button class="user_delete"' + value_user_id + '>delete</button></td>' +
            '</tr>';
        $('#users_table tbody').append(new_row);
    }
    last_users_page = data.last;
    first_users_page = data.first;
}

