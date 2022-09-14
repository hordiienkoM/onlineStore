
1) Create (POST)
   http://localhost:8080/order/order?userId=3
   {
   "status": "1",
   "createDate": "2022-09-14T17:26:05.856907682",
   "address": "address-1",
   "orderProduct": [{
   "amount": 5,
   "product":{"id": 3}},
   {
   "amount": 12,
   "product":{"id": 2}}]
   }
2) Read all orders (GET)
   http://localhost:8080/order/orders?userId=1
3) Update order (POST)
   http://localhost:8080/order/order/id?orderId=32&userId=3
   {
   "status": "2",
   "createDate": "2022-09-14T17:26:05.856907682",
   "address": "address-3",
   "orderProduct": [{
   "amount": 5,
   "product":{"id": 2}},
   {
   "amount": 12,
   "product":{"id": 1}}]
   }
4) Delete order (DELETE)
   http://localhost:8080/order/id?orderId=40

   
   
    