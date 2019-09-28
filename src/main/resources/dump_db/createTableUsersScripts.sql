--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.1

CREATE SCHEMA IF NOT EXISTS "public";

SET SCHEMA 'public';

create table user_roles (
    user_id bigint not null
        constraint user_roles_users_id_fk
            references users (id),
    role_id bigint not null
        constraint user_roles_roles_id_fk
            references roles (id)
);