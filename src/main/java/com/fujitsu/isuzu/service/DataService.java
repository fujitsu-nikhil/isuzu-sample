package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.Data;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Data}.
 */
public interface DataService {

    /**
     * Save a data.
     *
     * @param data the entity to save.
     * @return the persisted entity.
     */
    Data save(Data data);

    /**
     * Get all the data.
     *
     * @return the list of entities.
     */
    List<Data> findAll();


    /**
     * Get the "id" data.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Data> findOne(Long id);

    /**
     * Delete the "id" data.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
