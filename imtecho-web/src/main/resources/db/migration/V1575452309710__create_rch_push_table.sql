CREATE TABLE anmol_eligible_couple_tracking (
  serial_id bigserial NOT NULL PRIMARY KEY,
  member_id int8 NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  id int8 NULL,
  rural_urban text NULL,
  pregnant text NULL,
  "method" text NULL,
  other varchar NULL,
  pregnant_test text NULL,
  visitdate text NULL,
  created_on text NULL,
  created_date timestamp NULL,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  created_by int8 NULL,
  whose_mobile text NULL,
  case_no int4 NULL,
  rchlmpfollowupid int8 null unique,
  visit_no int8 null,
  is_upload boolean default false
);

CREATE TABLE anmol_mother_registration (
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  mobile_relates_to text NULL,
  created_by int8 NULL,
  jsy_beneficiary text NULL,
  jsy_payment_received text NULL,
  rural_urban text NULL,
  bpl_apl text NULL,
  age float8 NULL,
  address text NULL,
  name_pw text NULL,
  name_h text NULL,
  created_on text NULL,
  created_date timestamp NULL,
  registration_date text NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  weight float4 NULL,
  caste int4 NULL,
  religion_code int4 NULL,
  mobile_no text NULL,
  member_id int8 NULL,
  anmol_master_id int8 NULL,
  case_no int4 NULL,
  pregnancy_reg_det_id int8 NULL UNIQUE,
  birth_date text null,
  is_upload boolean default false,
  serial_id bigserial NOT NULL PRIMARY KEY
);

CREATE TABLE anmol_mother_medical (
  serial_id bigserial NOT NULL PRIMARY KEY,
  rural_urban text NULL,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  mobile_relates_to unknown NULL,
  created_by int8 NULL,
  lmp_date text NULL,
  edd_date text NULL,
  reg_12weeks bool NULL,
  created_on text NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  member_id int8 NULL,
  anmol_master_id int8 NULL,
  case_no int4 NULL,
  pregnancy_reg_det_id int8 null unique,
  created_date timestamp NULL,
  l2l_preg_complication text NULL,
  past_illness unknown NULL,
  outcome_l2l_preg text NULL,
  no_of_pregnancy int4 NULL,
  outcome_last_preg int4 NULL,
  past_illness_length int4 NULL,
  deliverylocationid int4 NULL,
  l2l_preg_complication_length int4 NULL,
  bloodgroup_test int4 NULL,
  expected_delivery_place_updated int4 NULL,
  blood_group text NULL,
  vdrl_date text NULL,
  hiv_test bool NULL,
  hiv_result text NULL,
  vdrl_test bool NULL,
  vdrl_result text NULL,
  expected_delivery_place int4 NULL,
  last_preg_complication text NULL,
  last_preg_complication_length int4 null,
  is_upload boolean default false
);

CREATE TABLE anmol_mother_anc (
  serial_id bigserial NOT NULL PRIMARY KEY,
  rch_anc_master_id int8 NULL UNIQUE,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  facilityplaceancdone int4 NULL,
  facilitylocationancdone text NULL,
  facilitylocationidancdone text NULL,
  created_by int8 NULL,
  anc_type int4 NULL,
  bp_systolic int4 NULL,
  bp_distolic int4 NULL,
  abortion_ifany bool NULL,
  lmp text NULL,
  abortion_preg_weeks int4 NULL,
  abortion_date text NULL,
  abortion_type int4 NULL,
  induced_indicate_facility int4 NULL,
  abdoman_fh int4 NULL,
  abdoman_fp int4 NULL,
  ppmc int4 NULL,
  weight float4 NULL,
  created_date timestamp NULL,
  hb_gm float4 NULL,
  maternal_death text NULL,
  death_reason text NULL,
  blood_sugar_fasting int4 NULL,
  blood_sugar_post_prandial int4 NULL,
  ifa_given int4 NULL,
  fa_given int4 NULL,
  totalweek int4 NULL,
  pregnancy_month int4 NULL,
  foetal_movements int4 NULL,
  urine_sugar text NULL,
  urine_albumin text NULL,
  urine_test int4 NULL,
  bloodsugar_test int4 NULL,
  death_date text NULL,
  rural_urban text NULL,
  created_on text NULL,
  anc_date text NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  case_no int4 NULL,
  member_id int8 NULL,
  vdrl_date text NULL,
  symptoms_high_riskarray text NULL,
  other_symptoms_high_risk varchar(500) NULL,
  anc_no int8 NULL,
  symptoms_high_risk text NULL,
  symptoms_high_risk_length int4 NULL,
  other_death_reason text NULL,
  visit_id int8 NULL,
  tt1_date text NULL,
  tt_date text NULL,
  tt2_date text NULL,
  ttb_date text NULL,
  tt_code int4 NULL,
  is_upload boolean default false
);

