drop table if exists chardham_tourist_screening_master;

CREATE TABLE if not exists chardham_tourist_screening_master(
id serial,
uuid uuid not null unique default uuid_generate_v4(),
member_unique_id text not null,
health_infra_id integer not null,
oxygen_value integer,
blood_pressure text,
screening_status text not null,
symptoms text,
treatment text,
blood_sugar_test_value integer,
temperature integer,
created_by integer NOT NULL,
created_on timestamp without time zone NOT NULL,
modified_by integer not null,
modified_on timestamp without time zone not null,
PRIMARY KEY (id)
);

ALTER TABLE chardham_tourist_screening_master
    ADD COLUMN if not exists systolic_bp integer,
    ADD COLUMN if not exists diastolic_bp integer,
	DROP COLUMN  if exists blood_pressure;