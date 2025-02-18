# eCommerce Backend

## Overview
The `ecommerce_backend` is a Spring Boot-based REST API that powers an eCommerce platform. It provides functionalities for user authentication, product management, and order processing.

## Features
- User Authentication & Authorization (JWT-based security)
- Product Management (CRUD operations)
- Order Management (Cart, Checkout, Order Processing)
- Admin Dashboard Support
- Role-based Access Control (RBAC)

## Tech Stack
- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Database:** PostgreSQL
- **Storage:** Cloudinary (for image storage)
- **Build Tool:** Maven

## Installation & Setup

### Prerequisites
Ensure you have the following installed:
- Java 21
- Maven
- PostgreSQL

### Clone the Repository
```sh
git clone https://github.com/ThuraMinThein/ecommerce_backend.git
cd ecommerce_backend
```

### Configure the Database
Update `application.properties` Or `application-dev.properties` for development:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### ENV File
Create a `.env` file  with the following content
#### You can reference from `.env.example`
```properties
CLOUDINARY_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

### Build & Run
#### Using Maven
```sh
mvn clean install
mvn spring-boot:run
```

## Contributing
Feel free to open issues or submit pull requests.