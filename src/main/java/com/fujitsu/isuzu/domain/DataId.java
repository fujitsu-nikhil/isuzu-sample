package com.fujitsu.isuzu.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DataId.
 */
@Entity
@Table(name = "data_id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "data_id", unique = true)
    private String dataId;

    @Column(name = "data_name")
    private String dataName;

    @Column(name = "sort_number")
    private String sortNumber;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public DataId dataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public DataId dataName(String dataName) {
        this.dataName = dataName;
        return this;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public DataId sortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
        return this;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public DataId updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataId)) {
            return false;
        }
        return id != null && id.equals(((DataId) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DataId{" +
            "id=" + getId() +
            ", dataId='" + getDataId() + "'" +
            ", dataName='" + getDataName() + "'" +
            ", sortNumber='" + getSortNumber() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
