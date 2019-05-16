package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.InspectionId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InspectionId}.
 */
public interface InspectionIdService {

    /**
     * Save a inspectionId.
     *
     * @param inspectionId the entity to save.
     * @return the persisted entity.
     */
    InspectionId save(InspectionId inspectionId);

    /**
     * Get all the inspectionIds.
     *
     * @return the list of entities.
     */
    List<InspectionId> findAll();


    /**
     * Get the "id" inspectionId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InspectionId> findOne(Long id);

    /**
     * Delete the "id" inspectionId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
