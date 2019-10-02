create table user_roles
(
    user_id bigint not null
        constraint user_roles_users_id_fk
            references users (id),
    role_id bigint not null
        constraint user_roles_roles_id_fk
            references roles (id)
);