insert into menu_config(active,menu_name,navigation_state,menu_type,group_id) values(true,'Location Wise Expected Target','techo.manage.expectedTarget','manage',(select id from menu_group where group_name='Administration' and active=true));