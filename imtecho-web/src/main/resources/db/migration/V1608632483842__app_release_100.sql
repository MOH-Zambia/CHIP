update system_configuration set key_value = '100-98' where system_key = 'ANDROID_VERSION';

delete from app_version_response;

insert into mobile_version_mapping(apk_version, text_version) values(100, '4.0.13');
