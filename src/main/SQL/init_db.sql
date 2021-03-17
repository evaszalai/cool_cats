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

