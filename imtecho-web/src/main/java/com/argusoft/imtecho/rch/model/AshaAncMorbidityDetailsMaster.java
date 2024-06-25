package com.argusoft.imtecho.rch.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * <p>
 *     Define rch_asha_anc_morbidity_details entity and its fields.
 * </p>
 * @author kunjan
 * @since 26/08/20 11:00 AM
 *
 */
@Entity
@Table(name = "rch_asha_anc_morbidity_details")
public class AshaAncMorbidityDetailsMaster implements Serializable {

    @EmbeddedId
    private MorbidityDetailsPKey morbidityDetailsPKey;
    @Column(name = "status", length = 1)
    private String status;
    @Column(name = "symptoms")
    private String symptoms;

    public AshaAncMorbidityDetailsMaster() {
    }

    public AshaAncMorbidityDetailsMaster(MorbidityDetailsPKey morbidityDetailsPKey, String status, String symptoms) {
        this.morbidityDetailsPKey = morbidityDetailsPKey;
        this.status = status;
        this.symptoms = symptoms;
    }

    public MorbidityDetailsPKey getMorbidityDetailsPKey() {
        return morbidityDetailsPKey;
    }

    public void setMorbidityDetailsPKey(MorbidityDetailsPKey morbidityDetailsPKey) {
        this.morbidityDetailsPKey = morbidityDetailsPKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
}