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
        let product_id = $(this).val();
        let newDescription = prompt($("#edit_message_multilingual_description").text());
        let newPrice = prompt($("#edit_message_multilingual_price").text());
        updateProduct(product_id, newDescription, newPrice);
    });
}