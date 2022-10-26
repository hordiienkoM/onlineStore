function product_buttons_manager() {
    listen_product_delete_buttons();
    listen_product_edit_buttons();
    listen_download_default_products();
    listen_delete_all_products();
}

//listen functions
function listen_product_delete_buttons() {
    $(".product_delete").click(function (e) {
        e.preventDefault();
        let product_id = $(this).val();
        deleteProductById(product_id);
    });
}

function listen_delete_all_products() {
    $("#delete_all_products").click(function (e) {
        e.preventDefault();
        deleteAllProducts();
    });
}

function listen_download_default_products() {
    $("#download_default_products").click(function (e) {
        e.preventDefault();
        downloadDefaultProducts();
    });
}

function listen_product_edit_buttons() {
    $(".product_edit").click(function (e) {
        e.preventDefault();
        let product_id_element = $("#edit_product_id");
        product_id_element.text($(this).val());
        get_product_info(product_id_element.text());
        $("#edit_product_table").show();
        listen_update_product_button();
    });
}

function listen_update_product_button() {
    $("#update_product_button").click(function (e) {
        e.preventDefault();
        let new_product_info = {
            id: $("#edit_product_id").text(),
            description: $("#update_description").val(),
            brand: $("#update_brand").val(),
            category: $("#update_category").val(),
            price: $("#update_price").val()
        };
        updateProduct(new_product_info);
    });
}
