create function public.log_user_action() returns trigger
    language plpgsql
as
$$
BEGIN
INSERT INTO public.user_actions (user_id, product_id, action, action_time)
VALUES (NEW.user_id, NEW.product_id, 'selected', NOW());
RETURN NEW;
END;
$$;

alter function public.log_user_action() owner to postgres;