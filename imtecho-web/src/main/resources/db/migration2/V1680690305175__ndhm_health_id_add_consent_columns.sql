alter table ndhm_health_id_user_details
drop COLUMN IF EXISTS is_details_match_with_beneficiary_consent_given,
add COLUMN is_details_match_with_beneficiary_consent_given boolean;