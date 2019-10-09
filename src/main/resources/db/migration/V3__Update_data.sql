UPDATE "public"."users" SET "password" = '1234567', "status" = 'ACTIVE' WHERE "id" = 1;
alter table users alter column created drop not null;
alter table users alter column updated drop not null;
alter table user_details alter column phone type VARCHAR(17) using phone::VARCHAR(17);
create unique index user_details_user_id_uindex on user_details (user_id);