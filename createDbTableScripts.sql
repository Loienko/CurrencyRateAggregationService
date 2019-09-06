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
    sale_of_currency varchar(15) not null
);

create unique index table_name_id_uindex on currency (id);

alter table currency
    add constraint table_name_pk
        primary key (id);

-- test to verify DB operation
insert into currency (bank_name, currency_code,purchase_currency, sale_of_currency) values('PrivatBank', 'USD', '25.1', '26.06');