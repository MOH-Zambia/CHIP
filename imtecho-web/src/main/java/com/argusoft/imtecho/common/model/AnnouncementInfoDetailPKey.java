/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.imtecho.common.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *<p>Defines fields related to user</p>
 * @author smeet
 * @since 26/08/2020 5:30
 */
@Embeddable
public class AnnouncementInfoDetailPKey implements Serializable{

    @Basic(optional = false)
    @Column(name = "announcement")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "language")
    private String language;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AnnouncementInfoDetailPKey{" + "id=" + id + ", language=" + language + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnouncementInfoDetailPKey that = (AnnouncementInfoDetailPKey) o;
        return Objects.equals(id, that.id) && Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language);
    }
}