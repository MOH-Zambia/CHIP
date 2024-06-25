ALTER TABLE health_infrastructure_details
DROP COLUMN IF EXISTS hfr_facility_id,
ADD COLUMN hfr_facility_id character varying(200),
ADD CONSTRAINT unique_hfr_facility_id UNIQUE (hfr_facility_id),
DROP COLUMN IF EXISTS other_details,
ADD COLUMN other_details text,
DROP COLUMN IF EXISTS speciality_type,
ADD COLUMN speciality_type varchar(50),
DROP COLUMN IF EXISTS type_of_services,
ADD COLUMN type_of_services varchar(100),
DROP COLUMN IF EXISTS ownership_code,
ADD COLUMN ownership_code varchar(100),
DROP COLUMN IF EXISTS ownership_sub_type_code,
ADD COLUMN ownership_sub_type_code varchar(100),
DROP COLUMN IF EXISTS ownership_sub_type_code2,
ADD COLUMN ownership_sub_type_code2 varchar(100),
DROP COLUMN IF EXISTS system_of_medicine_code,
ADD COLUMN system_of_medicine_code varchar(200),
DROP COLUMN IF EXISTS facility_type_code,
ADD COLUMN facility_type_code varchar(200),
DROP COLUMN IF EXISTS facility_sub_type_code,
ADD COLUMN facility_sub_type_code varchar(200),
DROP COLUMN IF EXISTS facility_region,
ADD COLUMN facility_region varchar(200),
DROP COLUMN IF EXISTS is_link_to_bridge_id,
ADD COLUMN is_link_to_bridge_id boolean DEFAULT false;