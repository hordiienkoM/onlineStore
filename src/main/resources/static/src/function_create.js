function listen_create_new_order() {
    $("#create_button").click(function (e) {
        e.preventDefault();
        let new_order = {
            createDate: new Date($.now()),
            address: $('#user_address').val(),
            orderProduct: products_to_order
        };
        $.ajax({
            url: "http://localhost:8080/v1/orders",
            type: "POST",
            data: JSON.stringify(new_order),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                alert("Order was created")
            },
            error: function (data) {
                alert(JSON.stringify(data))
            }
        });
    });
}

