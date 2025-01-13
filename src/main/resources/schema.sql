-- Table for Clients
CREATE TABLE clients (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    nif VARCHAR(10) NOT NULL UNIQUE,
    address VARCHAR(150)
);

-- Table for Products
CREATE TABLE products (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL UNIQUE,
    stock INT DEFAULT 0
);

-- Table for Sales
CREATE TABLE sales (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    client_id UUID NOT NULL REFERENCES clients(id),
    quantity INT NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for Purchases
CREATE TABLE purchases (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL REFERENCES products(id),
    supplier VARCHAR(150) NOT NULL,
    quantity INT NOT NULL,
    purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
