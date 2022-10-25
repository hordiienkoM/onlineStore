function deleteAllProducts() {
    $.ajax({
        url: "http://localhost:8080/v1/admin/products",
        type: "DELETE",
        success: function () {
            $("#products_deleted_fail").hide();
            $("#products_deleted_successfully").show();
            turn_current_products()
        },
        error: function (data) {
            $("#products_deleted_successfully").hide();
            let error_element = $("#products_deleted_fail")
            let error_info = JSON.parse(data.responseText);
            error_element.val(error_info.error);
            error_element.show();
        }
    });
}