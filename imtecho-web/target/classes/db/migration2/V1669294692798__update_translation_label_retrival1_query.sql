DELETE FROM QUERY_MASTER WHERE CODE='translation_label_retrival_1';

INSERT INTO QUERY_MASTER (uuid, created_by, created_on, modified_by, modified_on, code, params, query, description, returns_result_set, state )
VALUES (
'ce779c6c-4927-4103-b694-c403149360cb', 97070,  current_date , 97070,  current_date , 'translation_label_retrival_1',
'searchText,offset,appName,limit,startsWith',
'select
	labelMaster1.key as key,
	labelMaster1.language as language ,
	labelMaster2.text as label ,
	labelMaster1.text ,
	case
		when labelMaster1.language = ''EN'' then ''English''
		else ''Gujarati''
	end as languageToDisplay,
	labelMaster1.translation_pending as "isTranslationPending",
	labelMaster1.app_name as "appName"
from
	internationalization_label_master as labelMaster1
join internationalization_label_master as labelMaster2 on
	labelMaster2.key = labelMaster1.key
where
         (#startsWith# = null
	or labelMaster2.text ilike concat(#startsWith# , ''%'' ))
	and (#searchText# = null
	or labelMaster2.text ilike concat( ''%'',#searchText# , ''%'' ))
      and (#appName# is null
        or labelMaster2.app_name = #appName#)
order by
	labelMaster1.key
limit #limit# offset #offset#',
null,
true, 'ACTIVE');