DELETE FROM QUERY_MASTER WHERE CODE='get_soh_maintenance_mode_1';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'b7169e7b-91f7-4bc5-a258-7d97b3b1712f', 74909,  current_date , 74909,  current_date , 'get_soh_maintenance_mode_1',
'systemKey',
'select
	is_active,
	key_value
from
	system_configuration
where
	system_key = #systemKey#',
null,
true, 'ACTIVE');

DELETE FROM QUERY_MASTER WHERE CODE='insert_um_user_app_access_details_1';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'ef3ec7d6-4490-4755-a7a5-08661d102326', 74909,  current_date , 74909,  current_date , 'insert_um_user_app_access_details_1',
'deviceType,appVersion,appName,imei,userId',
'insert
	into
		um_user_app_access_details( user_id, app_name, app_version, device_type, created_on, imei_number )
	values(#userId#, #appName#, #appVersion#, #deviceType#, now(),
(case when #imei# = null then null else  #imei#  end)
)',
null,
false, 'ACTIVE');

DELETE FROM QUERY_MASTER WHERE CODE='state_of_health_get_timeline_data_1';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'227e1a28-4966-40ce-84fb-0bb3069b247d', 74909,  current_date , 74909,  current_date , 'state_of_health_get_timeline_data_1',
'locationId',
'select to_char(cast (timeline_type as date),''Mon,YYYY'') as timeline,*
from soh_timeline_analytics_temp where
timeline_sub_type in (''DATE'' ,''DAY'') and location_id=#locationId#',
null,
true, 'ACTIVE');

DELETE FROM QUERY_MASTER WHERE CODE='state_of_health_data_retrieval_15';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'70cdd8fa-0cda-479c-81f4-417826170a97', 74909,  current_date , 74909,  current_date , 'state_of_health_data_retrieval_15',
'locationId,element_name,timeline_type',
'with next_active_depth as (
select
	closer.depth as "depth"
from
	location_type_master lt
