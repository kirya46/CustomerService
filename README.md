# CustomerService

Rest service for make base **CRUD** operations on Customer:
- create Customer
- update Customer
- find Customer
- find all Customers
- delete Customer
- add Order to Customer (additional) 

**Tech:**
- Spring Boot v1.5.7 - framework
- Hibernate v5 - ORM 
- H2 database as default (also exist config for Postgres database) - project database
- Liquibase - for database managing
- JUnit - for testing
- Maven - for project management 

**Requirements:**
- Java 8
- Maven
- Git
- Curl (for make http request for test server, optional)

**Build and run project from command line:**

1. clone project: `$ git clone https://github.com/kirya46/CustomerService.git`
2. go to project folder: `$ cd CustomerService`
3. build project: `$ mvn clean && mvn package`
4. run project: `$ java -jar target/CustomerService-1.0-SNAPSHOT.jar`

or run project from sources without building:

1. run:  `$ mvn spring-boot:run`


**Server usage:**

- create Customer:
      request:
      `curl -i -H "Content-Type: application/json" -X POST -d '{
                  "name" : "Test name",
                  "surname" : "Test surname",
                  "phone" : "Test phone"
                }' http://localhost:8080/customer/create`
      
      response:          
      HTTP/1.1 201 
      Content-Type: application/json;charset=UTF-8
      Transfer-Encoding: chunked
      Date: Tue, 19 Sep 2017 10:53:54 GMT
      {"id":1,"name":"Test name","surname":"Test surname","phone":"Test phone","orders":[]}
        
- create Customer with existing name:
      request: 
      `curl -i -H "Content-Type: application/json" -X POST -d '{
                 "name" : "Test name",
                 "surname" : "Test surname",
                 "phone" : "Test phone"
       }' http://localhost:8080/customer/create`
       
      HTTP/1.1 400 
      Content-Length: 0
      Date: Tue, 19 Sep 2017 10:59:01 GMT
      Connection: close

                
- update Customer: 
      request: 
      `curl -i -H "Content-Type: application/json" -X POST -d '{
              "id" : 1,
              "name" : "New name",
              "surname" : "Test surname",
              "phone" : "Test phone"
       }' http://localhost:8080/customer/update`  
       
       HTTP/1.1 200 
       Content-Type: application/json;charset=UTF-8
       Transfer-Encoding: chunked
       Date: Tue, 19 Sep 2017 11:02:14 GMT
       
       {"id":1,"name":"New name","surname":"Test surname","phone":"Test phone","orders":[]}

- read Customer:
      request: `curl -i -X GET  http://localhost:8080/customer/1`
     
      HTTP/1.1 200 
      Content-Type: application/json;charset=UTF-8
      Transfer-Encoding: chunked
      Date: Tue, 19 Sep 2017 11:03:44 GMT
      
      {"id":1,"name":"New name","surname":"Test surname","phone":"Test phone","orders":[]}    
    
- read all Customers:
      request:  `curl -i -X GET  http://localhost:8080/customer/all`
      
      HTTP/1.1 200 
      Content-Type: application/json;charset=UTF-8
      Transfer-Encoding: chunked
      Date: Tue, 19 Sep 2017 11:08:17 GMT
      
      [{"id":1,"name":"New name","surname":"Test surname","phone":"Test phone","orders":[]}]

- delete existing Customer: 
      request: `curl -i -X GET  http://localhost:8080/customer/delete/1`
        
      HTTP/1.1 200 
      Content-Length: 0
      Date: Tue, 19 Sep 2017 11:08:43 GMT
       
    
- delete non existing Customer: 
      request: `curl -i -X GET  http://localhost:8080/customer/delete/100500`
    
      HTTP/1.1 400 
      Content-Length: 0
      Date: Tue, 19 Sep 2017 11:09:16 GMT
      Connection: close
      
- add Order for Customer with id = 1:
      request: `curl -i -H "Content-Type: application/json" -X POST -d '{
                 "description" : "Order description",
                 "price" : 99.99
               }' http://localhost:8080/customer/1/orders/add`    
                 
      HTTP/1.1 201 
      Content-Type: application/json;charset=UTF-8
      Transfer-Encoding: chunked
      Date: Tue, 19 Sep 2017 11:14:41 GMT
      
      {"id":1,"description":"Order description","price":99.99}   
    
        
    
                
**Additional:**

By default project use **H2 in-memory** database. Also project has **PostgresSQL** configuration.

For use Postgres as main database in project: 
    - install Postgres (http://www.postgresqltutorial.com/install-postgresql/)
    - create database ( https://www.tutorialspoint.com/postgresql/postgresql_create_database.htm )
    - change config file **../src/main/resources/database.properties** 
    
Comment next lines:
        
    #db.driver: org.h2.Driver
    #db.url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    #db.username: admin
    #db.password:
    #hibernate.dialect: org.hibernate.dialect.H2Dialect

And uncomment this lines:

    db.driver = org.postgresql.Driver
    db.url: jdbc:postgresql://localhost:5432/<db_name>
    db.username: <user_name>
    db.password: <user_password>
    hibernate.dialect: org.hibernate.dialect.PostgreSQL9Dialect
    







    
    