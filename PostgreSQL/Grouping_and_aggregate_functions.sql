create table devices(
    id serial primary key,
    name varchar(255),
    price float
);

create table people(
    id serial primary key,
    name varchar(255)
);

create table devices_people(
    id serial primary key,
    device_id int references devices(id),
    people_id int references people(id)
);

insert into devices(name, price) values ('notebook', 70000);
insert into devices(name, price) values ('watch', 3000);
insert into devices(name, price) values ('smartphone', 50000);
insert into devices(name, price) values ('headphones', 20000);
insert into devices(name, price) values ('smart TV', 80000);

insert into people(name) values ('Pavel'), ('Alena'), ('Petr');

insert into devices_people(device_id, people_id) values (1, 1), (3, 1), (4, 1);
insert into devices_people(device_id, people_id) values (2, 2), (3, 2), (4, 2);
insert into devices_people(device_id, people_id) values (5, 3);

select avg(price) from devices;

SELECT p.name, count(dp.device_id), avg(d.price)
from devices_people dp
join people p 
on dp.people_id = p.id
join devices d
on dp.device_id = d.id
group by p.name
having avg(d.price) > 30000;