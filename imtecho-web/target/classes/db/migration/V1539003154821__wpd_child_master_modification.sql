alter table rch_wpd_child_master
drop column if exists was_premature,
drop column if exists referral_reason,
drop column if exists referral_transport,
drop column if exists referral_place,
drop column if exists name,
drop column if exists breast_crawl,
drop column if exists kangaroo_care,
drop column if exists other_danger_sign,
add column was_premature boolean,
add column referral_reason character varying(100),
add column referral_transport character varying(100),
add column referral_place bigint,
add column name character varying(100),
add column breast_crawl boolean,
add column kangaroo_care boolean,
add column other_danger_sign character varying(100)