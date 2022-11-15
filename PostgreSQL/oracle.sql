-- Database: oracle

-- DROP DATABASE IF EXISTS oracle;

CREATE DATABASE oracle
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

CREATE TABLE company
(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person
(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

insert into company(id, name) values (1, 'Oracle');
insert into company(id, name) values (2, 'Java');
insert into company(id, name) values (3, 'SQL');
insert into company(id, name) values (4, 'Yandex');
insert into company(id, name) values (5, 'VK');

insert into person(id, name, company_id) values (1, 'Ivan', 1); 
insert into person(id, name, company_id) values (2, 'Alexander', 2);
insert into person(id, name, company_id) values (3, 'Maxim', 3);
insert into person(id, name, company_id) values (4, 'Artem', 4);
insert into person(id, name, company_id) values (5, 'Maria', 5);
insert into person(id, name, company_id) values (6, 'Daniil', 4);
insert into person(id, name, company_id) values (7, 'Boris', 2);

SELECT pr.name as "Name of the person", cmp.name as "Company name"
from person pr
join company cmp 
on pr.company_id = cmp.id
where cmp.id != 5;

SELECT cmp.name as "Company name", count(pr.company_id) as "Number of persons"
from person pr
join company cmp
on pr.company_id = cmp.id,
(
SELECT max(counter.total) as maximum
from (
SELECT cmp.name, count(pr.company_id) as total
from person pr
join company cmp
on pr.company_id = cmp.id
GROUP by cmp.name) as counter) as meaning
GROUP by cmp.name, meaning.maximum
having meaning.maximum = count(pr.company_id);