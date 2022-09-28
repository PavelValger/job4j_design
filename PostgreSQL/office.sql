create table departments(
    id serial primary key,
	name text
);

create table employees(
    id serial primary key,
	name text,
	department_id int REFERENCES departments(id)
);


INSERT into departments(name) values ('HR'), ('ПО'), ('Бережливое производство');
insert into employees(name, department_id) values ('Аня', 1),
('Ваня', 2), ('Боря', 2), ('Петя', 2), ('Регина', 1);

select * from departments d left join employees e on e.department_id = d.id;
select * from departments d right join employees e on e.department_id = d.id;
select * from departments d full join employees e on e.department_id = d.id;
select * from departments d CROSS join employees e;

select * from departments d left join employees e on e.department_id = d.id
where e.id is null;

select d.name "Отдел", e.department_id, e.name "Сотрудник"
from departments d left join employees e on e.department_id = d.id;
select d.name "Отдел", e.department_id, e.name "Сотрудник"
from employees e right join departments d on e.department_id = d.id;

create table teens(
    id serial primary key,
	name text,
	gender VARCHAR(255)
);

insert into teens(name, gender) VALUES ('Аня', 'Ж'), ('Ваня', 'М'),
('Боря', 'М'), ('Петя', 'М'), ('Алина', 'Ж');

select t1.name a, t2.name b, (t2.name, t1.name)
as "Разнополая пара" from teens t1 cross join teens t2
where t1.gender != t2.gender and t1.gender = 'Ж';