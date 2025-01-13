DROP TABLE IF EXISTS `sales_det`;
DROP TABLE IF EXISTS `sales`;
DROP TABLE IF EXISTS `clients`;
DROP TABLE IF EXISTS `products`;

CREATE TABLE `clients` (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    nif VARCHAR(10) NOT NULL UNIQUE,
    address VARCHAR(150)
);

CREATE TABLE `products` (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) NOT NULL UNIQUE,
    stock INT NOT NULL DEFAULT 0
);

CREATE TABLE `sales` (
     id VARCHAR(36) PRIMARY KEY,
     client_id VARCHAR(36) NOT NULL,
     FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);

CREATE TABLE `sales_det` (
     sale_id VARCHAR(36) NOT NULL,
     product_id VARCHAR(36) NOT NULL,
     quantity INT NOT NULL CHECK (quantity > 0),
     PRIMARY KEY (sale_id, product_id),
     FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE,
     FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE RESTRICT
);