create table public.product
(
    id      bigserial
        constraint product_pk
            primary key,
    name    varchar(50)             not null,
    price   double precision        not null,
    created timestamp default now() not null,
    updated timestamp default now() not null
);

alter table public.product
    owner to postgres;

create trigger update_product_timestamp
    before update
    on public.product
    for each row
    execute procedure public.update_timestamp();