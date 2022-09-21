create table students(
    id serial primary key, name text
);

insert into students(name) values ('Аня'), ('Ваня'), ('Боря');

begin transaction isolation level serializable;

select * from students;

update students set name = 'Олег' where id = 1;

commit;

select * from students;