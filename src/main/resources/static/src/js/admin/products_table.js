let current_page = 0;
let page_size = 5;
let sort_field = "id";
let last_product_page = false;
let first_product_page = false;

//functions turn page

function turn_next_products() {
    current_page++;
    getProductPage();
}

function turn_previous_products() {
    current_page--;
    getProductPage();
}

function turn_current_products() {
    getProductPage();
}

turn_next_products();

//functions listen buttons

$(document).ready(function () {
    $("#next_product_page").click(function () {
        if (last_product_page !== true) {
            turn_next_products();
        }
    })
})

$(document).ready(function () {
    $("#previous_product_page").click(function () {
        if (first_product_page !== true) {
            turn_previous_products();
        }
    })
})

$(document).ready(function () {
    $("#add_product").click(function () {
        let new_product_info = {
            description: $("#next_description").val(),
            brand: $("#next_brand").val(),
            category: $("#next_category").val(),
            price: $("#next_price").val()
        };
        add_new_product(new_product_info);
    })
})

//functions with logic to work with a products page
function getProductPage() {
    $('#products_table tbody').empty();
    $("#current_products_page").replaceWith("<a id='current_products_page'> " + current_page + " </a>");
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
            showProductPage(data);
        }
    });
}

function showProductPage(data) {
    for (let i = 0; i < data.content.length; i++) {
        const product_id = data.content[i].product_id;
        const description = data.content[i].description;
        const brand = data.content[i].brand;
        const category = data.content[i].category;
        const price = data.content[i].price;
        const value_product_id = "value = \"" + product_id + "\"";
        const last_td = '<tr><td>' + description + '</td>' +
            '<td>' + brand + '</td>' +
            '<td>' + category + '</td>' +
            '<td>' + price + '</td>' +
            '<td><button class="product_edit"' + value_product_id + '>edit</button></td>' +
            '<td><button class="product_delete"' + value_product_id + '>delete</button></td>' +
            '</tr>';
        $('#products_table tbody').append(last_td);
    }
    last_product_page = data.last;
    first_product_page = data.first;
    product_buttons_manager();
}