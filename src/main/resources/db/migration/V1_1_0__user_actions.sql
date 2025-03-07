create table public.user_actions
(
    id          bigserial
        primary key,
    user_id     bigint                  not null
        references public.users
            on delete cascade,
    product_id  bigint                  not null
        references public.product
            on delete cascade,
    action      varchar(50)             not null,
    action_time timestamp default now() not null
);

alter table public.user_actions
    owner to postgres;