create table people(
    id serial primary key,
    name varchar(255)
);

insert into people(name) values ('Pavel'), ('Alena'), ('Petr');

start transaction isolation level SERIALIZABLE;

insert into people(name) values ('Ivan');

savepoint first_savepoint;
select * from people;
delete from people where id = 1;
savepoint second_savepoint;
select * from people;
delete from people;
select * from people;
rollback to second_savepoint;
select * from people;
rollback to first_savepoint;
select * from people;
rollback to second_savepoint;
commit;
select * from people;