package com.fujitsu.isuzu.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A InspectionResult.
 */
@Entity
@Table(name = "inspection_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InspectionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehicle_id_number")
    private String vehicleIdNumber;

    @Column(name = "inspection_id")
    private String inspectionId;

    @Column(name = "system_id")
    private String systemId;

    @Column(name = "pattern_1")
    private String pattern1;

    @Column(name = "judgment")
    private String judgment;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "parts_number")
    private String partsNumber;

    @Column(name = "detail")
    private String detail;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleIdNumber() {
        return vehicleIdNumber;
    }

    public InspectionResult vehicleIdNumber(String vehicleIdNumber) {
        this.vehicleIdNumber = vehicleIdNumber;
        return this;
    }

    public void setVehicleIdNumber(String vehicleIdNumber) {
        this.vehicleIdNumber = vehicleIdNumber;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public InspectionResult inspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
        return this;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSystemId() {
        return systemId;
    }

    public InspectionResult systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPattern1() {
        return pattern1;
    }

    public InspectionResult pattern1(String pattern1) {
        this.pattern1 = pattern1;
        return this;
    }

    public void setPattern1(String pattern1) {
        this.pattern1 = pattern1;
    }

    public String getJudgment() {
        return judgment;
    }

    public InspectionResult judgment(String judgment) {
        this.judgment = judgment;
        return this;
    }

    public void setJudgment(String judgment) {
        this.judgment = judgment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public InspectionResult createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPartsNumber() {
        return partsNumber;
    }

    public InspectionResult partsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
        return this;
    }

    public void setPartsNumber(String partsNumber) {
        this.partsNumber = partsNumber;
    }

    public String getDetail() {
        return detail;
    }

    public InspectionResult detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public InspectionResult updatedAt(Instant updatedAt) {
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
        if (!(o instanceof InspectionResult)) {
            return false;
        }
        return id != null && id.equals(((InspectionResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InspectionResult{" +
            "id=" + getId() +
            ", vehicleIdNumber='" + getVehicleIdNumber() + "'" +
            ", inspectionId='" + getInspectionId() + "'" +
            ", systemId='" + getSystemId() + "'" +
            ", pattern1='" + getPattern1() + "'" +
            ", judgment='" + getJudgment() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", partsNumber='" + getPartsNumber() + "'" +
            ", detail='" + getDetail() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
