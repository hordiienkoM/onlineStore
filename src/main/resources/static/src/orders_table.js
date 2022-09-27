let user_id = getCurrentUserId();
let current_orders_page = 0;
let page_orders_size = 3;
let sort_orders_field = "id";
let last_orders_page = false;
let first_orders_page = false;
let order_content_size = 0
function turn_next_orders() {
    current_orders_page++;
    $('#orders_table tbody').empty();
    $("#current_orders_page").replaceWith("<a id='current_orders_page'> " + current_orders_page + " </a>");
    $.ajax({
        url: "http://localhost:8080/v1/orders",
        method: "get",
        data: {
            "userId": user_id,
            "page": (current_orders_page - 1),
            "pageSize": page_orders_size,
            "sortField": sort_orders_field
        },
        dataType: "json",
        success: function (data){
            show_orders_page(data)
            order_content_size = data.content.length;
            button_manager();
        }
    });

}
turn_next_orders();
$("#current_user_id").text(getCurrentUserId());

$(document).ready(function (){
    $("#next_orders_page").click(function () {
        if (last_orders_page !== true) {
            turn_next_orders();
            $("#order_view").hide();
        }
    })
})

function turn_previous_orders() {
    current_orders_page--;
    $('#orders_table tbody').empty();
    $("#current_orders_page").replaceWith("<a id='current_orders_page'> " + current_orders_page + " </a>");
    $.ajax({
        url: "http://localhost:8080/v1/orders",
        method: "get",
        data: {
            "userId": user_id,
            "page": (current_orders_page - 1),
            "pageSize": page_orders_size,
            "sortField": sort_orders_field
        },
        dataType: "json",
        success: function (data){
            show_orders_page(data);
            order_content_size = data.content.length;
            button_manager();
        }
    });
}

$(document).ready(function (){
    $("#previous_orders_page").click(function () {
        if (first_orders_page !== true) {
            turn_previous_orders();
            $("#order_view").hide();
        }
    })
})

function show_orders_page(data) {
    for (let i = 0; i < data.content.length; i++) {
        const order_id = data.content[i].id;
        const order_status = data.content[i].status;
        const this_date = new Date(data.content[i].createDate)
        const order_date = this_date.toLocaleDateString() + " " +
            this_date.getHours() + ":" + this_date.getMinutes();
        const value_order_id = "value = \"" + order_id + "\"";
        const order_row_id = "id= \"order_row_id_" + order_id + "\"";
        const order_address = data.content[i].address;
        const new_row = '<tr ' + order_row_id + '><td>' + order_id + '</td>' +
            '<td class="order_status">' + order_status + '</td>' +
            '<td>' + order_date + '</td>' +
            '<td>' + order_address + '</td>' +
            '<td><button class="order_view" ' + value_order_id + '>view</button></td>' +
            '<td><button class="order_edit"' + value_order_id + '>edit</button></td>' +
            '<td><button class="order_delete"' + value_order_id + '>delete</button></td>' +
            '</tr>';
        $('#orders_table tbody').append(new_row);
    }
    last_orders_page = data.last;
    first_orders_page = data.first;
}

function button_manager() {
    listen_order_view_buttons();
    listen_order_edit_buttons();
    listen_order_delete_buttons()
}

function listen_order_view_buttons(){
    $(".order_view").click(function(e) {
        e.preventDefault();
            let order_id = $(this).val();
            $("#order_view").show();
            $("#current_order_id ").text(order_id);
            showOrderInfo();
        });
    }

function listen_order_edit_buttons(){
    $(".order_edit").click(function(e) {
        e.preventDefault();
        alert(122121)
    });
}

function listen_order_delete_buttons(){
    $(".order_delete").click(function(e) {
        e.preventDefault();
        let order_id = $(this).val();
        deleteOrderById(order_id);
    });
}
