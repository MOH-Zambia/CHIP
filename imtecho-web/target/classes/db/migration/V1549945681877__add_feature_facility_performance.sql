--insert into menu_config(active,menu_name,navigation_state,menu_type,group_id) values(true,'Facility Performance','techo.manage.facilityPerformance','manage',(select id from menu_group where group_name='Facility Data Entry'));