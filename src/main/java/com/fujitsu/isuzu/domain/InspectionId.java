package com.fujitsu.isuzu.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A InspectionId.
 */
@Entity
@Table(name = "inspection_id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InspectionId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "inspection_id", unique = true)
    private String inspectionId;

    @Column(name = "inspection_flag")
    private String inspectionFlag;

    @Column(name = "inspection_name")
    private String inspectionName;

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

    public String getInspectionId() {
        return inspectionId;
    }

    public InspectionId inspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
        return this;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getInspectionFlag() {
        return inspectionFlag;
    }

    public InspectionId inspectionFlag(String inspectionFlag) {
        this.inspectionFlag = inspectionFlag;
        return this;
    }

    public void setInspectionFlag(String inspectionFlag) {
        this.inspectionFlag = inspectionFlag;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public InspectionId inspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
        return this;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public InspectionId sortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
        return this;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public InspectionId updatedAt(Instant updatedAt) {
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
        if (!(o instanceof InspectionId)) {
            return false;
        }
        return id != null && id.equals(((InspectionId) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InspectionId{" +
            "id=" + getId() +
            ", inspectionId='" + getInspectionId() + "'" +
            ", inspectionFlag='" + getInspectionFlag() + "'" +
            ", inspectionName='" + getInspectionName() + "'" +
            ", sortNumber='" + getSortNumber() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
