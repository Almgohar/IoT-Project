/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guc.iot.iotbackend.rest.server.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author eslam
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long organization;
    private String name;
    private String description;
    private String location;
    private Date creationDate;
    private Date expirationDate;
    private Boolean hasBooth;
    private String imgURL;
    
    public Event() {
    }
    
    public Event(Long id, Long organization, String name, String description, String location, Date creationDate, Date expirationDate, Boolean hasBooth,
            String imgURL) {
        this.id = id;
        this.organization = organization;
        this.name = name;
        this.description = description;
        this.location = location;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.hasBooth = hasBooth;
        this.imgURL = imgURL;
    }
    
    public Long getOrganization() {
        return organization;
    }
//    @TODO: Change String type for organization to Organization;
    public void setOrganization(Long organization) {
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getHasBooth() {
        return hasBooth;
    }

    public void setHasBooth(Boolean hasBooth) {
        this.hasBooth = hasBooth;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.guc.iot.iotbackend.rest.server.Event[ id=" + id + " ]";
    }
    
}
