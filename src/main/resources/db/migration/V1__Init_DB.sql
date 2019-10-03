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
    name     varchar(50)  not null,
    username varchar(50)  not null unique,
    password varchar(256) not null,
    email    varchar(100) not null,
    created  timestamp    not null default now(),
    updated  timestamp    not null default now(),
    status   VARCHAR(15)  not null
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

create table IF NOT EXISTS user_details
(
    id          bigserial    not null unique PRIMARY KEY,
    surname     varchar(50)  not null,
    phone       bigint,
    description varchar(256) not null,
    user_id     bigint       not null
        constraint user_id_fk references users (id)
);


INSERT INTO "users" ("id", "name", "username", "password", "email", "status")
VALUES ('1', 'admin', 'admin', 'admin', 'admin@ukr.net', 'active');

INSERT INTO "roles" (id, "name")
VALUES ('1', 'ADMIN');
INSERT INTO "roles" (id, "name")
VALUES ('2', 'USER');

INSERT INTO "user_roles" (user_id, role_id)
VALUES ('1', '1');

INSERT INTO "user_details" (surname, phone, description, user_id)
VALUES ('admin', '123456789', 'just admin', 1);