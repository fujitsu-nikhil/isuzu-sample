package com.fujitsu.isuzu.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "vehicle_id_number", nullable = false, unique = true)
    private String vehicleIdNumber;

    @Column(name = "overall_judgment")
    private String overallJudgment;

    @Column(name = "overall_judgment_at")
    private Instant overallJudgmentAt;

    @Column(name = "model_year")
    private Instant modelYear;

    @Column(name = "model_code")
    private String modelCode;

    @Column(name = "lot_number")
    private String lotNumber;

    @Column(name = "unit_number")
    private String unitNumber;

    @Column(name = "estimated_production_date")
    private LocalDate estimatedProductionDate;

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

    public Vehicle vehicleIdNumber(String vehicleIdNumber) {
        this.vehicleIdNumber = vehicleIdNumber;
        return this;
    }

    public void setVehicleIdNumber(String vehicleIdNumber) {
        this.vehicleIdNumber = vehicleIdNumber;
    }

    public String getOverallJudgment() {
        return overallJudgment;
    }

    public Vehicle overallJudgment(String overallJudgment) {
        this.overallJudgment = overallJudgment;
        return this;
    }

    public void setOverallJudgment(String overallJudgment) {
        this.overallJudgment = overallJudgment;
    }

    public Instant getOverallJudgmentAt() {
        return overallJudgmentAt;
    }

    public Vehicle overallJudgmentAt(Instant overallJudgmentAt) {
        this.overallJudgmentAt = overallJudgmentAt;
        return this;
    }

    public void setOverallJudgmentAt(Instant overallJudgmentAt) {
        this.overallJudgmentAt = overallJudgmentAt;
    }

    public Instant getModelYear() {
        return modelYear;
    }

    public Vehicle modelYear(Instant modelYear) {
        this.modelYear = modelYear;
        return this;
    }

    public void setModelYear(Instant modelYear) {
        this.modelYear = modelYear;
    }

    public String getModelCode() {
        return modelCode;
    }

    public Vehicle modelCode(String modelCode) {
        this.modelCode = modelCode;
        return this;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public Vehicle lotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
        return this;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public Vehicle unitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
        return this;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public LocalDate getEstimatedProductionDate() {
        return estimatedProductionDate;
    }

    public Vehicle estimatedProductionDate(LocalDate estimatedProductionDate) {
        this.estimatedProductionDate = estimatedProductionDate;
        return this;
    }

    public void setEstimatedProductionDate(LocalDate estimatedProductionDate) {
        this.estimatedProductionDate = estimatedProductionDate;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Vehicle updatedAt(Instant updatedAt) {
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
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
            "id=" + getId() +
            ", vehicleIdNumber='" + getVehicleIdNumber() + "'" +
            ", overallJudgment='" + getOverallJudgment() + "'" +
            ", overallJudgmentAt='" + getOverallJudgmentAt() + "'" +
            ", modelYear='" + getModelYear() + "'" +
            ", modelCode='" + getModelCode() + "'" +
            ", lotNumber='" + getLotNumber() + "'" +
            ", unitNumber='" + getUnitNumber() + "'" +
            ", estimatedProductionDate='" + getEstimatedProductionDate() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
