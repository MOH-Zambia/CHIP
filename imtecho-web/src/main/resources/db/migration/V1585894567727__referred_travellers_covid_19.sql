DELETE FROM QUERY_MASTER WHERE CODE='opd_search_referred_travellers_covid_19_by_location_id';

INSERT INTO public.QUERY_MASTER ( created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
1,  current_date , 1,  current_date , 'opd_search_referred_travellers_covid_19_by_location_id',
'offSet,locationId,limit',
'
    with maxScreeningId as (
        select
        max(ctsi.id) as id,
        ctsi.covid_info_id as "covidInfoId"
        from covid_travellers_info cti
        inner join covid_travellers_screening_info ctsi on ctsi.covid_info_id = cti.id
        where ctsi.any_symptoms and ctsi.referral_required
        and cti.location_id in (select child_id from location_hierchy_closer_det where parent_id = #locationId#)
        group by covid_info_id
    )
    select
    cti.id as "travellersInfoId",
    ctsi.id as "travellersScreeningInfoId",
    cti.name,
    cti.address,
    cti.pincode,
    cti.location_id as "locationId",
    get_location_hierarchy(cti.location_id) as "locationHierarchy",
    cti.mobile_number as "mobileNumber",
    ctsi.symptoms,
    ctsi.other_symptoms as "otherSymptoms",
    imt_member.id as "memberId",
    imt_member.unique_health_id as "uniqueHealthId",
    imt_member.family_id as "familyId",
    imt_member.dob as "dob",
    cast(age(imt_member.dob) as text) as "age",
    concat(uu1.first_name, '' '', uu1.middle_name, '' '', uu1.last_name) as "ashaName",
    uu1.contact_number as "ashaContactNumber",
    uu1.techo_phone_number as "ashaTechoContactNumber",
    concat(uu2.first_name, '' '', uu2.middle_name, '' '', uu2.last_name) as "anmName",
    uu2.contact_number as "anmContactNumber",
    uu2.techo_phone_number as "anmTechoContactNumber"
    from covid_travellers_info cti
    inner join covid_travellers_screening_info ctsi on ctsi.id = (select id from maxScreeningId msi where msi."covidInfoId" = cti.id)
    left join imt_member on imt_member.id = cti.member_id
    left join imt_family on imt_member.family_id = imt_family.family_id
    left join um_user_location uul1 on uul1.loc_id = imt_family.area_id and uul1.state = ''ACTIVE'' and (select uu.role_id from um_user uu where uu.id = uul1.user_id and state = ''ACTIVE'') = 24
    left join um_user uu1 on uu1.id = uul1.user_id
    left join um_user_location uul2 on uul2.loc_id = imt_family.location_id and uul2.state = ''ACTIVE'' and (select uu.role_id from um_user uu where uu.id = uul2.user_id and state = ''ACTIVE'') = 30
    left join um_user uu2 on uu2.id = uul2.user_id
    left join rch_opd_member_registration romr on ctsi.id = romr.reference_id and romr.reference_type = ''COVID_TRAVELLERS_SCREENING''
    where romr.id is null
    and cti.location_id in (select child_id from location_hierchy_closer_det where parent_id = #locationId#)
    limit #limit# offset #offSet#;
',
'OPD Search IDSP Referred Patients By Location ID',
true, 'ACTIVE');