create table users
(
    id serial not null,
    first_name varchar not null,
    last_name varchar not null,
    country varchar not null,
    address varchar not null,
    email varchar not null,
    password varchar not null,
    salt varchar not null
);

create unique index users_id_uindex
    on users (id);

alter table users
    add constraint users_pk
        primary key (id);

create table orders
(
    id serial not null,
    user_id int not null,
    date date not null,
    constraint orders_users__fk
        foreign key (user_id) references users
);

create unique index orders_id_uindex
    on orders (id);

alter table orders
    add constraint orders_pk
        primary key (id);

create table products
(
    id serial not null,
    name varchar,
    description varchar,
    price int not null
);

create unique index products_id_uindex
    on products (id);

alter table products
    add constraint products_pk
        primary key (id);