CREATE TABLE anmol_mother_deliveries (
  serial_id bigserial NOT NULL PRIMARY KEY,
  rural_urban text NULL,
  discharge_date text NULL,
  discharge_time text NULL,
  delivery_date text NULL,
  delivery_time text NULL,
  delivery_location text NULL,
  delivery_complication_array text [] NULL,
  otherdelivery_complication text NULL,
  delivery_type int4 NULL,
  death_cause int4 NULL,
  jsy_benificiary bool NULL,
  jsy_payment_received text NULL,
  date_of_delivery timestamp NULL,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  created_by int8 NULL,
  case_no int4 NULL,
  delivery_place int4 NULL,
  deliverylocationid int4 NULL,
  deliverylocation int4 NULL,
  delivery_conducted_by int4 NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  member_id int8 NULL,
  anmol_master_id int8 NULL,
  created_date timestamp NULL,
  rch_wpd_mother_master_id int8 null unique,
  created_on text NULL,
  delivery_complication text NULL,
  wpd_mother_id int8 NULL,
  livebirth int8 NULL,
  stillbirth int8 NULL,
  delivery_outcomes int8 NULL,
  is_upload boolean default false
);

CREATE TABLE anmol_mother_infants (
  serial_id bigserial NOT NULL PRIMARY KEY,
  breast_feeding bool NULL,
  baby_cried_immediately_at_birth text NULL,
  weight float4 NULL,
  gender_infant varchar(100) NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  infant_name text NULL,
  rural_urban text NULL,
  infant_no int8 NULL,
  resucitation_done int4 NULL,
  created_on text NULL,
  member_id int8 NULL,
  pregnancy_reg_det_id int8 NULL,
  child_id int8 NULL,
  created_date timestamp NULL,
  rwcmid int8 NULL,
  rch_wpd_child_master_id int8 NULL UNIQUE,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  case_no int4 NULL,
  anmol_master_id int8 NULL,
  created_by int8 NULL,
  infant_term text NULL,
  inj_corticosteriods_given text NULL,
  higher_facility unknown NULL,
  visit_id int8 NULL,
  opv_date date NULL,
  bcg_date date NULL,
  hep_b_date date NULL,
  vit_k_date date NULL,
  any_defect_seen_at_birth int4 NULL,
  wpd_id int8 null,
  is_upload boolean default false
);

CREATE TABLE anmol_mother_pnc (
  serial_id bigserial NOT NULL PRIMARY KEY,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  created_by int8 NULL,
  rural_urban text NULL,
  no_of_ifa_tabs_given_to_mother int4 NULL,
  mother_death bool NULL,
  mother_death_date text NULL,
  mother_death_reason text NULL,
  pnc_date text NULL,
  mother_death_reason_other varchar(50) NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  case_no int4 NULL,
  ppc text NULL,
  otherppc_method text NULL,
  dangersign_mother_other varchar(999) NULL,
  pnc_type int4 NULL,
  dangersign_mother text NULL,
  dangersign_mother_length int4 NULL,
  member_id int8 NULL,
  created_date timestamp NULL,
  created_on text NULL,
  pnc_no int8 NULL,
  rch_pnc_mother_master_id int8 NULL UNIQUE,
  is_upload boolean default false
);

