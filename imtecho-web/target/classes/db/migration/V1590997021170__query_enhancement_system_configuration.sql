-- Manage System Configuration

DELETE FROM QUERY_MASTER WHERE CODE='retrieve_system_configuration_by_key';

INSERT INTO public.QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state ) 
VALUES ( 
'91c31904-5a17-4eab-bcea-1be08c18929e', 75398,  current_date , 75398,  current_date , 'retrieve_system_configuration_by_key', 
'key', 
'SELECT
    system_key as "key",
    is_active as "isActive",
    key_value as "value"
    FROM public.system_configuration
    WHERE system_key = #key#;', 
'Retrieve System Configuration Details By Key', 
true, 'ACTIVE');

DELETE FROM QUERY_MASTER WHERE CODE='system_config_update';

INSERT INTO public.QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state ) 
VALUES ( 
'acb7af62-94cf-40fd-b82a-e7cd177ef4a1', 75398,  current_date , 75398,  current_date , 'system_config_update', 
'oldKey,isActive,value,key', 
'update system_configuration set key_value = #value# , system_key = #key#, is_active = #isActive# where system_configuration.system_key = #oldKey#', 
'update system configuration', 
false, 'ACTIVE');


-- Query Builder

DELETE FROM QUERY_MASTER WHERE CODE='retrieve_logged_actions_by_table_name_query_code';

INSERT INTO public.QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state ) 
VALUES ( 
'da210aba-151a-4f6d-b0fe-347e14432b74', 75398,  current_date , 75398,  current_date , 'retrieve_logged_actions_by_table_name_query_code', 
'queryCode,tableName', 
'SELECT
la.event_id as "eventId",
la.schema_name as "schemaName",
la.table_name as "tableName",
la.relid as "relId",
la.session_user_name as "sessionUserName",
la.action_tstamp_tx as "action_tstamp_tx",
la.action_tstamp_stm as "action_tstamp_stm",
la.action_tstamp_clk as "action_tstamp_clk",
la.transaction_id as "transactionId",
la.application_name as "applicationName",
la.client_port as "clientPort",
la.client_query as "clientQuery",
la.action as "action",
la.row_data -> ''id'' AS "oldId",
la.row_data -> ''code'' AS "oldCode",
la.row_data -> ''query'' AS "oldQuery",
la.row_data -> ''description'' AS "oldDescription",
la.row_data -> ''state'' AS "oldState",
la.row_data -> ''is_public'' AS "oldIsPublic",
la.row_data -> ''params'' AS "oldParams",
la.row_data -> ''created_on'' AS "oldCreatedOn",
la.row_data -> ''modified_on'' AS "oldModifiedOn",
la.row_data -> ''returns_result_set'' AS "oldReturnsResultSet",
la.changed_fields -> ''id'' AS "newId",
la.changed_fields -> ''code'' AS "newCode",
la.changed_fields -> ''query'' AS "newQuery",
la.changed_fields -> ''description'' AS "newDescription",
la.changed_fields -> ''state'' AS "newState",
la.changed_fields -> ''is_public'' AS "newIsPublic",
la.changed_fields -> ''params'' AS "newParams",
la.changed_fields -> ''created_on'' AS "newCreatedOn",
la.changed_fields -> ''modified_on'' AS "newModifiedOn",
la.changed_fields -> ''returns_result_set'' AS "newReturnsResultSet",
la.statement_only as "statementOnly"
FROM logged_actions la WHERE la.table_name = #tableName# AND 
la.row_data -> ''code'' = #queryCode# order by la.event_id desc', 
'This query is used to retrieve the history of query code with all changes', 
true, 'ACTIVE');


-- Query Management Tool

DELETE FROM QUERY_MASTER WHERE CODE='retrieve_limited_query_history';

INSERT INTO public.QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state ) 
VALUES ( 
'5ab98a63-ec6a-4f6c-8318-30b43dc06938', 75398,  current_date , 75398,  current_date , 'retrieve_limited_query_history', 
'limit,searchKey,userId', 
'SELECT * from query_history where user_id = #userId# AND  ( #searchKey# = null OR query ILIKE CONCAT(''%'', #searchKey#, ''%'' )) order by id desc limit #limit#', 
'Fire postgres query to get limited records from query_history table for given user', 
true, 'ACTIVE');