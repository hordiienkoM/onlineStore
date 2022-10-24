function deleteOrderById(orderId) {
    $.ajax({
        url: "http://localhost:8080/v1/orders/" + orderId,
        method: "DELETE",
        success: function (data) {
            if (data) {
                alert("Order " + orderId + " was deleted")
                turn_current_orders();
            } else {
                alert("Order not found");
            }
        }
    });
    $("#order_view").hide();
}