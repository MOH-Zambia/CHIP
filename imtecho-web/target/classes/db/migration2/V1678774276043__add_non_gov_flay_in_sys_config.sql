DELETE FROM public.system_configuration
	WHERE system_key='IS_NON_GOV_VAR';
INSERT INTO public.system_configuration(
	system_key, is_active, key_value)
	VALUES ('IS_NON_GOV_VAR', true, true);