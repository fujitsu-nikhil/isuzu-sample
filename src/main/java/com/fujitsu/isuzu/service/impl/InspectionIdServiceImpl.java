package com.fujitsu.isuzu.service.impl;

import com.fujitsu.isuzu.service.InspectionIdService;
import com.fujitsu.isuzu.domain.InspectionId;
import com.fujitsu.isuzu.repository.InspectionIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InspectionId}.
 */
@Service
@Transactional
public class InspectionIdServiceImpl implements InspectionIdService {

    private final Logger log = LoggerFactory.getLogger(InspectionIdServiceImpl.class);

    private final InspectionIdRepository inspectionIdRepository;

    public InspectionIdServiceImpl(InspectionIdRepository inspectionIdRepository) {
        this.inspectionIdRepository = inspectionIdRepository;
    }

    /**
     * Save a inspectionId.
     *
     * @param inspectionId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InspectionId save(InspectionId inspectionId) {
        log.debug("Request to save InspectionId : {}", inspectionId);
        return inspectionIdRepository.save(inspectionId);
    }

    /**
     * Get all the inspectionIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InspectionId> findAll() {
        log.debug("Request to get all InspectionIds");
        return inspectionIdRepository.findAll();
    }


    /**
     * Get one inspectionId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InspectionId> findOne(Long id) {
        log.debug("Request to get InspectionId : {}", id);
        return inspectionIdRepository.findById(id);
    }

    /**
     * Delete the inspectionId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InspectionId : {}", id);
        inspectionIdRepository.deleteById(id);
    }
}
