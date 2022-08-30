create table fauna (
    id serial primary key,
    name text,
    avg_age int,
    discovery_date date
);

INSERT into fauna("name", avg_age, discovery_date) VALUES ('fish', 3650, '01.01.1000');
INSERT into fauna("name", avg_age) VALUES ('birds', 8000);
INSERT into fauna("name", avg_age, discovery_date) VALUES ('reptiles', 11000, '05.03.1800');
INSERT into fauna("name", avg_age, discovery_date) VALUES ('mammals', 36000, '05.03.0500');

select * from fauna where name like '%fish%';
select * from fauna where avg_age >= 10000 and avg_age <= 21000;
select * from fauna where discovery_date is null;
select * from fauna where discovery_date < '01.01.1950';