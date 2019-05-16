package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.domain.DataId;
import com.fujitsu.isuzu.service.DataIdService;
import com.fujitsu.isuzu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fujitsu.isuzu.domain.DataId}.
 */
@RestController
@RequestMapping("/api")
public class DataIdResource {

    private final Logger log = LoggerFactory.getLogger(DataIdResource.class);

    private static final String ENTITY_NAME = "dataId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataIdService dataIdService;

    public DataIdResource(DataIdService dataIdService) {
        this.dataIdService = dataIdService;
    }

    /**
     * {@code POST  /data-ids} : Create a new dataId.
     *
     * @param dataId the dataId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataId, or with status {@code 400 (Bad Request)} if the dataId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-ids")
    public ResponseEntity<DataId> createDataId(@Valid @RequestBody DataId dataId) throws URISyntaxException {
        log.debug("REST request to save DataId : {}", dataId);
        if (dataId.getId() != null) {
            throw new BadRequestAlertException("A new dataId cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataId result = dataIdService.save(dataId);
        return ResponseEntity.created(new URI("/api/data-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-ids} : Updates an existing dataId.
     *
     * @param dataId the dataId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataId,
     * or with status {@code 400 (Bad Request)} if the dataId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-ids")
    public ResponseEntity<DataId> updateDataId(@Valid @RequestBody DataId dataId) throws URISyntaxException {
        log.debug("REST request to update DataId : {}", dataId);
        if (dataId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataId result = dataIdService.save(dataId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dataId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-ids} : get all the dataIds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataIds in body.
     */
    @GetMapping("/data-ids")
    public List<DataId> getAllDataIds() {
        log.debug("REST request to get all DataIds");
        return dataIdService.findAll();
    }

    /**
     * {@code GET  /data-ids/:id} : get the "id" dataId.
     *
     * @param id the id of the dataId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataId, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-ids/{id}")
    public ResponseEntity<DataId> getDataId(@PathVariable Long id) {
        log.debug("REST request to get DataId : {}", id);
        Optional<DataId> dataId = dataIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataId);
    }

    /**
     * {@code DELETE  /data-ids/:id} : delete the "id" dataId.
     *
     * @param id the id of the dataId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-ids/{id}")
    public ResponseEntity<Void> deleteDataId(@PathVariable Long id) {
        log.debug("REST request to delete DataId : {}", id);
        dataIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
