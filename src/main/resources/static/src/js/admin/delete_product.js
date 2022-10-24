function deleteProductById(productId) {
    $.ajax({
        url: "http://localhost:8080/v1/products?productId=" + productId,
        method: "DELETE",
        success: function (data) {
            turn_current_products();
        }
    });
}

