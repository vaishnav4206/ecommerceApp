docker-compose up : will start the local db, keycloak instances
localhost:4200 : will redirect to keycloak for authentication -> register as new user
if you want admin access -> go to localhost:8080 -> users -> add user / use existing -> add client_admin role

//TO DO: Automate all these - docker compose

// to run pgAdmin in local
docker run --name my-pgadmin -p 8080:80 -e PGADMIN_DEFAULT_EMAIL=user@example.com -e PGADMIN_DEFAULT_PASSWORD=admin -d dpage/pgadmin4

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
    ('Product A', 'Description for Product A', 19.99, 'http://example.com/imageA.jpg', 100, NOW(), NOW()),
    ('Product B', 'Description for Product B', 29.99, 'http://example.com/imageB.jpg', 50, NOW(), NOW()),
    ('Product C', 'Description for Product C', 39.99, 'http://example.com/imageC.jpg', 25, NOW(), NOW()),
    ('Product D', 'Description for Product D', 49.99, 'http://example.com/imageD.jpg', 75, NOW(), NOW()),
    ('Product E', 'Description for Product E', 59.99, 'http://example.com/imageE.jpg', 10, NOW(), NOW());