create table products (
    id serial primary key,
    name varchar(50),
    producer varchar(50),
    count integer default 0,
    price integer
);

create or replace function tax10()
    returns trigger as
$$
    BEGIN
        update products
        set price = price + price * 0.1
        where id = (select id from inserted);
        return new;
    END;
$$
LANGUAGE 'plpgsql';

create trigger tax10_trigger
    after insert on products
    referencing new table as inserted
    for each statement
    execute procedure tax10();

insert into products (name, producer, count, price)
VALUES ('apple', 'tree', 3, 100);

alter table products disable trigger tax10_trigger;

create or replace function tax20()
    returns trigger as
$$
    BEGIN
		new.price = new.price + new.price * 0.2;
        return NEW;
    END;
$$
LANGUAGE 'plpgsql';

create trigger tax20_trigger
    BEFORE insert
    on products
    for each row
    execute procedure tax20();

insert into products (name, producer, count, price)
VALUES ('orange', 'Africa', 2, 500);

create table history_of_price (
    id serial primary key,
    name varchar(50),
    price integer,
    date timestamp
);

create or replace function history()
    returns trigger as
$$
    BEGIN
        insert into history_of_price (name, price, date)
		values (new.name, new.price, current_date);
        return NEW;
    END;
$$
LANGUAGE 'plpgsql';

create trigger history_trigger
    after insert
    on products
    for each row
    execute procedure history(); 

insert into products (name, producer, count, price)
VALUES ('banana', 'Africa', 4, 100);
