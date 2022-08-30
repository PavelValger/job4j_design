create table zoo(
    id serial primary key,
    name text
);

create table animal(
id serial primary key,
	name text,
	zoo_id int REFERENCES zoo(id)
);

insert into zoo(name) values ('Moscow_Zoo');
insert into animal(name, zoo_id) values ('tiger', 1);
insert into animal(name, zoo_id) values ('wolf', 1);
insert into animal(name, zoo_id) values ('bear', 1);

select * from animal;
select * from zoo where id in (select id from animal);