package com.fujitsu.isuzu.service.impl;

import com.fujitsu.isuzu.service.InspectionService;
import com.fujitsu.isuzu.domain.Inspection;
import com.fujitsu.isuzu.repository.InspectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Inspection}.
 */
@Service
@Transactional
public class InspectionServiceImpl implements InspectionService {

    private final Logger log = LoggerFactory.getLogger(InspectionServiceImpl.class);

    private final InspectionRepository inspectionRepository;

    public InspectionServiceImpl(InspectionRepository inspectionRepository) {
        this.inspectionRepository = inspectionRepository;
    }

    /**
     * Save a inspection.
     *
     * @param inspection the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Inspection save(Inspection inspection) {
        log.debug("Request to save Inspection : {}", inspection);
        return inspectionRepository.save(inspection);
    }

    /**
     * Get all the inspections.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inspection> findAll() {
        log.debug("Request to get all Inspections");
        return inspectionRepository.findAll();
    }


    /**
     * Get one inspection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Inspection> findOne(Long id) {
        log.debug("Request to get Inspection : {}", id);
        return inspectionRepository.findById(id);
    }

    /**
     * Delete the inspection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inspection : {}", id);
        inspectionRepository.deleteById(id);
    }
}
