function updateProduct(new_product_info) {
    $.ajax({
        url: "http://localhost:8080/v1/products",
        type: "PUT",
        data: JSON.stringify(new_product_info),
        async: false,
        dataType: 'text',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function () {
            $("#edit_product_error").hide();
            turn_current_products();
            $("#edit_product_table").hide();
        },
        error: function (data) {
            let error_element = $("#edit_product_error");
            let error_info = JSON.parse(data.responseText);
            error_element.text(error_info.error);
            error_element.show();
        }
    });
}