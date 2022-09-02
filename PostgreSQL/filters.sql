CREATE TABLE type(
	id serial primary key,
	name text
);

create table product(
    id serial primary key,
    name text,
	type_id int REFERENCES type(id),
	expired_date TIMESTAMP,
	price FLOAT
);

insert into type(name) values ('Сыр');
insert into type(name) values ('Мороженое');
insert into type(name) values ('Молоко');

insert into product(name, type_id, expired_date, price) 
values ('Сыр плавленный', 1, date '01.09.2022', 200.00);

insert into product(name, type_id, expired_date, price) 
values ('Сыр Моцарелла', 1, date '05.09.2022', 250.00);

insert into product(name, type_id, expired_date, price) 
values ('Сыр Пармезан', 1, date '06.09.2022', 350.00);

insert into product(name, type_id, expired_date, price) 
values ('Мороженное Вечерний Екатеринбург', 2, date '01.10.2022', 50.00);

insert into product(name, type_id, expired_date, price) 
values ('Мороженное Maxibon', 2, date '01.09.2022', 150.00);

insert into product(name, type_id, expired_date, price) 
values ('Мороженное Baskin Robbins', 2, date '01.11.2022', 550.00);

insert into product(name, type_id, expired_date, price) 
values ('Молоко Простоквашино', 3, date '01.09.2022', 60.00);

insert into product(name, type_id, expired_date, price) 
values ('Молоко Талицкое', 3, date '15.09.2022', 70.00);

insert into product(name, type_id, expired_date, price) 
values ('Молоко деревенское', 3, date '10.09.2022', 80.00);

SELECT * from type where name like 'Сыр';
SELECT * from product where type_id = 1;

SELECT * from product where name like '%Мороженное%';

SELECT * from product where expired_date < '02.09.2022';

SELECT tp.name as "Тип продукта", max(pr.price) as "Максимальная стоимость"
from product pr
join type tp 
on pr.type_id = tp.id
group by tp.name;

SELECT pr.name, pr.price
from product pr
group by pr.name, pr.price
having pr.price = (select max(price) from product);

SELECT tp.name as "Тип продукта", count(pr.name) as "Количество продуктов"
from product pr
join type tp 
on pr.type_id = tp.id
group by tp.name;

SELECT tp.name as "Тип продукта", pr.name as "Имя продукта"
from product pr
join type tp 
on pr.type_id = tp.id
where tp.name like 'Сыр' or tp.name like 'Молоко';

SELECT tp.name as "Тип продукта", count(pr.name) as "Количество продуктов"
from product pr
join type tp 
on pr.type_id = tp.id
group by tp.name
having count(pr.name) < 10;

SELECT pr.name "Продукт", pr.expired_date, pr.price, tp.name as "Тип продукта"
from product pr
join type tp 
on pr.type_id = tp.id;
