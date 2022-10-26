function get_product_info(productId) {
    $.ajax({
        url: "http://localhost:8080/v1/products/info?id=" + productId,
        method: "get",
        dataType: "json",
        success: function (data) {
            const brand = data.brand.toUpperCase().replaceAll(" ", "_")
            const category = data.category.toUpperCase().replaceAll(" ", "_")
            $("#update_description").val(data.description);
            const $brand_list = document.querySelector('#update_brand');
            $brand_list.value = brand;
            const $category_list = document.querySelector('#update_category');
            $category_list.value = category;
            $("#update_price").val(data.price);
        }
    });
}