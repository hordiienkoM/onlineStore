let order_id = $("#current_order_id").text();
$.ajax({
    url: "http://localhost:8080/v1/orders/" + order_id,
    method: "get",
    dataType: "json",
    success: function (data) {
        const this_date = new Date(data.createDate);
        const order_date = this_date.toLocaleDateString() + " " +
            this_date.getHours() + ":" + this_date.getMinutes();
        const new_row = '<tr><td>' + order_date + '</td>' +
            '<td>' + data.address  + '</td>' +
            '<td>' + data.status + '</td>' +
            '</tr>';
        $("#order_info tbody").append(new_row)
    }
});

$.ajax({
    url: "http://localhost:8080/v1/order_products/" + order_id,
    method: "get",
    dataType: "json",
    success: function (data) {
        for (let i = 0; i < data.length; i++) {
            const new_row = '<tr><td>' + data[i].description + '</td>' +
                '<td>' + data[i].amount  + '</td>' +
                '</tr>';
            $("#order_products tbody").append(new_row)
        }
    }
});