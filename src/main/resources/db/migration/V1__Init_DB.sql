CREATE TABLE IF NOT EXISTS currency
(
    id                bigserial   not null unique PRIMARY KEY,
    bank_name         varchar(50) not null,
    currency_code     varchar(15) not null,
    purchase_currency varchar(15) not null,
    sale_of_currency  varchar(15) not null
);

create table IF NOT EXISTS users
(
    id       bigserial    not null unique PRIMARY KEY,
    email    varchar(50)  not null unique,
    name     varchar(50)  not null,
    password varchar(256) not null,
    username varchar(50)  not null unique
);

create table IF NOT EXISTS roles
(
    id   bigserial   not null unique PRIMARY KEY,
    name varchar(50) not null unique
);

create table IF NOT EXISTS user_roles
(
    user_id bigint not null
        constraint user_roles_id_user_fk
            references users (id),
    role_id bigint not null
        constraint user_roles_id_roles_fk
            references roles (id)
);

INSERT INTO "currency" ("bank_name", "currency_code", "purchase_currency", "sale_of_currency")  VALUES ('adhas@c.u', 'name', 'weqwe', 'qweqgwe');
INSERT INTO "currency" ("bank_name", "currency_code", "purchase_currency", "sale_of_currency")  VALUES ('adhas@c.u', 'name', 'weqwe', 'qweqgwe');

INSERT INTO "users" ("email", "name", "password", "username")   VALUES ('adhas@c.u', 'name', 'weqwe', 'qweqgwe');
INSERT INTO "users" ("email", "name", "password", "username")   VALUES ('ahwerdas@c.u', 'name', 'weqwe', 'qwheqwe');

INSERT INTO "roles" ("name")    VALUES ('ADMIN');
INSERT INTO "roles" ("name")    VALUES ('USER');