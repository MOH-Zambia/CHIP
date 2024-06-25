CREATE OR REPLACE FUNCTION public.location_master_insert_trigger_func()
  RETURNS trigger AS
$BODY$
BEGIN

	INSERT INTO public.location_hierchy_closer_det(
            child_id, child_loc_type, depth, parent_id, parent_loc_type)
    VALUES ( NEW.id, NEW.type, 0, NEW.id, NEW.type);

	if NEW.parent is not null then
		INSERT INTO public.location_hierchy_closer_det(
            child_id, child_loc_type, depth, parent_id, parent_loc_type)
		SELECT  c.child_id,c.child_loc_type, p.depth+c.depth+1,p.parent_id,p.parent_loc_type
		FROM location_hierchy_closer_det p, location_hierchy_closer_det c
		WHERE p.child_id = NEW.parent AND c.parent_id = NEW.id;
	END if;
	
	insert into location_wise_analytics (loc_id)
	VALUES (NEW.id);

if (select case when key_value = 'P' then true else false end from system_configuration where system_key = 'SERVER_TYPE') then
	PERFORM dblink_exec
	(
		'dbname='||(select key_value from system_configuration where system_key = 'TRAINING_DB_NAME'),
		 'INSERT INTO location_master(
            id, address, associated_user, contact1_email, contact1_name, 
            contact1_phone, contact2_email, contact2_name, contact2_phone, 
            created_by, created_on, is_active, is_archive, max_users, modified_by, 
            modified_on, name, pin_code, type, unique_id, parent, is_tba_avaiable, 
            total_population, location_hierarchy_id, location_code, state)
	       Values ('|| quote_nullable(NEW.id) || '
			, '||quote_nullable(NEW.address) ||'
			, '||quote_nullable(NEW.associated_user) ||'
			, '||quote_nullable(NEW.contact1_email) ||'
			, '||quote_nullable(NEW.contact1_name) ||'
			, '||quote_nullable(NEW.contact1_phone) ||'
			, '||quote_nullable(NEW.contact2_email) ||'
			, '||quote_nullable(NEW.contact2_name) ||'
			, '||quote_nullable(NEW.contact2_phone) ||'
			, '||quote_nullable(NEW.created_by) ||'
			, '||quote_nullable(NEW.created_on) ||'
			, '||quote_nullable(NEW.is_active) ||'
			, '||quote_nullable(NEW.is_archive) ||'
			, '||quote_nullable(NEW.max_users) ||'
			, '||quote_nullable(NEW.modified_by) ||'
			, '||quote_nullable(NEW.modified_on) ||'
			, '||quote_nullable(NEW.name) ||'
			, '||quote_nullable(NEW.pin_code) ||'
			, '||quote_nullable(NEW.type) ||'
			, '||quote_nullable(NEW.unique_id) ||'
			, '||quote_nullable(NEW.parent) ||'
			, '||quote_nullable(NEW.is_tba_avaiable) ||'
			, '||quote_nullable(NEW.total_population) ||'
			, '||quote_nullable(NEW.location_hierarchy_id) ||'
			, '||quote_nullable(NEW.location_code) ||'
			, '||quote_nullable(NEW.state) ||');'
        ); 
	end if;

	if new.type='SC' or new.type='P' then
			INSERT INTO rch_institution_master(name,location_id,type,is_location,state) VALUES (new.name,new.id,new.type,true,'active');
	end if;
   
	RETURN NEW;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;