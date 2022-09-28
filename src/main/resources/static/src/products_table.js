let current_page = 0;
let page_size = 2;
let sort_field = "id";
let last_product_page = false;
function turn_next_product() {
    current_page++;
    $('#products_table tbody').empty();
    $("#current_page_products").replaceWith("<a id='current_page_products'> " + current_page + " </a>");
    let pageable = {
        "page": current_page - 1,
        "size": page_size,
        "sortField": sort_field
    }
    $.ajax({
        url: "http://localhost:8080/v1/products",
        method: "get",
        async: false,
        data: JSON.stringify(pageable),
        dataType: "application/json",
        success: function (data){
            for (let i = 0; i < data.content.length; i++) {
                const product_id = data.content[i].product_id;
                const description = data.content[i].description;
                const value_product_id = "value = \"" + product_id + "\"";
                const product_row_id = "id= \"product_row_id_" + product_id + "\"";
                const last_td = '<tr ' + product_row_id + '><td>' + description + '</td>' +
                    '<td><input type="text" class="products_amount" placeholder="0"></td>' +
                    '<td><button class="add_product" ' + value_product_id + '>add</button></td>' +
                    '</tr>';
                $('#products_table tbody').append(last_td);
            }
            last_product_page = data.last;
            listen_product_add_buttons();
        }
    });
}

turn_next_product();
listen_create_new_order()

function turn_previous_product() {
    $('#products_table tbody').empty();
    current_page--;
    $("#current_page").replaceWith("<a id='current_page'> " + (current_page) + " </a>");
    $.ajax({
        url: "http://localhost:8080/v1/products?page=" + (current_page - 1) + "&pageSize= " +
            page_size + "&sortField=" + sort_field,
        method: "get",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: "json",
        success: function (data){
            for (let i = 0; i < data.content.length; i++) {
                const product_id = data.content[i].product_id;
                const description = data.content[i].description;
                const value_product_id = "value = \"" + product_id + "\"";
                const product_row_id = "id= \"product_row_id_" + product_id + "\"";
                const last_td = '<tr ' + product_row_id + '><td>' + description + '</td>' +
                    '<td><input type="text" class="products_amount" placeholder="0"></td>' +
                    '<td><button class="add_product" ' + value_product_id + '>add</button></td>' +
                    '</tr>';
                $('#products_table tbody').append(last_td);
            }
            last_product_page = data.last;
            listen_product_add_buttons();
        }
    });
}

let products_to_order = [];

function listen_product_add_buttons() {
    $(".add_product").click(function (e) {
        e.preventDefault();
        let product_id = $(this).val();
        let element_amount = "#product_row_id_" + product_id + " .products_amount";
        let product_amount = $(element_amount).val();
        let orderProduct = {
            amount: product_amount,
            product: {id: product_id}
        };
        products_to_order.push(orderProduct)
    });
}

function listen_create_new_order() {
    $("#create_button").click(function (e) {
        e.preventDefault();
        let userId = $("#current_user_id").text();
        let new_order = {
            status: "new",
            createDate: new Date($.now()),
            address: $('#user_address').val(),
            orderProduct: products_to_order
        };
        alert(JSON.stringify(new_order));
        $.ajax({

            url: "http://localhost:8080/v1/orders?userId=" + userId,
            type: "POST",
            data: JSON.stringify(new_order),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data){
                alert(JSON.stringify("Order was created"))
            }
        });
    });
}

$(document).ready(function (){
    $("#next_product_page").click(function () {
        if (last_product_page !== true) {
            turn_next_product();
        }
    })
})

$(document).ready(function (){
    $("#previous_product_page").click(function () {
        if (current_page - 2 >= 0) {
            turn_previous_product();
        }
    })
})