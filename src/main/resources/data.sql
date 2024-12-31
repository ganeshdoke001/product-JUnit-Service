create table product (
        id integer not null,
        name varchar(255),
        price float(53),
        quantity integer,
        primary key (id)
    );
    
    INSERT INTO product (id, name, price, quantity)
VALUES
  (1, 'Laptop', 999.99, 50),
  (2, 'Smartphone', 499.99, 100),
  (3, 'Headphones', 79.99, 200),
  (4, 'Monitor', 199.99, 30);
