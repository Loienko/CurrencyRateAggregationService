--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 11.1

CREATE TABLE public.currency (
                                id integer NOT NULL,
                                bank_name character varying(50),
                                currency_code character varying(15),
                                purchase_currency character varying(15),
                                sale_of_currency character varying(15)
)
WITH (oids = false);

CREATE INDEX currency_seq ON public.currency USING btree (id);

ALTER TABLE ONLY currency
  ADD CONSTRAINT currency_pkey
PRIMARY KEY (id);

COMMENT ON SCHEMA public IS 'standard public schema';