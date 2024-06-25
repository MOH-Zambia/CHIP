package com.argusoft.imtecho.systemconstraint.model;

import com.argusoft.imtecho.common.model.EntityAuditInfo;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "system_constraint_field_value_master")
public class SystemConstraintFieldValueMaster extends EntityAuditInfo implements Serializable {

    @Id
    @Column(name = "uuid")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID uuid;

    @Column(name = "field_master_uuid")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID fieldMasterUuid;

    @Column(name = "value_type")
    private String valueType;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

    @Column(name = "default_value")
    private String defaultValue;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getFieldMasterUuid() {
        return fieldMasterUuid;
    }

    public void setFieldMasterUuid(UUID fieldMasterUuid) {
        this.fieldMasterUuid = fieldMasterUuid;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static class Fields {
        public static final String UUID = "uuid";
        public static final String FIELD_MASTER_UUID = "fieldMasterUuid";
        public static final String VALUE_TYPE = "valueType";
        public static final String KEY = "key";
        public static final String VALUE = "value";
        public static final String DEFAULT_VALUE = "defaultValue";
    }
}
