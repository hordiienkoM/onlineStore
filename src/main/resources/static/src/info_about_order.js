let order_id;
function showOrderInfo(){
    order_id = $("#current_edit_order_id").text();
    writeOrderTable();
    writeProductsTable();
    $("#order_view .order_delete").val(order_id);
}

function writeOrderTable () {
    $.ajax({
        url: "http://localhost:8080/v1/orders/" + order_id,
        method: "get",
        dataType: "json",
        success: function (data) {
            $("#order_info tbody").empty();
            const this_date = new Date(data.createDate);
            const order_date = this_date.toLocaleDateString() + " " +
                this_date.getHours() + ":" + this_date.getMinutes();
            const new_row = '<tr><td>' + order_date + '</td>' +
                '<td>' + data.address + '</td>' +
                '<td>' + data.status + '</td>' +
                '</tr>';
            $("#order_info tbody").append(new_row)
        }
    });
}

function writeProductsTable() {
    $.ajax({
        url: "http://localhost:8080/v1/order_products/" + order_id,
        method: "get",
        dataType: "json",
        success: function (data) {
            $("#order_products tbody").empty();
            for (let i = 0; i < data.length; i++) {
                const new_row = '<tr><td>' + data[i].description + '</td>' +
                    '<td>' + data[i].amount + '</td>' +
                    '</tr>';
                $("#order_products tbody").append(new_row)
            }
        }
    });
}
