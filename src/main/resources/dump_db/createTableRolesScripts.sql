--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.1

CREATE SCHEMA IF NOT EXISTS "public";

SET SCHEMA 'public';

create table roles (
                          id serial not null,
                          name varchar(50) not null
);

create unique index table_name_roles_id_uindex on roles (id);

alter table roles
    add constraint table_name_roles_pk
        primary key (id);