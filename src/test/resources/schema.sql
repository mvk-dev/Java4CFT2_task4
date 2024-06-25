drop table if exists logins;
drop table if exists users;

create table users(id serial primary key, username text, fio text);
create table logins(id serial primary key, access_date timestamp, user_id bigint references users(id), application text);






