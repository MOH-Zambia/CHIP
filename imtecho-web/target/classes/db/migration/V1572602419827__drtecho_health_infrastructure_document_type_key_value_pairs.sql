
INSERT INTO listvalue_field_master(field_key, field, is_active, field_type, form)
    SELECT 'drtecho_health_infrastructure_document_types', 'Dr. Techo health infrastructure documnent types', true, 'T', 'DRTECHO'
    WHERE NOT EXISTS (SELECT 1 FROM listvalue_field_master WHERE field_key='drtecho_health_infrastructure_document_types');

INSERT INTO listvalue_field_value_detail(is_active, is_archive, last_modified_by, last_modified_on, value, field_key, file_size)
    SELECT true, false, 'dhirpara', now(), 'Health Infrastructure Certificate', 'drtecho_health_infrastructure_document_types', 0
    WHERE NOT EXISTS (SELECT 1 FROM listvalue_field_value_detail WHERE field_key='drtecho_health_infrastructure_document_types' AND value='Health Infrastructure Certificate');

