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
     sell_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
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

INSERT INTO `clients` (`id`, `name`, `nif`, `address`) VALUES
(UUID(), 'Carlos Martínez', '12345678A', 'Calle Mayor, 10, Madrid, España'),
(UUID(), 'Ana López', '23456789B', 'Avenida de la Constitución, 45, Barcelona, España'),
(UUID(), 'José García', '34567890C', 'Calle del Prado, 32, Valencia, España'),
(UUID(), 'María Sánchez', '45678901D', 'Gran Vía, 15, Sevilla, España'),
(UUID(), 'Luis Fernández', '56789012E', 'Paseo de Gracia, 18, Barcelona, España'),
(UUID(), 'Laura Rodríguez', '67890123F', 'Calle de la Paz, 10, Zaragoza, España'),
(UUID(), 'David Moreno', '78901234G', 'Avenida de la Libertad, 20, Bilbao, España'),
(UUID(), 'Elena Ruiz', '89012345H', 'Calle Real, 25, Málaga, España'),
(UUID(), 'Pablo Gómez', '90123456I', 'Paseo de la Castellana, 100, Madrid, España'),
(UUID(), 'Sandra Torres', '01234567J', 'Calle Nueva, 8, Murcia, España');

INSERT INTO `products` (`id`, `name`, `code`, `stock`) VALUES
(UUID(), 'Laptop Pro', 'LP1001', 10),
(UUID(), 'Smartphone X', 'SP2001', 20),
(UUID(), 'Tablet Plus', 'TP3001', 15),
(UUID(), 'Smartwatch Basic', 'SW4001', 25),
(UUID(), 'Wireless Earbuds', 'WE5001', 30),
(UUID(), '4K Monitor', 'KM6001', 10),
(UUID(), 'Gaming Keyboard', 'GK7001', 15),
(UUID(), 'Bluetooth Speaker', 'BS8001', 20),
(UUID(), 'External Hard Drive', 'EH9001', 15),
(UUID(), 'USB-C Hub', 'UC10001', 25);

INSERT INTO `sales` (`id`, `client_id`, `sell_date`) VALUES
(UUID(), (SELECT id FROM clients WHERE nif = '12345678A'), '2023-01-15 10:30:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '23456789B'), '2023-01-20 15:45:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '34567890C'), '2023-02-10 12:15:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '45678901D'), '2023-02-15 09:00:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '56789012E'), '2023-03-01 17:30:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '67890123F'), '2023-03-10 11:25:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '78901234G'), '2023-04-05 16:40:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '89012345H'), '2023-04-20 10:50:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '90123456I'), '2023-05-01 14:30:00'),
(UUID(), (SELECT id FROM clients WHERE nif = '01234567J'), '2023-05-15 13:20:00');

INSERT INTO `sales_det` (`sale_id`, `product_id`, `quantity`) VALUES
((SELECT id FROM sales LIMIT 1 OFFSET 0), (SELECT id FROM products WHERE code = 'LP1001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 0), (SELECT id FROM products WHERE code = 'TP3001'), 2),
((SELECT id FROM sales LIMIT 1 OFFSET 1), (SELECT id FROM products WHERE code = 'SP2001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 1), (SELECT id FROM products WHERE code = 'SW4001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 2), (SELECT id FROM products WHERE code = 'WE5001'), 3),
((SELECT id FROM sales LIMIT 1 OFFSET 3), (SELECT id FROM products WHERE code = 'KM6001'), 2),
((SELECT id FROM sales LIMIT 1 OFFSET 3), (SELECT id FROM products WHERE code = 'LP1001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 4), (SELECT id FROM products WHERE code = 'GK7001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 4), (SELECT id FROM products WHERE code = 'BS8001'), 2),
((SELECT id FROM sales LIMIT 1 OFFSET 5), (SELECT id FROM products WHERE code = 'EH9001'), 2),
((SELECT id FROM sales LIMIT 1 OFFSET 5), (SELECT id FROM products WHERE code = 'WE5001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 6), (SELECT id FROM products WHERE code = 'LP1001'), 2),
((SELECT id FROM sales LIMIT 1 OFFSET 7), (SELECT id FROM products WHERE code = 'UC10001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 8), (SELECT id FROM products WHERE code = 'TP3001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 8), (SELECT id FROM products WHERE code = 'SP2001'), 1),
((SELECT id FROM sales LIMIT 1 OFFSET 9), (SELECT id FROM products WHERE code = 'SW4001'), 2);