# Ecommerce Application

This is a microservices-based ecommerce application built using Spring Boot, Angular, RabbitMQ, Keycloak, and PostgreSQL. The application consists of two main microservices: Product Service and Order Service. It also includes an Angular-based user interface.

## Prerequisites

- Docker
- Java 17+
- Node.js and npm
- Gradle (8.9)

## Getting Started

1. **Clone the repository**:
   git clone https://github.com/vaishnav4206/ecommerceApp.git
   cd ecommerceApp

2. **Start the local development environment using Docker Compose**:
    docker-compose up
This will start the PostgreSQL database, Keycloak, RabbitMQ, and other necessary containers.

3. **Configure Keycloak**:
    Go to http://localhost:8080 and log in to the Keycloak admin console.
    Username: admin
    Password: admin
    Create a new realm and import the ekart-realm configuration from ecommerceApp/keycloak/realms.
    Click Create to configure the realm.

4. **Build and run the backend services**:
    Navigate to the ecommerce-product and ecommerce-order directories.
    Run ./gradlew bootRun to start each service.

5. **Set up the frontend**:
    Navigate to the ecommerce-ui directory.
    Run npm install to install dependencies.
    Run ng serve to start the Angular development server.

6. **Access the application**:
    Open http://localhost:4200 in your browser.
    You will be redirected to Keycloak for authentication. Register as a new user or use an existing account.

7. **Obtain admin access (optional)**:
    Go to http://localhost:8080 and log in to the Keycloak admin console.
    Navigate to Users and add a new user or use an existing one.
    Assign the client_admin role to the user.
    
# OpenAPI Documentation

    Swagger/OpenAPI is configured for the API documentation. You can access the documentation using the following URLs:
    Product: 
        OpenAPI JSON: http://localhost:8081/v3/api-docs
        Swagger UI: http://localhost:8081/swagger-ui.html
    Order: 
        OpenAPI JSON: http://localhost:8082/v3/api-docs
        Swagger UI: http://localhost:8082/swagger-ui.html    

# Database Management
You can use pgAdmin to manage the PostgreSQL database:
    
    1. Start pgAdmin:
        docker run --name my-pgadmin -p 8090:80 -e PGADMIN_DEFAULT_EMAIL=user@example.com -e PGADMIN_DEFAULT_PASSWORD=admin -d dpage/pgadmin4 (pgAdmin will be already up and running when you start docker-compose up initially)

    2. Access pgAdmin:
        Open http://localhost:8090 in your browser.
        Username: user@example.com
        Password: admin

    3. Configure the PostgreSQL connection:
        Host: host.docker.internal
        Username and Password: Refer to the docker-compose.yml file.
        Maintenance Database: postgres

    4. Execute SQL queries:
        docker exec -it postgres-sql-ecommerce psql -U username -d ecommerce-products

    Example SQL commands:

        CREATE TABLE Product (
            id SERIAL PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            description TEXT,
            price DECIMAL(10, 2) NOT NULL,
            image_url VARCHAR(255),
            stock_quantity INT NOT NULL DEFAULT 0,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        );

        INSERT INTO Product (name, description, price, image_url, stock_quantity, created_at, updated_at)
        VALUES
            ('watch1', 'Description for Product A', 19.99, 'images/products/watch1.jpg', 100, NOW(), NOW()),
            ('watch2', 'Description for Product B', 29.99, 'images/products/watch2.jpg', 50, NOW(), NOW()),
            ('watch3', 'Description for Product C', 39.99, 'images/products/watch3.jpg', 25, NOW(), NOW()),
            ('watch5', 'Description for Product D', 49.99, 'images/products/watch5.jpg', 75, NOW(), NOW()),
            ('watch6', 'Description for Product E', 59.99, 'images/products/watch6.jpg', 10, NOW(), NOW()),
            ('watch7', 'Description for Product F', 59.99, 'images/products/watch7.jpg', 10, NOW(), NOW());

# RabbitMQ Setup

RabbitMQ is configured in the docker-compose.yml file and starts automatically with other services.

    Access RabbitMQ Management Console:
        URL: http://localhost:15672
        Username: user
        Password: password

    Queues and Exchanges:
        Order Queue: orderQueue
        Status Queue: statusQueue
        Notification Queue: notification-queue
        Exchange: orderExchange
        Exchange: notification-exchange
        
    Using RabbitMQ in Services:
        Services can send and receive messages through the RabbitMQ configuration in their respective microservices.
        Automation

# Contributing

If you would like to contribute to this project, please follow the standard fork-and-pull git workflow:

    1. Fork the repository
    2. Create your feature branch (git checkout -b feature/fooBar)
    3. Commit your changes (git commit -am 'Add some fooBar')
    4. Push to the branch (git push origin feature/fooBar)
    5. Create a new Pull Request