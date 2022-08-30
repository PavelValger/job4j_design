insert into role(name) values ('Admin');
insert into role(name) values ('Visitor');

insert into users(name, role_id) values ('Pavel', 1);
insert into users(name, role_id) values ('Alena', 1);
insert into users(name, role_id) values ('Petr', 2);

insert into rules(name) values ('All');
insert into rules(name) values ('Restricted access');

INSERT into role_rules(role_id, rules_id) values (1, 1);
INSERT into role_rules(role_id, rules_id) values (2, 2);

insert into category(name) values ('Critical');
insert into category(name) values ('Regular');

insert into state(name) values ('Open');
insert into state(name) values ('Close');

INSERT into item(name, users_id, category_id, state_id) values ('1', 2, 2, 2);
INSERT into item(name, users_id, category_id, state_id) values ('2', 2, 2, 1);
INSERT into item(name, users_id, category_id, state_id) values ('3', 1, 1, 1);

INSERT into comments(name, item_id) values ('Как стать админом?', 1);
INSERT into comments(name, item_id) values ('СРОЧНО!!!', 3);

INSERT into attachs(name, item_id) values ('Manual', 1);
INSERT into attachs(name, item_id) values ('pom.xml', 3);