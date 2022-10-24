function product_buttons_manager() {
    listen_product_delete_buttons();
    listen_product_edit_buttons();
}

//listen functions
function listen_product_delete_buttons() {
    $(".product_delete").click(function (e) {
        e.preventDefault();
        let product_id = $(this).val();
        deleteProductById(product_id);
    });
}

function listen_product_edit_buttons() {
    $(".product_edit").click(function (e) {
        e.preventDefault();
        let product_id = $(this).val();
        var newDescription = prompt($("#edit_message_multilingual").text());
        updateProduct(product_id, newDescription);
    });
}