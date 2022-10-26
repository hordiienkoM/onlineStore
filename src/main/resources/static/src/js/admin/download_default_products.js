function downloadDefaultProducts() {
    $.ajax({
        url: "http://localhost:8080/v1/admin/products",
        async: false,
        type: "POST",
        success: function () {
            $("#products_downloaded_fail").hide();
            $("#products_downloaded_successfully").show();
            $("#products_deleted_successfully").hide
            turn_current_products()
        },
        error: function (data) {
            $("#products_downloaded_successfully").hide();
            let error_element = $("#products_downloaded_fail")
            let error_info = JSON.parse(data.responseText);
            error_element.val(error_info.error);
            error_element.show();
        }
    });
}