docker-compose up : will start the local db, keycloak instances
localhost:4200 : will redirect to keycloak for authentication -> register as new user
if you want admin access -> go to localhost:8080 -> users -> add user / use existing -> add client_admin role

//TO DO: Automate all these - docker compose

// to run pgAdmin in local
docker run --name my-pgadmin -p 8090:80 -e PGADMIN_DEFAULT_EMAIL=user@example.com -e PGADMIN_DEFAULT_PASSWORD=admin -d dpage/pgadmin4

//configuration properties
host: host.docker.internal
username and password - refer docker compose 
Maintenance Database: postgres

// to execute psql queries via CLI:
docker exec -it postgres-sql-ecommerce psql -U username -d ecommerce-products

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
    ('watch3', 'Description for Product C', 39.99, 'images/products/watch3.jpg.jpg', 25, NOW(), NOW()),
    ('watch5', 'Description for Product D', 49.99, 'images/products/watch5.jpg.jpg', 75, NOW(), NOW()),
    ('watch6', 'Description for Product E', 59.99, 'images/products/watch6.jpg.jpg', 10, NOW(), NOW());
    ('watch7', 'Description for Product E', 59.99, 'images/products/watch7.jpg.jpg', 10, NOW(), NOW());



## RabbitMQ Setup

RabbitMQ is configured in the `docker-compose.yml` file. It is automatically started along with other services.

### Access RabbitMQ Management Console

- **URL:** [http://localhost:15672](http://localhost:15672)
- **Username:** `user`
- **Password:** `password`

### Queues and Exchanges

- **Order Queue:** `orderQueue`
- **Status Queue:** `statusQueue`
- **Exchange:** `orderExchange`

### Using RabbitMQ in Services

Services can send and receive messages through the RabbitMQ configuration in their respective microservices.
