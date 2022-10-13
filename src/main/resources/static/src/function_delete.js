function deleteOrderById(orderId) {
    $.ajax({
        url: "http://localhost:8080/v1/orders/" + orderId,
        method: "DELETE",
        success: function (data) {
            if (data) {
                alert("Order " + orderId + " was deleted")
            } else {
                alert("Order not found");
            }
        }
    });
    $("#order_view").hide();
    let status = "#order_row_id_" + orderId + " .order_status";
    $(status).text("deleted");
}