inner join location_hierchy_closer_det closer on
	lt.type = closer.child_loc_type where closer.parent_id = #locationId# and lt.is_soh_enable = true  and depth != 0 order by closer."depth" limit 1
),
elements_details as (
       select lt.level,lt.type,closer.child_id as "locationId",
        case when lt.type =''C'' or lt.type =''D'' then ''Districts/Corporation'' else lt.name end as "locationType",
        case when l.english_name is not null then l.english_name else l.name end as english_name,
 cast(#element_name# as  text) as "elementName",
        value as value,sh.target,male as male,female as female,
        chart1 as chart1,chart2 as chart2,chart3 as chart3,chart4 as chart4,
chart5 as chart5,
chart6 as chart6,
chart7 as chart7,
chart8 as chart8,
chart9 as chart9,
chart10 as chart10,
chart11 as chart11,
chart12 as chart12,
chart13 as chart13,
chart14 as chart14,
chart15 as chart15,

        cast(#timeline_type# as text) as timeline_type,
        days as days,
        reporting as reporting,
        calculatedTarget as calculatedTarget,
        color as color,
        percentage as percentage,
        sortPriority as "sortPriority",
        displayValue as displayValue
        from location_hierchy_closer_det closer
        inner join location_master l on closer.child_id = l.id
        inner join location_type_master lt on l.type = lt.type
        left join soh_element_configuration config on config.element_name = #element_name#
        left join soh_timeline_analytics_temp sh on sh.location_id = closer.child_id and config.element_name = sh.element_name
	and timeline_type = #timeline_type#
        where closer.parent_id = #locationId# and depth=(SELECT * FROM next_active_depth)
        order by english_name),
unique_locations as (
        select distinct "locationId" from elements_details
        ),
user_details_ids as (
        select uul.loc_id as location_id, concat(uu.first_name,'' '',uu.middle_name,'' '', uu.last_name)
				 as "userName",
				 uu.role_id,
uu.contact_number as "contactNumber",
uu.id as user_id,
role.short_name as "roleName"

        from unique_locations elements
        inner join um_user_location uul on elements."locationId" = uul.loc_id
        inner join location_master lm on lm.id =  uul.loc_id
        inner join soh_location_type_role_mapping as t on t.location_type = lm."type"
        inner join um_user uu on uu.id = uul.user_id
        inner join um_role_master role on role.id= t.role_id

        where uul.state=''ACTIVE''
        and uu.state=''ACTIVE''
         and uu.role_id=role.id
),
remaining_user_data as (
select "locationId" as location_id,
cast(''-'' as text) as "userName",
t.role_id,
cast(''-'' as text) as "contactNumber",
cast(0 as numeric) as "user_id",
role.short_name as "roleName"
from unique_locations
inner join location_master lm on lm.id = "locationId"
inner join soh_location_type_role_mapping as t on t.location_type = lm."type"
inner join um_role_master role on role.id= t.role_id
),
remaining_user_data_1 as (
select users.*
from remaining_user_data remains
left join user_details_ids users  on users.location_id = remains.location_id
and users.role_id  = remains.role_id
where users.user_id is not null
union
select remains.*
from remaining_user_data remains
left join user_details_ids users  on users.location_id = remains.location_id
and users.role_id  = remains.role_id
where users.user_id is null
),
user_details as (
      select
      cast(array_to_json(array_agg(row_to_json(ud.*))) as text) as user_name,ud.location_id
      from remaining_user_data_1 ud
      group by ud.location_id
)
select elements.*,
case when users.user_name is null then null else users.user_name end as  "userDetail"
from elements_details elements
left join user_details users on elements."locationId" = users.location_id',
null,
true, 'ACTIVE');

DELETE FROM QUERY_MASTER WHERE CODE='get_elements_details_permission_wise_3';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'1824ebcb-eb65-425e-9cd6-ff7ea5e126d1', 74909,  current_date , 74909,  current_date , 'get_elements_details_permission_wise_3',
'user_id,locationId,timeline_type',
'with user_detail as(
select role_id,id from um_user where id = #user_id#
),
elements_id as ( select soh_element_permissions.element_id from soh_element_permissions inner join user_detail on true
where  (case when permission_type = ''ROLE'' then reference_id = user_detail.role_id end) or
(case when permission_type = ''USER'' then reference_id = user_detail.id end) or
permission_type = ''ALL''),
soh_elements as (select * from soh_element_configuration where id in (select distinct(element_id) from elements_id) or is_public = true),
elements_details as (
       select lt.level,lt.type,closer.child_id as "locationId",
        case when lt.type =''C'' or lt.type =''D'' then ''Districts/Corporation'' else lt.name end as "locationType",
        case when l.english_name is not null then l.english_name else l.name end as english_name,
config.element_name as "elementName",
        value as value,sh.target,male as male,female as female,
        chart1 as chart1,chart2 as chart2,chart3 as chart3,chart4 as chart4,
chart5 as chart5,
chart6 as chart6,
chart7 as chart7,
chart8 as chart8,
chart9 as chart9,
chart10 as chart10,
chart11 as chart11,
chart12 as chart12,
chart13 as chart13,
chart14 as chart14,
chart15 as chart15,
        cast(#timeline_type# as text) as timeline_type,
        days as days,
        reporting as reporting,
        calculatedTarget as calculatedTarget,
        color as color,
        percentage as percentage,
        sortPriority as "sortPriority",
        displayValue as displayValue
        from location_hierchy_closer_det closer
        inner join location_master l on closer.child_id = l.id
        inner join location_type_master lt on l.type = lt.type
        left join soh_elements config on TRUE
        left join soh_timeline_analytics_temp sh on sh.location_id = closer.parent_id and config.element_name = sh.element_name
	and timeline_type = #timeline_type#
        where closer.parent_id = #locationId# and depth=0
        order by english_name),
unique_locations as (
        select distinct "locationId" from elements_details
        ),
user_details_ids as (
        select uul.loc_id as location_id, concat(uu.first_name,'' '',uu.middle_name,'' '', uu.last_name)
				 as "userName",
				 uu.role_id,
uu.contact_number as "contactNumber",
uu.id as user_id,
role.short_name as "roleName"

        from unique_locations elements
        inner join um_user_location uul on elements."locationId" = uul.loc_id
        inner join location_master lm on lm.id =  uul.loc_id
        inner join soh_location_type_role_mapping as t on t.location_type = lm."type"
        inner join um_user uu on uu.id = uul.user_id
        inner join um_role_master role on role.id= t.role_id

        where uul.state=''ACTIVE''
        and uu.state=''ACTIVE''
        and uu.role_id=role.id
),
remaining_user_data as (
select "locationId" as location_id,
cast(''-'' as text) as "userName",
t.role_id,
cast(''-'' as text) as "contactNumber",
cast(0 as numeric) as "user_id",
role.short_name as "roleName"
from unique_locations
inner join location_master lm on lm.id = "locationId"
inner join soh_location_type_role_mapping as t on t.location_type = lm."type"
inner join um_role_master role on role.id= t.role_id
),
remaining_user_data_1 as (
select users.*
from remaining_user_data remains
left join user_details_ids users  on users.location_id = remains.location_id
and users.role_id  = remains.role_id
where users.user_id is not null
union
select remains.*
from remaining_user_data remains
left join user_details_ids users  on users.location_id = remains.location_id
and users.role_id  = remains.role_id
where users.user_id is null
),
user_details as (
      select
      cast(array_to_json(array_agg(row_to_json(ud.*))) as text) as user_name,ud.location_id
      from remaining_user_data_1 ud
      group by ud.location_id
)
select elements.*,
case when users.user_name is null then null else users.user_name end as  "userDetail"
from elements_details elements
left join user_details users on elements."locationId" = users.location_id',
null,
true, 'ACTIVE');