function add_new_product(description) {
    $.ajax({
        url: "http://localhost:8080/v1/products?description=" + description,
        type: "POST",
        success: function () {
            $("#add_product_error").text("")
            alert("Product '" + description + "' has been added");
            $("#next_description").val("")
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