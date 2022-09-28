create table car_bodies(
            id serial primary key,
            name varchar(255)
);

create table car_engines(
            id serial primary key,
            name varchar(255)
);

create table car_transmissions(
            id serial primary key,
            name varchar(255)
);

create table cars(
            id serial primary key,
            name varchar(255),
	body_id int references car_bodies(id),
	engine_id int REFERENCES car_engines(id),
	transmission_id int REFERENCES car_transmissions(id)
);

insert into car_bodies(name) values ('седан'), ('хэтчбек'), ('пикап');
insert into car_engines(name) values ('бензиновый'), ('электрический'),
('дизельный');
insert into car_transmissions(name) values ('механика'), ('автомат'),
('робот');
insert into cars(name, body_id, engine_id, transmission_id)
values ('LADA', 1, 1, 1), ('Mazda', 2, 2, 2), ('Nissan', 1, 2, 1);
insert into cars(name, body_id, transmission_id)
values ('LADA_RX', 1, 1);

select c.id, c.name as "car name", b.name as "body name",
e.name as "engine name", t.name as "transmission name"
FROM cars c left join car_bodies b on b.id = c.body_id
left join car_engines e on e.id = c.engine_id
left join car_transmissions t on t.id = c.transmission_id;

select b.name as "body name", c.id, c.name as "car name"
FROM cars c right join car_bodies b on b.id = c.body_id
where c.id is null;

select e.name as "engine name", c.id, c.name as "car name"
FROM cars c right join car_engines e on e.id = c.engine_id
where c.id is null;

select t.name as "transmission name", c.id, c.name as "car name"
FROM cars c right join car_transmissions t on t.id = c.transmission_id
where c.id is null;