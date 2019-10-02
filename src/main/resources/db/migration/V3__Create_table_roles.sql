create table roles
(
    id   int8        not null,
    name varchar(50) not null unique
);

create unique index table_name_roles_id_uindex on roles (id ASC);

alter table roles
    add constraint table_name_roles_pk
        primary key (id);