create table cars(
id serial primary key,
	name text,
	year integer,
	move boolean
);
insert into cars(name, year, move) VALUES('LADA', 2020, true);
update cars set year = 2010;
update cars set move = false;
DELETE from cars;