CREATE TABLE anmol_child_pnc (
  serial_id bigserial NOT NULL PRIMARY KEY,
  pnc_date text NULL,
  created_on text NULL,
  created_date timestamp NULL,
  infant_death_date text NULL,
  infant_death bool NULL,
  place_of_death int4 NULL,
  infant_death_reason text NULL,
  infant_death_reason_other text NULL,
  infant_death_reason_length int4 NULL,
  rural_urban text NULL,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  created_by int8 NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  infantregistration varchar(255) NULL,
  case_no int4 NULL,
  infant_weight float4 NULL,
  member_id int8 NULL,
  pnc_type int4 NULL,
  child_id int8 NULL,
  rch_pnc_child_master_id int8 null unique,
  dangersign_infant_other text NULL,
  dangersign_infant_array text NULL,
  pnc_no int8 NULL,
  dangersign_infant text NULL,
  dangersign_infant_length int4 null,
  is_upload boolean default false
);

CREATE TABLE anmol_child_tracking (
  serial_id bigserial NOT NULL PRIMARY KEY,
  immunisation_given varchar(50) NULL,
  visit_id int8 NULL,
  visit_type varchar(50) NULL,
  rural_urban text NULL,
  state_code int8 NULL,
  district_code int8 NULL,
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  created_by int8 NULL,
  case_no int4 NULL,
  immu_code int4 NULL,
  immu_date text NULL,
  deathdate timestamp NULL,
  death_reason int4 NULL,
  deathplace int4 NULL,
  immu_source int4 NULL,
  fully_immunized int4 NULL,
  aefi_serious int4 NULL,
  serious_reason int4 NULL,
  reason_closure int4 NULL,
  closure_remarks int4 NULL,
  received_allvaccines int4 NULL,
  case_closure bool NULL,
  vac_manuf varchar(50) NULL,
  vac_exp_date timestamp NULL,
  vac_batch varchar(50) NULL,
  vac_name varchar(50) NULL,
  other_death_reason text NULL,
  registration_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  child_id int8 NULL,
  immu_id int8 NULL unique,
  service_id int8 null,
  is_upload boolean default false
);

CREATE TABLE anmol_child_tracking_medical (
	serial_id bigserial NOT NULL PRIMARY KEY,
	complentary_feeding text NULL,
	visit_date text NULL,
	created_on text NULL,
	created_date timestamp NULL,
	breastfeeding text NULL,
	child_weight float4 NULL,
	month_complentary_feeding int4 NULL,
	rural_urban text NULL,
	diarrhoea int4 NULL,
	ors_given int4 NULL,
	pneumonia int4 NULL,
	antibiotics_given int4 NULL,
	immu_code int4 NULL,
	state_code int8 NULL,
	district_code int8 NULL,
	taluka_code varchar(255) NULL,
	village_code int8 NULL,
	healthfacility_code int8 NULL,
	healthsubfacility_code int8 NULL,
	healthblock_code int8 NULL,
	healthfacility_type int8 NULL,
	asha_id int8 NULL,
	anm_id int8 NULL,
	case_no int4 NULL,
	created_by int8 NULL,
	service_id int8 null unique,
	registration_no varchar(255) NULL,
	mobile_id varchar(255) null,
	is_upload boolean default false
);

CREATE TABLE anmol_child_registration (
  serial_id bigserial NOT NULL PRIMARY KEY,
  state_code int8 NULL,
  district_code int, -- select8 NULL
  taluka_code varchar(255) NULL,
  village_code int8 NULL,
  healthfacility_code int8 NULL,
  healthsubfacility_code int8 NULL,
  healthblock_code int8 NULL,
  healthfacility_type int8 NULL,
  asha_id int8 NULL,
  anm_id int8 NULL,
  rural_urban text NULL,
  registration_date text NULL,
  gender text NULL,
  birth_date text NULL,
  name_child text NULL,
  name_mother text NULL,
  name_father text NULL,
  mobile_no text NULL,
  address text NULL,
  caste int4 NULL,
  religion_code int4 NULL,
  identity_type int4 NULL,
  created_by int8 NULL,
  mobile_relates_to unknown NULL,
  case_no int4 NULL,
  status int4 NULL,
  birth_place int4 NULL,
  delivery_location text NULL,
  register_srno int4 NULL,
  deliverylocationid int4 NULL,
  child_aadhar_no varchar(32) NULL,
  mother_reg_no varchar(255) NULL,
  mobile_id varchar(255) NULL,
  registration_no varchar(255) NULL,
  weight float4 NULL,
  created_date timestamp NULL,
  anmol_master_id int8 NULL,
  member_id int8 NULL,
  child_id int8 NULL unique,
  anmol_child_master_id int8 null,
  is_upload boolean default false
);