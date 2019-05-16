package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.DataId;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DataId}.
 */
public interface DataIdService {

    /**
     * Save a dataId.
     *
     * @param dataId the entity to save.
     * @return the persisted entity.
     */
    DataId save(DataId dataId);

    /**
     * Get all the dataIds.
     *
     * @return the list of entities.
     */
    List<DataId> findAll();


    /**
     * Get the "id" dataId.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataId> findOne(Long id);

    /**
     * Delete the "id" dataId.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
