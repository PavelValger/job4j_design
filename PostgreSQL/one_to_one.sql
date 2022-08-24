create table husband(
    id serial primary key,
    name text
);

create table wife(
    id serial primary key,
    name text
);

create table marriage(
    id serial primary key,
    husband_id int references husband(id) unique,
    wife_id int references wife(id) unique
);

insert into husband(name) values ('Valger Pavel');
insert into wife(name) values ('Valger Alena');
INSERT into marriage(husband_id, wife_id) values (1, 1);

select * from marriage;
select * from husband where id in (select id from marriage);
select * from wife where id in (select id from marriage);