create table if not exists location_wise_rch_reports_analytics (
	loc_id bigint,
	reg_preg_women integer,
	high_risk_mothers integer,
	risk_with_two_or_more_factors integer,
	prev_preg_complications integer,
	pre_existing_chronic integer,
	present_preg_complications integer,
	severe_anemia integer,
	blood_pressure integer,
	gestational_diabetes integer,
	mal_presentation integer,
	extreme_age integer,
	interval_bet_preg_less_18_months integer,
	height_less_140 integer,
	weight_less_45 integer,
	urine_albumin_present integer,
	women_regd_with_live_children integer,
	women_regd_with_0_children integer,
	c1_male integer,
	c1_female integer,
	c2_male integer,
	c2_female integer,
	c2_1_male_1_female integer,
	c3_3_male integer,
	c3_3_female integer,
	c3_2_male_1_female integer,
	c3_1_male_2_female integer,
	anc_in_2_or_3_trimester integer,
	albendazole_administration integer,
	anc_albendazole_not_given integer,
	hbsag_test_done integer, 
	total_reactive integer,
	total_non_reactive integer,
	constraint location_wise_rch_reports_analytics_pkey primary key (loc_id)
);