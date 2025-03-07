create function public.update_timestamp() returns trigger
    language plpgsql
as
$$
BEGIN
    NEW.updated = NOW();
RETURN NEW;
END;
$$;

alter function public.update_timestamp() owner to postgres;