create table public.users
(
    id               bigserial
        constraint users_pk
            primary key,
    firstname        varchar(20),
    second_name      varchar(20),
    age              integer,
    telephone_number varchar(20)             not null
        unique,
    email            varchar(30)             not null
        unique,
    created          timestamp default now() not null,
    updated          timestamp default now() not null,
    sex              varchar(10),
    is_deleted       boolean   default false
);

alter table public.users
    owner to postgres;

create index idx_users_email
    on public.users (email);

create trigger update_users_timestamp
    before update
    on public.users
    for each row
    execute procedure public.update_timestamp();