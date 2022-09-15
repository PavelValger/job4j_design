insert into products (name, producer, count, price)
VALUES ('apple', 'tree', 3, 100);
insert into products (name, producer, count, price)
VALUES ('orange', 'Africa', 2, 500);
insert into products (name, producer, count, price)
VALUES ('grape', 'Uzbekistan', 0, 700);

create or replace procedure delete_product(d_id int)
language 'plpgsql'
as $$
    BEGIN
	delete from products where id = d_id;
	delete from products where count = 0;
    END
$$;

call delete_product(2);

create or replace function f_delete_product(d_id int)
returns varchar(50)
language 'plpgsql'
as
$$
declare
        result varchar(50);
    begin
	select into result name from products where id = d_id;
        delete from products where id = d_id;
		return result;
    end;
$$;

select f_delete_product(1);