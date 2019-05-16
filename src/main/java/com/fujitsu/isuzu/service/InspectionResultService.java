package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.InspectionResult;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InspectionResult}.
 */
public interface InspectionResultService {

    /**
     * Save a inspectionResult.
     *
     * @param inspectionResult the entity to save.
     * @return the persisted entity.
     */
    InspectionResult save(InspectionResult inspectionResult);

    /**
     * Get all the inspectionResults.
     *
     * @return the list of entities.
     */
    List<InspectionResult> findAll();


    /**
     * Get the "id" inspectionResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InspectionResult> findOne(Long id);

    /**
     * Delete the "id" inspectionResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
