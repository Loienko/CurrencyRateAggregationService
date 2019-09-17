--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.1

create table currency (
    id serial not null,
    bank_name varchar(50) not null,
    currency_code varchar(15) not null,
    purchase_currency varchar(15) not null,
    sale_of_currency varchar(15) not null,
    version int default 0
);

create unique index table_name_id_uindex on currency (id);

alter table currency
    add constraint table_name_pk
        primary key (id);