package com.fujitsu.isuzu.service.impl;

import com.fujitsu.isuzu.service.DataIdService;
import com.fujitsu.isuzu.domain.DataId;
import com.fujitsu.isuzu.repository.DataIdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DataId}.
 */
@Service
@Transactional
public class DataIdServiceImpl implements DataIdService {

    private final Logger log = LoggerFactory.getLogger(DataIdServiceImpl.class);

    private final DataIdRepository dataIdRepository;

    public DataIdServiceImpl(DataIdRepository dataIdRepository) {
        this.dataIdRepository = dataIdRepository;
    }

    /**
     * Save a dataId.
     *
     * @param dataId the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DataId save(DataId dataId) {
        log.debug("Request to save DataId : {}", dataId);
        return dataIdRepository.save(dataId);
    }

    /**
     * Get all the dataIds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataId> findAll() {
        log.debug("Request to get all DataIds");
        return dataIdRepository.findAll();
    }


    /**
     * Get one dataId by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataId> findOne(Long id) {
        log.debug("Request to get DataId : {}", id);
        return dataIdRepository.findById(id);
    }

    /**
     * Delete the dataId by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataId : {}", id);
        dataIdRepository.deleteById(id);
    }
}
