-- Insert sample clients
INSERT INTO clients (id, name, nif, address) VALUES
    (gen_random_uuid(), 'John Doe', '123456789', '123 Main Street'),
    (gen_random_uuid(), 'Jane Smith', '987654321', '456 Elm Street');

-- Insert sample products
INSERT INTO products (id, name, code, stock) VALUES
    (gen_random_uuid(), 'Product A', 'P12345', 50),
    (gen_random_uuid(), 'Product B', 'P67890', 30);

-- Insert sample sales
INSERT INTO sales (id, product_id, client_id, quantity) VALUES
    (gen_random_uuid(),
     (SELECT id FROM products WHERE code = 'P12345'),
     (SELECT id FROM clients WHERE nif = '123456789'),
     5);

-- Insert sample purchases
INSERT INTO purchases (id, product_id, supplier, quantity) VALUES
    (gen_random_uuid(),
     (SELECT id FROM products WHERE code = 'P67890'),
     'Supplier X',
     20);
