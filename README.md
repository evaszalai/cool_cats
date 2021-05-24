# Cats for pets


## Story

It's time to enhance the Online Shop, an online eCommerce web-application with Java.
Where users can not only browse products, add them into a Shopping Cart,
checkout items and make payments. But also can log in and see the abandoned shopping cart or order history.

> Did you know that the very first product on eBay was a broken laser pointer?
> If you don't believe, check their history: [eBay history](https://www.ebayinc.com/company/our-history/)

---
## What we have learned during the project

- How to create dynamic web pages in Java with servlets
- How to use the Thymeleaf templating engine
- Using database to make the data persistent
- How to use the `DAO` design pattern in `Java`
- Refreshing SQL knowledge
- Java object serialization

---
## How to use

### Prerequisites

- JAVA JDK
- Postgresql
- Maven

1. Create a new Postgresql database and run the ```/src/main/SQL/init_db.sql``` file on it

2. Open a new terminal in project folder and run the following command: ```mvn jetty:run```

3. Open a web browser and visit ```localhost:8080```

4. Buy some cat

- You can change the type of storage between in-memory use and database
- To change from database to in-memory use open the ```/src/main/resources/connection.properties``` file and replace ```dao = jdbc``` to ```dao = memory```

---

## Contributors

- Szalai Éva
- Kiss Tamás
- Nguyen Anh Tuan (Bence)
