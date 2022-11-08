let current_page = 0;
let page_size = 5;
let sort_field = "id";
let last_product_page = false;

//functions turn page

function turn_next_product() {
    current_page++;
    getProductsPage();
}

function turn_previous_product() {
    current_page--;
    getProductsPage();
}

turn_next_product();
listen_create_new_order()


let products_to_order = [];

//logic functions
function getProductsPage() {
    $('#products_table tbody').empty();
    $("#current_page").replaceWith("<a id='current_page'> " + (current_page) + " </a>");
    $.ajax({
        url: "http://localhost:8080/v1/products",
        data: {
            "page": (current_page - 1),
            "size": page_size,
            "sortField": sort_field
        },
        method: "get",
        dataType: "json",
        success: function (data) {
            showProductsTable(data);
        }
    });
}

function showProductsTable(data) {
    for (let i = 0; i < data.content.length; i++) {
        const product_id = data.content[i].product_id;
        const description = data.content[i].description;
        const brand = data.content[i].brand;
        const category = data.content[i].category;
        const price = data.content[i].price;
        const value_product_id = "value = \"" + product_id + "\"";
        const product_row_id = "id= \"product_row_id_" + product_id + "\"";
        const last_td = '<tr ' + product_row_id + '><td>' + description + '</td>' +
            '<td>' + brand + '</td>' +
            '<td>' + category + '</td>' +
            '<td>' + price + '</td>' +
            '<td><input type="text" class="products_amount" placeholder="0"></td>' +
            '<td><button class="add_product" ' + value_product_id + '>add</button></td>' +
            '</tr>';
        $('#products_table tbody').append(last_td);
    }
    last_product_page = data.last;
    listen_product_add_buttons();
}

function listen_product_add_buttons() {
    $(".add_product").click(function (e) {
        e.preventDefault();
        let product_id = $(this).val();
        let element_amount = "#product_row_id_" + product_id + " .products_amount";
        let product_amount = $(element_amount).val();
        let orderProduct = {
            amount: product_amount,
            productId: product_id
        };
        products_to_order.push(orderProduct)
    });
}

$(document).ready(function () {
    $("#next_product_page").click(function () {
        if (last_product_page !== true) {
            turn_next_product();
        }
    })
})

$(document).ready(function () {
    $("#previous_product_page").click(function () {
        if (current_page - 2 >= 0) {
            turn_previous_product();
        }
    })
})