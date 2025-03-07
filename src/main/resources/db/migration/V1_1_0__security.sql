create table public.security
(
    id       bigserial
        constraint security_pk
            primary key,
    login    varchar(20)                                   not null
        unique,
    password varchar(255)                                  not null,
    salt     varchar(255)                                  not null
    role     varchar(20) default 'USER'::character varying not null,
    created  timestamp   default now()                     not null,
    updated  timestamp   default now()                     not null,
    user_id  bigint                                        not null
        unique
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade,

);

alter table public.security
    owner to postgres;

create index idx_security_login
    on public.security (login);

create trigger update_security_timestamp
    before update
    on public.security
    for each row
    execute procedure public.update_timestamp();

