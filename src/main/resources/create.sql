CREATE TABLE clients (
    id varchar(36) NOT NULL,
    name varchar(150) NOT NULL,
    nif varchar(10) NOT NULL,
    address varchar(150),

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE products (
    id varchar(36) NOT NULL,
    name varchar(100) NOT NULL,
    code varchar(10) NOT NULL UNIQUE,
    stock smallint unsigned NOT NULL DEFAULT 0,

    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sales (
    id varchar(36) NOT NULL,
    client_id varchar(36) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES clients(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sales_details (
    sale_id varchar(36) NOT NULL,
    product_id varchar(36) NOT NULL,
    quantity smallint unsigned NOT NULL,

    PRIMARY KEY (sale_id, product_id),
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM clients ORDER BY name;
SELECT * FROM products ORDER BY code;
SELECT * FROM sales;
SELECT * FROM sales_details;

-- # DROP TABLE clients;
-- # DROP TABLE products;
-- # DROP TABLE sales;
-- # DROP TABLE sales_details;

INSERT INTO clients VALUES ('b394024d-1ebe-4138-92f4-92512e05bd25', 'carlos', '41752530D', 'Menorca');
INSERT INTO clients VALUES ('d9bf33cd-b5af-4b68-af76-9600e74ffe95', 'ulises', '51752531A', 'mnc');
INSERT INTO clients VALUES ('29a13e19-2110-4d65-ae86-b5a4b5654a65', 'ivan', '51752531A', 'mnc');

INSERT INTO products VALUES ('d2303d39-c529-4b23-9073-abca389fcad4', 'laptop', '25P001', 10);
INSERT INTO products VALUES ('ee2d0e2f-7668-4271-8992-314912b95f40', 'tshirt', '25P002', 11);
INSERT INTO products VALUES ('01ce2f61-0300-476b-8390-bb0010ed5edf', 'iphone 16', '25P002', 20);
INSERT INTO products VALUES ('1b8bc92e-d9c2-4350-8266-c1ea0e013f32', 'airpods pro', '25P002', 8);
INSERT INTO products VALUES ('225f2891-21e1-4641-80b6-94e633dd61cb', 'keyboard', '25P002', 9);
INSERT INTO products VALUES ('1d4508fc-27ce-464e-b504-f3ea80f8f5eb', 'mouse', '25P002', 16);
INSERT INTO products VALUES ('7f3c2536-974a-4e6f-8053-05eb309a6c54', 'bluetooth speaker', '25P002', 8);
INSERT INTO products VALUES ('6f7c9550-92f1-4df1-ac92-1634b1773569', 'camera', '25P002', 9);

INSERT INTO sales VALUES ('d392eba9-f9d6-458f-882a-3ad2cbcd9505', 'b394024d-1ebe-4138-92f4-92512e05bd25');
INSERT INTO sales VALUES ('dbba7a05-32fb-4ddd-9a12-3ff3f7d7a67f', 'b394024d-1ebe-4138-92f4-92512e05bd25');
INSERT INTO sales VALUES ('5b86fcf6-dd14-4582-93cc-95d14e2fb167', 'd9bf33cd-b5af-4b68-af76-9600e74ffe95');

INSERT INTO sales_details VALUES ('5b86fcf6-dd14-4582-93cc-95d14e2fb167', '1b8bc92e-d9c2-4350-8266-c1ea0e013f32', 8);
INSERT INTO sales_details VALUES ('5b86fcf6-dd14-4582-93cc-95d14e2fb167', '7f3c2536-974a-4e6f-8053-05eb309a6c54', 1);
INSERT INTO sales_details VALUES ('5b86fcf6-dd14-4582-93cc-95d14e2fb167', '1b8bc92e-d9c2-4350-8266-c1ea0e013f32', 7);
INSERT INTO sales_details VALUES ('dbba7a05-32fb-4ddd-9a12-3ff3f7d7a67f', '1d4508fc-27ce-464e-b504-f3ea80f8f5eb', 3);
INSERT INTO sales_details VALUES ('d392eba9-f9d6-458f-882a-3ad2cbcd9505', '01ce2f61-0300-476b-8390-bb0010ed5edf', 2);
INSERT INTO sales_details VALUES ('d392eba9-f9d6-458f-882a-3ad2cbcd9505', 'd2303d39-c529-4b23-9073-abca389fcad4', 6);
