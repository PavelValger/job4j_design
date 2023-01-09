CREATE TABLE customers(
    id serial primary key,
    first_name text,
    last_name text,
    age int,
    country text
);

insert into customers(first_name, last_name, age, country) values ('Pavel', 'Valger', 30, 'Russia');
insert into customers(first_name, last_name, age, country) values ('Petr', 'Ars', 39, 'Russia');
insert into customers(first_name, last_name, age, country) values ('Ivan', 'Ivanov', 30, 'Russia');
insert into customers(first_name, last_name, age, country) values ('Stas', 'Kor', 30, 'Belarus');

CREATE TABLE orders(
    id serial primary key,
    amount int,
    customer_id int references customers(id)
);

insert into orders(amount, customer_id) values (500, 1);
insert into orders(amount, customer_id) values (500, 3);

SELECT c.first_name, c.last_name, c.age, c.country
from customers c
where  c.age = (select min(age) from customers);

SELECT c.first_name, c.last_name, c.age, c.country
from customers c
where c.id not in (select customer_id from orders);