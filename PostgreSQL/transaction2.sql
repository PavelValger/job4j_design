begin transaction isolation level serializable;

select * from students;

delete from students where id = 1;

commit;

select * from students;