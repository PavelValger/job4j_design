create table employees(
    id serial primary key,
	name text
);

create table departments(
    id serial primary key,
	name text,
	employee_id int REFERENCES employees(id)
);


insert into employees(name) VALUES ('Аня'), ('Ваня'), ('Боря'), ('Петя');
INSERT into departments(name, employee_id) values ('HR', 1), ('ПО', 2),
('ПО', 3), ('ПО', 4), ('Бережливое производство', null);

select * from departments d left join employees e on d.employee_id = e.id;
select * from departments d right join employees e on d.employee_id = e.id;
select * from departments d full join employees e on d.employee_id = e.id;
select * from departments d CROSS join employees e;

select * from departments d left join employees e on d.employee_id = e.id
where e.id is null;

select d.name, d.employee_id, e.name
from departments d left join employees e on d.employee_id = e.id;
select d.name, d.employee_id, e.name
from employees e right join departments d on d.employee_id = e.id;

create table teens(
    id serial primary key,
	name text,
	gender VARCHAR(255)
);

insert into teens(name, gender) VALUES ('Аня', 'Ж'), ('Ваня', 'М'),
('Боря', 'М'), ('Петя', 'М'), ('Алина', 'Ж');

select distinct t1.name a, t2.name b, (t2.name, t1.name)
as "Разнополая пара" from teens t1 cross join teens t2
where t1.gender != t2.gender limit 6;