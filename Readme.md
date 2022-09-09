Finished: 
1) Date 
   http://localhost:8080/order/date?orderId=1     // 1 to change (for example)
2) Status
   http://localhost:8080/order/status?orderId=1   // now they are numbers, then I will change to enam
3) Address
   http://localhost:8080/order/address?orderId=1
4) Description
   http://localhost:8080/product/description?productId=1
5) test of connection
   http://localhost:8080/user/test

Problems:
1) Set of orders (returns an empty Set)
   http://localhost:8080/user/orders?userId=1
2) User (works, but returns an empty Set of orders)
   http://localhost:8080/order/user?orderId=1
3) Set of products (returns error)
   http://localhost:8080/order/products?orderId=1
   
   
    