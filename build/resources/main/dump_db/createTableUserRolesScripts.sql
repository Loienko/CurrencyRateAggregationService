--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.1

CREATE SCHEMA IF NOT EXISTS "public";

SET SCHEMA 'public';

create table users (
                          id serial not null,
                          email varchar(50) not null unique,
                          name varchar(50) not null,
                          password varchar(256) not null,
                          username varchar(50) not null
);

create unique index table_name_users_id_uindex on users (id);

alter table users
    add constraint table_name_users_pk
        primary key (id);