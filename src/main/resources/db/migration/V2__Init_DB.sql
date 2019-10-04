alter table users add created   timestamp   not null default now();
alter table users add updated   timestamp   not null default now();
alter table users add status    VARCHAR(15) not null default 'NOT_ACTIVE';

create table IF NOT EXISTS user_details
(
    id          bigserial    not null unique PRIMARY KEY,
    surname     varchar(50)  not null,
    phone       bigint,
    description varchar(256) not null,
    user_id     bigint       not null
        constraint user_id_fk references users (id)
);

INSERT INTO "user_details" (surname, phone, description, user_id)
VALUES ('admin', '123456789', 'just admin', 1);