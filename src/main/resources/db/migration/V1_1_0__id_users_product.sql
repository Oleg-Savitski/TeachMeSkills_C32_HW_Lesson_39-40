create table public.id_users_product
(
    id         bigint    default nextval('l_users_product_id_seq'::regclass) not null
        constraint id_users_product_pk
            primary key,
    user_id    bigint                                                        not null
        constraint id_users_product_users_id_fk
            references public.users
            on update cascade on delete cascade,
    product_id bigint                                                        not null
        constraint id_users_product_product_id_fk
            references public.product
            on update cascade on delete cascade,
    created    timestamp default now()                                       not null,
    updated    timestamp default now()                                       not null
);

alter table public.id_users_product
    owner to postgres;

create index idx_l_users_product_user_id
    on public.id_users_product (user_id);

create index idx_l_users_product_product_id
    on public.id_users_product (product_id);

create trigger update_l_users_product_timestamp
    before update
    on public.id_users_product
    for each row
    execute procedure public.update_timestamp();

create trigger log_user_product_selection
    after insert
    on public.id_users_product
    for each row
    execute procedure public.log_user_action();