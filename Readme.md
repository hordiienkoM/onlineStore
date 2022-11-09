Online store

This is an online store project. Implemented the functions of user registration and authorization, getting a list of
available products, creating and changing an order.After registration, the user will receive an email with a link to
confirm registration. For the user, orders and products, all CRUD operations are implemented through the REST API
requests. Adding and deleting products and users can only be done by a user with administrator rights. A user with
administrator rights can also upload a list of products, the names of which will be read from an external site, and
other fields will be automatically generated. Also, the administrator can delete all products. These 2 functions serve
to quickly test other functions. Products are stored in MongoDB database, all other data in Mysql. The site has a simple
frontend and 2 languages to choose from. The list of requests can be viewed at http://localhost:8080/swager.ui/ . The
default admin user is user1@user.us, password 1.

Technology:
Spring Boot, Spring Security, Spring JPA, Spring Web, Spring Email, Spring Validation,
Hibernate, Mysql, Mongodb, Junit, Maven, Liquibase, Mapstruct, Swagger, Jsoup, JavaScript(jQuery, Ajax), HTML, CSS, Git,
Docker, Postman

To start the project, you have to configure the application.properties (You have to write your own properties in
#mysql properties, #mongodb properties, #Spring mail properties)



   
   
    