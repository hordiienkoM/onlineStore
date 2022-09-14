
1) Create (POST)
   http://localhost:8080/order/newOrder?userId=3
   {
   "status": "1",
   "createDate": "2022-09-08T15:05:56.000+00:00",
   "address": "address-5",
   "orderProduct": [{
   "amount": 4,
   "product":{"id": 2}},
   {
   "amount": 8,
   "product":{"id": 1}}]
   }
2) Read all orders (GET)
   http://localhost:8080/order/orders?userId=1
3) Update order (POST)
   http://localhost:8080/order/updateOrder?orderId=32&userId=3
   {
   "status": "1",
   "createDate": "2022-09-08T15:05:56.000+00:00",
   "address": "address-5",
   "orderProduct": [{
   "amount": 4,
   "product":{"id": 2}},
   {
   "amount": 8,
   "product":{"id": 1}}]
   }
4) Delete order (DELETE)
   http://localhost:8080/order/?orderId=40

   
   
    