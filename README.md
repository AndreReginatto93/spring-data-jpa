# 📚 Bookstore API (Spring Data JPA)

A simple REST API built with **Java, Spring Boot, and PostgreSQL**, demonstrating how to manage authors, publishers, and books using **Spring Data JPA**.

## Table of Contents
- [Run Locally](#-run-locally)
- [Technologies](#-technologies)
- [API Endpoints](#authors)
    - [Authors](#authors)
    - [Publishers](#publisher)
    - [Books](#books)

## Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/AndreReginatto93/spring-data-jpa.git
   cd spring-data-jpa 
2. Start PostgreSQL and create a database:
   * CREATE DATABASE bookstore-jpa;
3. Update your application.properties if needed:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/bookstore-jpa
   spring.datasource.username=postgres
   spring.datasource.password=your_password
4. Run the application
5. Test the endpoints with Postman or curl
   * "http://localhost:8080/bookstore/author"
   * "http://localhost:8080/bookstore/publisher"
   * "http://localhost:8080/bookstore/book"

## 🛠 Technologies

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **pgAdmin** (database management)
- **IntelliJ IDEA** (development IDE)
- **Postman** (API testing)



# Authors
| Operations       | HTTP Method | Path                  |
|------------------|-------------|-----------------------|
| Get all authors  | GET         | /bookstore/author     |
| Get an author    | GET         | /bookstore/author/:id |
| Create an author | POST        | /bookstore/author     |
| Update an author | PUT         | /bookstore/author     |
| Delete an author | DELETE      | /bookstore/author/:id |

```http
POST /bookstore/author

{
    "name": "Arthur Conan Doyle"
}
```
#### Response:

```json
{
  "id": "d16499ef-58be-4e2f-8aa9-9ee9e88abbec",
  "name": "Arthur Conan Doyle"
}
```

# Publisher
| Operations         | HTTP Method | Path                     |
|--------------------|-------------|--------------------------|
| Get all publishers | GET         | /bookstore/publisher     |
| Get a publisher    | GET         | /bookstore/publisher/:id |
| Create a publisher | POST        | /bookstore/publisher     |
| Update a publisher | PUT         | /bookstore/publisher     |
| Delete a publisher | DELETE      | /bookstore/publisher/:id |

```http
POST /bookstore/publisher

{
    "name": "Penguin"
}
```
#### Response:

```json
{
  "id": "fc112b35-de79-4892-afcb-ef4012d316da",
  "name": "Penguin"
}
```

# Books
| Operations                               | HTTP Method | Path                |
|------------------------------------------|-------------|---------------------|
| Get all books                            | GET         | /bookstore/book     |
| Get a book                               | GET         | /bookstore/book/:id |
| Create a book                            | POST        | /bookstore/book     |
| Update a book                            | PUT         | /bookstore/book     |
| Delete a book                            | DELETE      | /bookstore/book/:id |

```http
POST /bookstore/book

{
    "title": "Sherlock Holmes",
    "publisherId": "fc112b35-de79-4892-afcb-ef4012d316da",
    "authorsIds": ["d16499ef-58be-4e2f-8aa9-9ee9e88abbec"],
    "reviewComment": "A gripping tale of wit and deduction, where Sherlock Holmes unravels mysteries with sharp logic and charm."
}
```

#### Response:

```json
{
  "id": "b50007a5-1d6f-44b7-88cd-ac5432c338ba",
  "title": "Sherlock Holmes",
  "publisher": {
    "id": "fc112b35-de79-4892-afcb-ef4012d316da",
    "name": "Penguin"
  },
  "authors": [
    {
      "id": "d16499ef-58be-4e2f-8aa9-9ee9e88abbec",
      "name": "Arthur Conan Doyle"
    }
  ],
  "review": {
    "id": "78a40dc4-3960-4d18-a2d0-03a8305ec977",
    "comment": "A gripping tale of wit and deduction, where Sherlock Holmes unravels mysteries with sharp logic and charm."
  }
}
```