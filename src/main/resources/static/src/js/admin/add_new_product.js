function add_new_product(new_product_info) {
    $.ajax({
        url: "http://localhost:8080/v1/products",
        type: "POST",
        data: JSON.stringify(new_product_info),
        dataType: 'text',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function () {
            $("#add_product_error").text("")
            alert("Product '" + new_product_info.description + "' has been added");
            $("#next_description").val("")
            $("#next_price").val("")
            turn_current_products()
        },
        error: function (data) {
            let error_element = $("#add_product_error")
            let error_info = JSON.parse(data.responseText);
            $("#next_description").val("")
            error_element.text(error_info.error)
        }
    });
}