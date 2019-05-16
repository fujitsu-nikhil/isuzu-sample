package com.fujitsu.isuzu.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Inspection.
 */
@Entity
@Table(name = "inspection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inspection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "model_year", length = 3, nullable = false)
    private String modelYear;

    @NotNull
    @Size(max = 5)
    @Column(name = "model_code", length = 5, nullable = false)
    private String modelCode;

    @NotNull
    @Size(max = 3)
    @Column(name = "lot_start", length = 3, nullable = false)
    private String lotStart;

    @NotNull
    @Size(max = 3)
    @Column(name = "unit_start", length = 3, nullable = false)
    private String unitStart;

    @NotNull
    @Size(max = 3)
    @Column(name = "lot_end", length = 3, nullable = false)
    private String lotEnd;

    @NotNull
    @Size(max = 3)
    @Column(name = "unit_end", length = 3, nullable = false)
    private String unitEnd;

    @NotNull
    @Column(name = "estimated_production_date_start", nullable = false)
    private LocalDate estimatedProductionDateStart;

    @NotNull
    @Column(name = "estimated_production_date_end", nullable = false)
    private LocalDate estimatedProductionDateEnd;

    @Size(max = 15)
    @Column(name = "inspection_id", length = 15)
    private String inspectionId;

    
    @Column(name = "system_id", unique = true)
    private String systemId;

    @Column(name = "pattern")
    private String pattern;

    @Size(max = 1)
    @Column(name = "pattern_division_number", length = 1)
    private String patternDivisionNumber;

    @Size(max = 1)
    @Column(name = "pattern_division_number_total", length = 1)
    private String patternDivisionNumberTotal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelYear() {
        return modelYear;
    }

    public Inspection modelYear(String modelYear) {
        this.modelYear = modelYear;
        return this;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getModelCode() {
        return modelCode;
    }

    public Inspection modelCode(String modelCode) {
        this.modelCode = modelCode;
        return this;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getLotStart() {
        return lotStart;
    }

    public Inspection lotStart(String lotStart) {
        this.lotStart = lotStart;
        return this;
    }

    public void setLotStart(String lotStart) {
        this.lotStart = lotStart;
    }

    public String getUnitStart() {
        return unitStart;
    }

    public Inspection unitStart(String unitStart) {
        this.unitStart = unitStart;
        return this;
    }

    public void setUnitStart(String unitStart) {
        this.unitStart = unitStart;
    }

    public String getLotEnd() {
        return lotEnd;
    }

    public Inspection lotEnd(String lotEnd) {
        this.lotEnd = lotEnd;
        return this;
    }

    public void setLotEnd(String lotEnd) {
        this.lotEnd = lotEnd;
    }

    public String getUnitEnd() {
        return unitEnd;
    }

    public Inspection unitEnd(String unitEnd) {
        this.unitEnd = unitEnd;
        return this;
    }

    public void setUnitEnd(String unitEnd) {
        this.unitEnd = unitEnd;
    }

    public LocalDate getEstimatedProductionDateStart() {
        return estimatedProductionDateStart;
    }

    public Inspection estimatedProductionDateStart(LocalDate estimatedProductionDateStart) {
        this.estimatedProductionDateStart = estimatedProductionDateStart;
        return this;
    }

    public void setEstimatedProductionDateStart(LocalDate estimatedProductionDateStart) {
        this.estimatedProductionDateStart = estimatedProductionDateStart;
    }

    public LocalDate getEstimatedProductionDateEnd() {
        return estimatedProductionDateEnd;
    }

    public Inspection estimatedProductionDateEnd(LocalDate estimatedProductionDateEnd) {
        this.estimatedProductionDateEnd = estimatedProductionDateEnd;
        return this;
    }

    public void setEstimatedProductionDateEnd(LocalDate estimatedProductionDateEnd) {
        this.estimatedProductionDateEnd = estimatedProductionDateEnd;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public Inspection inspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
        return this;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSystemId() {
        return systemId;
    }

    public Inspection systemId(String systemId) {
        this.systemId = systemId;
        return this;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPattern() {
        return pattern;
    }

    public Inspection pattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPatternDivisionNumber() {
        return patternDivisionNumber;
    }

    public Inspection patternDivisionNumber(String patternDivisionNumber) {
        this.patternDivisionNumber = patternDivisionNumber;
        return this;
    }

    public void setPatternDivisionNumber(String patternDivisionNumber) {
        this.patternDivisionNumber = patternDivisionNumber;
    }

    public String getPatternDivisionNumberTotal() {
        return patternDivisionNumberTotal;
    }

    public Inspection patternDivisionNumberTotal(String patternDivisionNumberTotal) {
        this.patternDivisionNumberTotal = patternDivisionNumberTotal;
        return this;
    }

    public void setPatternDivisionNumberTotal(String patternDivisionNumberTotal) {
        this.patternDivisionNumberTotal = patternDivisionNumberTotal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspection)) {
            return false;
        }
        return id != null && id.equals(((Inspection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inspection{" +
            "id=" + getId() +
            ", modelYear='" + getModelYear() + "'" +
            ", modelCode='" + getModelCode() + "'" +
            ", lotStart='" + getLotStart() + "'" +
            ", unitStart='" + getUnitStart() + "'" +
            ", lotEnd='" + getLotEnd() + "'" +
            ", unitEnd='" + getUnitEnd() + "'" +
            ", estimatedProductionDateStart='" + getEstimatedProductionDateStart() + "'" +
            ", estimatedProductionDateEnd='" + getEstimatedProductionDateEnd() + "'" +
            ", inspectionId='" + getInspectionId() + "'" +
            ", systemId='" + getSystemId() + "'" +
            ", pattern='" + getPattern() + "'" +
            ", patternDivisionNumber='" + getPatternDivisionNumber() + "'" +
            ", patternDivisionNumberTotal='" + getPatternDivisionNumberTotal() + "'" +
            "}";
    }
}
