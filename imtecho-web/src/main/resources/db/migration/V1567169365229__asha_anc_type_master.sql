INSERT INTO public.notification_type_master(
            created_by, created_on, modified_by, modified_on, code, name, 
            type, role_id, state, notification_for)
    VALUES (-1, now(), -1, now(), 'ASHA_ANC', 'Asha Anc Service Visit', 
            'MO', '24', 'ACTIVE', 'MEMBER');