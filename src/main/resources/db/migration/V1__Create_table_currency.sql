create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE currency
(
    id                int8        not null,
    bank_name         varchar(50) not null,
    currency_code     varchar(15) not null,
    purchase_currency varchar(15) not null,
    sale_of_currency  varchar(15) not null
);

create unique index table_name_currency_id_uindex on currency (id ASC);

alter table currency
    add constraint table_name_currency_pk
        primary key (id);