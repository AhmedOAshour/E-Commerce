-- use db_example;

insert ignore into cart values('1');
insert ignore into user(type, id, active, email, password, name, cart_id) values(0, '123', true, 'test@mail.com', '{noop}1234', 'test testing', 1);