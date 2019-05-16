package com.fujitsu.isuzu.service;

import com.fujitsu.isuzu.domain.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Vehicle}.
 */
public interface VehicleService {

    /**
     * Save a vehicle.
     *
     * @param vehicle the entity to save.
     * @return the persisted entity.
     */
    Vehicle save(Vehicle vehicle);

    /**
     * Get all the vehicles.
     *
     * @return the list of entities.
     */
    List<Vehicle> findAll();


    /**
     * Get the "id" vehicle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Vehicle> findOne(Long id);

    /**
     * Delete the "id" vehicle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
