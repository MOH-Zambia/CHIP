drop table if exists soh_element_configuration;
create table soh_element_configuration (
    id serial primary key,
    element_name varchar(255),
    element_display_short_name varchar(255),
    element_display_name varchar(255),
    element_display_name_postfix varchar(255),
    upper_bound numeric,
    lower_bound numeric,
    upper_bound_for_rural numeric,
    lower_bound_for_rural numeric,
    is_small_value_positive boolean,
    field_name varchar(255),
    module varchar(255),
    target numeric,
    target_for_rural numeric,
    target_for_urban numeric,
    target_mid numeric,
    target_mid_enable boolean,
    tabs_json text,
    is_public boolean,
    element_order varchar(255),
    file_id integer,
    created_by integer not null,
    created_on timestamp without time zone not null,
    modified_by integer not null,
    modified_on timestamp without time zone not null
);

-- insert soh elements

INSERT INTO public.soh_element_configuration (element_name,element_display_short_name,element_display_name,upper_bound,lower_bound,upper_bound_for_rural,lower_bound_for_rural,is_small_value_positive,field_name,"module",target,target_for_rural,target_for_urban,target_mid,created_by,created_on,modified_on,modified_by,is_public,element_display_name_postfix,target_mid_enable,tabs_json,"element_order") VALUES 
('VERIFICATION_SERVICE','Data Quality','Data Quality',75,100,NULL,NULL,true,'percentage','RCH',98,NULL,75,95,1,'2020-01-24 11:15:00.430','2020-01-31 15:44:56.736',66522,NULL,'%',true,'[]',NULL)
,('LBW','LBW','LBW',15,12,NULL,NULL,true,'chart4','RCH',13.5,NULL,NULL,12.15,1,'2020-01-24 11:08:53.719','2020-01-31 15:13:02.018',80210,true,NULL,true,'[{"elementLineListLable":"","sortField":"chart4","colorField":"LBW","lineList":{"level":"8","field":"chart4","queryName":"state_of_health_get_lbw_detail_3"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[{"field":"chart2","color":"#d62728","name":"Very LBW","$$hashKey":"object:861"},{"field":"chart1","color":"#ff7f0e","name":"Moderate","$$hashKey":"object:869"},{"field":"chart3","color":"#2ca02c","name":"Normal","$$hashKey":"object:877"}]},"fields":[{"name":"Total Birth","shortName":"Total","fieldName":"value","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating total number of babies weighed during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:892","fieldLevel":"8","isFieldPublic":false},{"name":"LBW","shortName":"LBW","fieldName":"chart4","isDBField":true,"value":"","isPublic":null,"help":"This will show very LBW and moderately LBW values of live births in selected time period","isPrimary":true,"isSecondary":false,"$$hashKey":"object:914","fieldLevel":"","isFieldPublic":true},{"name":"Very LBW","shortName":"Very LBW","fieldName":"chart2","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:936","fieldLevel":"8","isFieldPublic":false},{"name":"Moderate","shortName":"Moderate","fieldName":"chart1","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:958","fieldLevel":"8","isFieldPublic":false},{"name":"Normal","shortName":"Normal","fieldName":"chart3","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:980","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:825","tabName":"LBW","tabElementName":"LBW","tabFieldName":"chart4","lineListLabel":"LBW","isMapEnable":true,"isPieChartEnable":true,"isLineListEnable":true}]',NULL)
,('FI','Fully Immunized','Fully Immunized',55,45,NULL,NULL,false,'percentage','RCH',98,NULL,NULL,72,1,'2020-01-24 11:11:48.046','2020-01-31 16:26:56.027',80210,true,'%',false,'[{"tabName":"Fully Immunized","elementName":"","elementDisplayNamePostfix":"","elementLineListLable":"","fieldName":"","sortField":"value","colorField":"FI","lineList":{"level":""},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Fully Immunized (A)","shortName":"FI","fieldName":"value","isDBField":true,"value":"","isPublic":null,"help":"This will show total number of fully immunized children during selected time period","isPrimary":true,"isSecondary":true,"level":"","$$hashKey":"object:1081","fieldLevel":"8","isFieldPublic":true},{"name":"Non-fully Immunized (B)","shortName":"Non-FI","fieldName":"chart2","isDBField":true,"value":"","isPublic":null,"help":"This will show total number of partially or non-fully immunized children during selected time period","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:1103","fieldLevel":"8","isFieldPublic":false},{"name":"Total (C)","shortName":"Total","fieldName":"chart1","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:1125","fieldLevel":"8","isFieldPublic":false},{"name":"% Fully Immunized against total ((A * 100) / C)","shortName":"%","fieldName":"percentage","isDBField":true,"value":"","isPublic":null,"help":"This will be percentage of fully immunized children","isPrimary":true,"isSecondary":true,"level":"","$$hashKey":"object:1147","fieldLevel":"4","isFieldPublic":false}],"$$hashKey":"object:1035","lineListLabel":"Non Fully Immunized","tabElementName":"FI","tabElementDisplayNamePostfix":"%","tabFieldName":"percentage","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":false},{"tabName":"Immu. Overdue","elementName":"","elementDisplayNamePostfix":"","elementLineListLable":"","fieldName":"","sortField":"chart4","colorField":"","lineList":{"level":"8","field":"chart4","queryName":"state_of_health_immunized_non_fi_detail_2"},"help":{"text":""},"map":{},"isPublic":false,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Immu. Overdue","shortName":"Immu. Overdue","fieldName":"chart4","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating immunization due of children less than 2 years age","isPrimary":true,"isSecondary":false,"level":"","$$hashKey":"object:1397","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:1367","isMapEnable":false,"isPieChartEnable":false,"tabFieldName":"chart4","lineListLabel":"Immu. Overdue","tabElementName":"FI","isLineListEnable":true}]',NULL)
,('VERIFICATION_SERVICE','Data Quality','Data Quality',100,85,NULL,NULL,false,'percentage','RCH',100,NULL,NULL,95,80210,'2020-01-31 14:22:48.732','2020-01-31 14:22:48.732',80210,NULL,'%',true,'[{"elementLineListLable":"","sortField":"percentage","colorField":"Total","lineList":{"level":"8","field":"value","queryName":"state_of_health_service_verification_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":false,"pieChart":{"isEnable":null,"fields":[{"field":"","color":"","name":"","$$hashKey":"object:474"}]},"fields":[{"name":"Total Verified service (A)","shortName":"Total","fieldName":"value","isDBField":true,"isPublic":null,"help":"Total indicates, total number of service verified","isPrimary":true,"isSecondary":true,"$$hashKey":"object:489","fieldLevel":"8","isFieldPublic":false},{"name":"False verified (B)","shortName":"False Veri.","fieldName":"chart2","isDBField":true,"isPublic":null,"help":"False indicates, count of potentially wrong information entered by user as verified by call center","isPrimary":true,"isSecondary":true,"$$hashKey":"object:509","fieldLevel":"8","isFieldPublic":false},{"name":"True verified (C)","shortName":"True Veri.","fieldName":"chart1","isDBField":true,"isPublic":null,"help":"","isPrimary":true,"isSecondary":true,"$$hashKey":"object:529","fieldLevel":"8","isFieldPublic":false},{"name":"% of True verified service against total service ((C * 100)/A)","shortName":"%","fieldName":"percentage","isDBField":true,"isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:549","fieldLevel":"4","isFieldPublic":false}],"$$hashKey":"object:429","tabName":"Data Quality","tabElementName":"VERIFICATION_SERVICE","tabElementDisplayNamePostfix":"%","tabFieldName":"percentage","lineListLabel":"Data Quality","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('PREG_REG','Early Pregnancy Reg.','Early Pregnancy Reg.',85,70,NULL,NULL,false,'percentage','RCH',80,NULL,NULL,75,80210,'2020-01-31 14:35:54.143','2020-01-31 14:35:54.143',80210,NULL,'%',true,'[{"elementLineListLable":"","sortField":"percentage","colorField":"%","lineList":{"level":"8","field":"chart1","queryName":"state_of_health_preg_reg_detail_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":false,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Reg.(A)","shortName":"Reg.","fieldName":"value","isDBField":true,"isPublic":null,"help":"It''s indicating total no of pregnancy registrations during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:647","fieldLevel":"8","isFieldPublic":false},{"name":"Total Early Reg.(B)","shortName":"Early Reg.","fieldName":"chart1","isDBField":true,"isPublic":null,"help":"It''s indicating total no of early pregnancy registrations during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:667","fieldLevel":"8","isFieldPublic":false},{"name":"% of early reg. against Total Reg ((B * 100)/A)","shortName":"%","fieldName":"percentage","isDBField":true,"isPublic":null,"help":"It''s indicating % of early registrations against total registration","isPrimary":true,"isSecondary":true,"$$hashKey":"object:687","fieldLevel":"4","isFieldPublic":false}],"$$hashKey":"object:605","tabName":"Early Pregnancy Reg.","tabElementName":"PREG_REG","tabElementDisplayNamePostfix":"%","tabFieldName":"percentage","lineListLabel":"Early Pregnancy Reg.","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('IMR','Infant Death','Infant Death',21,17,42,34,true,'value','RCH',20,20,20,15,1,'2020-01-24 10:21:02.641','2020-01-31 15:29:17.202',80210,NULL,NULL,true,'[{"elementLineListLable":"","sortField":"percentage","colorField":"%","lineList":{"level":"8","field":"value","queryName":"state_of_health_get_imr_detail_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Birth (A)","shortName":"Birth","fieldName":"chart1","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating total number of live birth during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1380","fieldLevel":"8","fieldValue":"","isFieldPublic":false},{"name":"Total Death (B)","shortName":"Death","fieldName":"value","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating total number of child death during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1402","fieldLevel":"8","isFieldPublic":true},{"name":"Estimation (C)","shortName":"Estimation","fieldName":"","isDBField":false,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:1424","fieldLevel":"8","fieldValue":"targetForRural","isFieldPublic":false},{"name":"% of death against estimation ((((B * 1000)/ A) * 100) / C)","shortName":"%","fieldName":"percentage","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1446","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:1338","tabName":"Infant Death","lineListLabel":"Infant Death","tabFieldName":"value","tabElementName":"IMR","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('NCD_HYPERTENSION_CONFIRM','HTN Confirmed ','HTN Confirmed ',22,15,NULL,NULL,false,'last30days','NCD',18,NULL,NULL,18,1,'2020-01-24 11:22:47.994','2020-01-24 11:22:47.994',1,NULL,NULL,NULL,NULL,NULL)
,('NCD_DIABETES_CONFIRM','DM Confirmed ','DM Confirmed ',13,7,NULL,NULL,false,'last30days','NCD',8.7,NULL,NULL,8.7,1,'2020-01-24 11:21:19.993','2020-01-24 11:21:19.993',1,NULL,NULL,NULL,NULL,NULL)
,('NCD_DIABETES','DM Screening ','DM Screening ',12,7,NULL,NULL,false,NULL,'NCD',8,NULL,NULL,7.2,1,'2020-01-24 11:19:54.270','2020-01-24 11:19:54.270',1,NULL,NULL,NULL,NULL,NULL)
,('NCD_HYP_DIABETES','HTN & DM 1','HTN & DM ',10,7,NULL,NULL,false,NULL,'NCD',NULL,NULL,NULL,66.42,1,'2020-01-24 11:16:32.329','2020-01-24 11:16:32.329',1,NULL,NULL,NULL,NULL,NULL)
;
INSERT INTO public.soh_element_configuration (element_name,element_display_short_name,element_display_name,upper_bound,lower_bound,upper_bound_for_rural,lower_bound_for_rural,is_small_value_positive,field_name,"module",target,target_for_rural,target_for_urban,target_mid,created_by,created_on,modified_on,modified_by,is_public,element_display_name_postfix,target_mid_enable,tabs_json,"element_order") VALUES 
('PREG_REG','Early Pregnancy Reg. ','Early Pregnancy Reg. ',85,70,NULL,NULL,false,NULL,'RCH',NULL,NULL,NULL,75,1,'2020-01-24 11:13:13.154','2020-01-24 11:13:13.154',1,NULL,NULL,NULL,NULL,NULL)
,('NCD_HYPERTENSION','HTN Screening ','HTN Screening ',12,7,NULL,NULL,false,'percentage','NCD',8,NULL,NULL,7.2,1,'2020-01-24 11:17:37.989','2020-01-31 18:48:54.539',80226,NULL,NULL,false,'[{"tabName":"","elementName":"","elementDisplayNamePostfix":"","elementLineListLable":"","fieldName":"","sortField":"","colorField":"","lineList":{"level":""},"help":{},"map":{},"isPublic":false,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"dfgfhfg","shortName":"eetry","fieldName":"rey","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":true,"isSecondary":false,"level":"","$$hashKey":"object:640","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:610","isLineListEnable":false,"isPieChartEnable":false,"isMapEnable":false}]',NULL)
,('SAM','SAM ','SAM ',12,8,NULL,NULL,true,NULL,'RCH',NULL,NULL,NULL,8.55,1,'2020-01-24 11:10:41.252','2020-01-24 11:10:41.252',1,true,NULL,NULL,NULL,NULL)
,('MMR','Maternal Death','Maternal Death',100,82,NULL,NULL,true,'value','RCH',60,NULL,NULL,45,1,'2020-01-24 10:30:50.696','2020-01-31 15:02:27.309',80210,true,NULL,true,'[{"elementLineListLable":"","sortField":"percentage","colorField":"%","lineList":{"level":"8","field":"value","queryName":"state_of_health_get_mmr_detail_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Birth (A)","shortName":"Birth","fieldName":"chart1","isDBField":true,"isPublic":null,"help":"It''s indicating total number of live birth during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1160","fieldLevel":"8","isFieldPublic":false},{"name":"Total Death (B):","shortName":"Death","fieldName":"value","isDBField":true,"isPublic":null,"help":"It''s indicating total number of mother death during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1180","fieldLevel":"8","isFieldPublic":true},{"name":"Estimation (C)","shortName":"Estimation","fieldName":"","isDBField":false,"isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:1200","fieldLevel":"8","isFieldPublic":false,"fieldValue":"target"},{"name":"% of death against estimation ((((B * 100000)/ A) * 100) / C)","shortName":"%","fieldName":"percentage","isDBField":true,"isPublic":null,"help":"It''s indicating % against estimated maternal deaths","isPrimary":true,"isSecondary":true,"$$hashKey":"object:1220","fieldLevel":"4","isFieldPublic":false}],"$$hashKey":"object:1118","tabName":"Maternal Death","tabElementName":"MMR","tabFieldName":"value","lineListLabel":"Maternal Death","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('SR','Sex Ratio','Sex Ratio',939,769,NULL,NULL,false,'displayvalue','RCH',943,NULL,NULL,896,1,'2020-01-24 11:05:05.259','2020-01-31 16:35:51.068',80210,true,NULL,true,'[{"tabName":"Sex Ratio","elementName":"","elementDisplayNamePostfix":"","elementLineListLable":"","fieldName":"","sortField":"percentage","colorField":"Ratio","lineList":{"level":"8","field":"value","queryName":"state_of_health_get_sex_ratio_detail_2"},"help":{"text":""},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Male (A)","shortName":"Male","fieldName":"male","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:1564","fieldLevel":"8","isFieldPublic":false},{"name":"Total Female (B)","shortName":"Female","fieldName":"female","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:1586","fieldLevel":"8","isFieldPublic":false},{"name":"Ratio ((B) * 1000)/(A)","shortName":"Ratio","fieldName":"percentage","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":true,"isSecondary":true,"level":"","$$hashKey":"object:1608","fieldLevel":"8","isFieldPublic":true}],"$$hashKey":"object:1516","tabElementName":"SR","tabFieldName":"displayvalue","lineListLabel":"Sex Ratio","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('Anemia','Severe Anemic PW','Severe Anemic PW',1.6,1.4,NULL,NULL,true,'chart1','RCH',1.5,NULL,NULL,1.35,1,'2020-01-24 11:06:36.955','2020-01-31 14:46:19.195',80210,true,NULL,true,'[{"elementLineListLable":"","sortField":"chart1","colorField":"Severe","lineList":{"level":"8","field":"chart1","queryName":"state_of_health_get_anemia_detail_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[{"field":"chart1","color":"#d62728","name":"Severe","$$hashKey":"object:914"},{"field":"chart2","color":"#ff7f0e","name":"Moderate","$$hashKey":"object:922"},{"field":"chart3","color":"#1f77b4","name":"Mild","$$hashKey":"object:930"},{"field":"chart4","color":"#2ca02c","name":"Normal","$$hashKey":"object:938"}]},"fields":[{"name":"Severe","shortName":"Severe","fieldName":"chart1","isDBField":true,"isPublic":null,"help":"The number of new cases identified as anemia in selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:953","fieldLevel":"8","isFieldPublic":true},{"name":"Moderate","shortName":"Moderate","fieldName":"chart2","isDBField":true,"isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:973","fieldLevel":"8","isFieldPublic":false},{"name":"Mild","shortName":"Mild","fieldName":"chart3","isDBField":true,"isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:993","fieldLevel":"8","isFieldPublic":false},{"name":"Normal","shortName":"Normal","fieldName":"chart4","isDBField":true,"isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"$$hashKey":"object:1013","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:878","tabName":"Severe Anemic PW","tabElementName":"Anemia","tabFieldName":"chart1","lineListLabel":"Severe Anemic PW","isMapEnable":true,"isPieChartEnable":true,"isLineListEnable":true}]',NULL)
,('ID','Hospital Delivery','Hospital Delivery',99,84,93,77,false,'percentage','RCH',99.5,NULL,NULL,99,1,'2020-01-24 10:31:06.558','2020-01-31 15:07:12.551',80210,NULL,'%',true,'[{"elementLineListLable":"","sortField":"percentage","colorField":"ID","lineList":{"level":"8","field":"percentage","queryName":"state_of_health_institute_delivery_detail_2"},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":true,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Delivery (A)","shortName":"Total","fieldName":"chart1","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating total number of delivery during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:678","fieldLevel":"8","isFieldPublic":false},{"name":"Total Institution Delivery (B)","shortName":"ID","fieldName":"value","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating total number of institution delivery during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:700","fieldLevel":"8","isFieldPublic":false},{"name":"% of ID against Total Delivery ((B * 100)/A)","shortName":"ID %","fieldName":"percentage","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating % of institution delivery during selected time period","isPrimary":true,"isSecondary":true,"$$hashKey":"object:722","fieldLevel":"4","isFieldPublic":true}],"$$hashKey":"object:632","tabName":"Hospital Delivery","tabElementName":"ID","tabElementDisplayNamePostfix":"%","tabFieldName":"percentage","lineListLabel":"Home Delivery","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":true}]',NULL)
,('AVG_SERVICE_DURATION','Real Time Data ','Real Time Data ',1,0.2,NULL,NULL,true,'percentage','RCH',1,NULL,NULL,0.9,1,'2020-01-24 11:24:21.240','2020-02-03 11:12:40.921',80226,NULL,NULL,false,'[{"tabName":"Real Time Data","elementName":"","elementDisplayNamePostfix":"","elementLineListLable":"","fieldName":"","sortField":"percentage","colorField":"AVG.","lineList":{},"help":{},"map":{"level":"2","field":"percentage"},"isPublic":false,"pieChart":{"isEnable":null,"fields":[]},"fields":[{"name":"Total Days (A)","shortName":"Total Days","fieldName":"chart1","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:644","fieldLevel":"8","isFieldPublic":false},{"name":"Total Services (B)","shortName":"Total Services","fieldName":"value","isDBField":true,"value":"","isPublic":null,"help":"","isPrimary":false,"isSecondary":true,"level":"","$$hashKey":"object:666","fieldLevel":"8","isFieldPublic":false},{"name":"Avg. service duration (A)/(B)","shortName":"AVG.","fieldName":"percentage","isDBField":true,"value":"","isPublic":null,"help":"It''s indicating number of days required to complete one service","isPrimary":true,"isSecondary":true,"level":"","$$hashKey":"object:688","fieldLevel":"8","isFieldPublic":false}],"$$hashKey":"object:603","tabElementName":"AVG_SERVICE_DURATION","lineListLabel":"Real Time Data","tabFieldName":"percentage","isMapEnable":true,"isPieChartEnable":false,"isLineListEnable":false}]',NULL)
;


drop table if exists soh_element_module_master;
create table soh_element_module_master (
    id serial primary key,
    module varchar(255),
    module_name varchar(255),
    is_public boolean,
    created_by integer not null,
    created_on timestamp without time zone not null,
    modified_by integer not null,
    modified_on timestamp without time zone not null
);

-- insert soh element modules

INSERT INTO public.soh_element_module_master ("module",module_name,is_public,created_by,created_on,modified_on,modified_by) VALUES 
('RCH','Reproductive Child Health',true,1,now(),now(),1)
,('NCD','Non-Communicable Diseases',false,1,now(),now(),1)
;