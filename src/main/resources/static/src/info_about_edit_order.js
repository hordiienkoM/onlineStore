let edit_order_id;

function showEditOrderInfo() {
    edit_order_id = $("#current_edit_order_id").text();
    writeEditOrderTable();
    writeEditProductsTable();
}

function writeEditOrderTable() {
    $.ajax({
        async: false,
        url: "http://localhost:8080/v1/orders/" + edit_order_id,
        method: "get",
        dataType: "json",
        success: function (data) {
            $("#edit_order_info tbody").empty();
            const this_date = new Date(data.createDate);
            const order_date = this_date.toLocaleDateString() + " " +
                this_date.getHours() + ":" + this_date.getMinutes();
            const new_row = '<tr><td>' + order_date + '</td>' +
                '<td><input type="text" id="current_user_address" value="' + data.address + '"></td>' +
                '<td>' + data.status + '</td>' +
                '</tr>';
            $("#edit_order_info tbody").append(new_row)
        }
    });
}

function writeEditProductsTable() {
    $.ajax({
        url: "http://localhost:8080/v1/order_products/full_info/" + edit_order_id,
        method: "get",
        dataType: "json",
        success: function (data) {
            $("#edit_order_products tbody").empty();
            order_products_amount = data.length
            for (let i = 0; i < data.length; i++) {
                const product_id = data[i].productId;
                const description = data[i].description;
                const amount = data[i].amount;
                const new_row = '<tr><td>' + description + '<a style="display: none;" id="update_product_id_'
                    + i + '">' + product_id + '</a>' + '</td>' +
                    '<td><input type="text" id="new_amount_' + i + '" value="' + amount + '"></td>' +
                    '</tr>';
                $("#edit_order_products tbody").append(new_row)
            }
        }
    });
}
