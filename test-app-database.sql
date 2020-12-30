drop database tempodb;
drop user tempouser;
create user tempouser with password 'tempopass';
create database tempodb with template=template0 owner=tempouser;
\connect tempodb;
alter default privileges grant all on tables to tempouser;
alter default privileges grant all on sequences to tempouser;

--LOGIN, SIGNUP - manage users YES

create table users(
    id integer not null primary key,
    user_name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    name varchar(255) not null,
    country varchar(255) not null
);
--HISTORY - recording transactions
create table history(
    query_id integer not null primary key,
    endpoint varchar(255) not null,
    user_name varchar(255) not null,
    number1 integer not null,
    number2 integer not null
);
alter table history add constraint history_users_fk foreign key (user_id) references users(id);

create sequence user_seq increment 1 start 1;
create sequence history_seq increment 1 start 100;
commit;