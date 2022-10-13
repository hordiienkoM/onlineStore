let order_products_amount

function listen_update_current_order() {
    $("#update_current_order").click(function (e) {
        e.preventDefault();
        let new_order_info = {
            createDate: new Date($.now()),
            address: $('#current_user_address').val(),
            orderProduct: update_products_list()
        };
        let orderId = $("#current_edit_order_id").text();
        $.ajax({
            url: "http://localhost:8080/v1/orders/" + orderId,
            type: "PUT",
            data: JSON.stringify(new_order_info),
            dataType: 'text',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                alert("Order was updated");
                $("#order_edit_view").hide();
            }
        });
    });
}

function update_products_list() {
    let products_list = [];
    for (let i = 0; i < order_products_amount; i++) {
        let name_product_index = "#update_product_id_" + i;
        let name_amount_index = "#new_amount_" + i;
        let product_order = {
            amount: $(name_amount_index).val(),
            product: {"id": $(name_product_index).text()}
        };
        products_list.push(product_order);
    }
    return products_list;
}