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

create table items
(
    id serial not null,
    product_id int not null,
    quantity int not null,
    constraint items_products__fk
        foreign key (product_id) references products
);

create unique index items_id_uindex
    on items (id);

alter table items
    add constraint items_pk
        primary key (id);

create table carts
(
    id serial not null,
    user_id int not null,
    sum_price int,
    constraint carts_users__fk
        foreign key (user_id) references users
);

create unique index carts_id_uindex
    on carts (id);

alter table carts
    add constraint carts_pk
        primary key (id);

alter table items
    add cart_id int;

alter table items
    add constraint items_carts__fk
        foreign key (cart_id) references carts;

alter table orders
    add cart_id int;

alter table orders
    add constraint orders_carts__fk
        foreign key (cart_id) references carts;

create table categories
(
    id serial not null,
    name text not null,
    description text not null
);

create unique index categories_id_uindex
    on categories (id);

alter table categories
    add constraint categories_pk
        primary key (id);

create table suppliers
(
    id serial not null,
    name text not null
);

create unique index suppliers_id_uindex
    on suppliers (id);

alter table suppliers
    add constraint suppliers_pk
        primary key (id);

alter table products
    add category_id int;

alter table products
    add supplier_id int;

alter table products
    add constraint products_categories__fk
        foreign key (category_id) references categories;

alter table products
    add constraint products_suppliers__fk
        foreign key (supplier_id) references suppliers;

INSERT INTO categories(name, description)
VALUES
('Big cat', 'Between 40-300 kg'),
('Mid-size cat', 'Between 15-40 kg'),
('Small cat', 'Up to 15 kg');

INSERT INTO suppliers(name)
VALUES
('Africa'),
('Asia'),
('Europe'),
('North-America'),
('South-America');