drop table if exists mytecho_pregnant_woman_symptoms_master;
create table mytecho_pregnant_woman_symptoms_master(
id bigserial,
user_id bigint,
member_id bigint,
family_id bigint,
notification_id bigint,
have_cough boolean,
vaginal_bleeding boolean,
burning_micturation boolean,
vaginal_whiteish_discharge boolean,
leaking_before_delivery boolean,
get_tired_soon boolean,
get_tired_household_work boolean,
swelling_on_face_hand_leg boolean,
pale_eyes_palm boolean,
latitude text,
longitude text,
mobile_start_date timestamp without time zone,
mobile_end_date timestamp without time zone,
created_by bigint NOT NULL,
created_on timestamp without time zone NOT NULL,
modified_by bigint,
modified_on timestamp without time zone,
CONSTRAINT mytecho_pregnant_woman_symptoms_master_pkey PRIMARY KEY (id)
);