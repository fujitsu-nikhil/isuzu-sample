package com.fujitsu.isuzu.service.impl;

import com.fujitsu.isuzu.service.InspectionResultService;
import com.fujitsu.isuzu.domain.InspectionResult;
import com.fujitsu.isuzu.repository.InspectionResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InspectionResult}.
 */
@Service
@Transactional
public class InspectionResultServiceImpl implements InspectionResultService {

    private final Logger log = LoggerFactory.getLogger(InspectionResultServiceImpl.class);

    private final InspectionResultRepository inspectionResultRepository;

    public InspectionResultServiceImpl(InspectionResultRepository inspectionResultRepository) {
        this.inspectionResultRepository = inspectionResultRepository;
    }

    /**
     * Save a inspectionResult.
     *
     * @param inspectionResult the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InspectionResult save(InspectionResult inspectionResult) {
        log.debug("Request to save InspectionResult : {}", inspectionResult);
        return inspectionResultRepository.save(inspectionResult);
    }

    /**
     * Get all the inspectionResults.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<InspectionResult> findAll() {
        log.debug("Request to get all InspectionResults");
        return inspectionResultRepository.findAll();
    }


    /**
     * Get one inspectionResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InspectionResult> findOne(Long id) {
        log.debug("Request to get InspectionResult : {}", id);
        return inspectionResultRepository.findById(id);
    }

    /**
     * Delete the inspectionResult by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InspectionResult : {}", id);
        inspectionResultRepository.deleteById(id);
    }
}
