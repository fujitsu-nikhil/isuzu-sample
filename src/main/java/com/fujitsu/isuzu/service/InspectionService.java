package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.Inspection;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Inspection}.
 */
public interface InspectionService {

    /**
     * Save a inspection.
     *
     * @param inspection the entity to save.
     * @return the persisted entity.
     */
    Inspection save(Inspection inspection);

    /**
     * Get all the inspections.
     *
     * @return the list of entities.
     */
    List<Inspection> findAll();


    /**
     * Get the "id" inspection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Inspection> findOne(Long id);

    /**
     * Delete the "id" inspection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
