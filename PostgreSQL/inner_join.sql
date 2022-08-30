create table auto(
    id serial primary key,
    name text,
	move BOOLEAN,
	colour text
);

create table specifications(
    id serial primary key,
    year int,
    auto_id int references auto(id)
);

insert into auto(name, move, colour) values ('LADA_Granta', false, 'green');
insert into auto(name) values ('LADA');
insert into auto(name, move) values ('LADA_Cross', true);
insert into auto(name) values ('LADA_Vesta');
insert into specifications(year) values (2020);
insert into specifications(year) values (2015);
insert into specifications(year, auto_id) values (2017, 1);
insert into specifications(year, auto_id) values (2018, 2);
insert into specifications(year, auto_id) values (2016, 3);

select * from auto 
join specifications spec on spec.auto_id = auto.id;

select auto.name Имя, spec.year as "Год выпуска" 
from auto join specifications spec on spec.auto_id = auto.id where spec.year > 2017;

select * from auto 
join specifications spec on spec.auto_id = auto.id where auto.move = true;

select auto.name Имя, auto.colour, auto.move as "На ходу"
from auto join specifications spec on spec.auto_id = auto.id where auto.colour is not null;