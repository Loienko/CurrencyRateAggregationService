create table users
(
    id       int8         not null,
    email    varchar(50)  not null unique,
    name     varchar(50)  not null,
    password varchar(256) not null,
    username varchar(50)  not null unique
);

create unique index table_name_users_id_uindex on users (id ASC);

alter table users
    add constraint table_name_users_pk
        primary key (id);
