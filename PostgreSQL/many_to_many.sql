create table TVseries(
    id serial primary key,
    name text
);

create table voice(
    id serial primary key,
    name text
);

create table voice_acting(
    id serial primary key,
    TVseries_id int references TVseries(id),
    voice_id int references voice(id)
);

insert into TVseries(name) values ('Game of thrones');
insert into TVseries(name) values ('Supernatural');
insert into voice(name) values ('LostFilm');
insert into voice(name) values ('NovaFilm');
INSERT into voice_acting(TVseries_id, voice_id) values (1, 1);
INSERT into voice_acting(TVseries_id, voice_id) values (1, 2);
INSERT into voice_acting(TVseries_id, voice_id) values (2, 1);

select * from TVseries;
select * from voice;
select * from voice_acting;
select * from TVseries where id in (select id from voice_acting); 
select * from voice where id in (select id from voice_acting);