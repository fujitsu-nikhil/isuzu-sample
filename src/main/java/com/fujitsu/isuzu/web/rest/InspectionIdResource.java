package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.domain.InspectionId;
import com.fujitsu.isuzu.service.InspectionIdService;
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
 * REST controller for managing {@link com.fujitsu.isuzu.domain.InspectionId}.
 */
@RestController
@RequestMapping("/api")
public class InspectionIdResource {

    private final Logger log = LoggerFactory.getLogger(InspectionIdResource.class);

    private static final String ENTITY_NAME = "inspectionId";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionIdService inspectionIdService;

    public InspectionIdResource(InspectionIdService inspectionIdService) {
        this.inspectionIdService = inspectionIdService;
    }

    /**
     * {@code POST  /inspection-ids} : Create a new inspectionId.
     *
     * @param inspectionId the inspectionId to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionId, or with status {@code 400 (Bad Request)} if the inspectionId has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspection-ids")
    public ResponseEntity<InspectionId> createInspectionId(@Valid @RequestBody InspectionId inspectionId) throws URISyntaxException {
        log.debug("REST request to save InspectionId : {}", inspectionId);
        if (inspectionId.getId() != null) {
            throw new BadRequestAlertException("A new inspectionId cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspectionId result = inspectionIdService.save(inspectionId);
        return ResponseEntity.created(new URI("/api/inspection-ids/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspection-ids} : Updates an existing inspectionId.
     *
     * @param inspectionId the inspectionId to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionId,
     * or with status {@code 400 (Bad Request)} if the inspectionId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionId couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspection-ids")
    public ResponseEntity<InspectionId> updateInspectionId(@Valid @RequestBody InspectionId inspectionId) throws URISyntaxException {
        log.debug("REST request to update InspectionId : {}", inspectionId);
        if (inspectionId.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InspectionId result = inspectionIdService.save(inspectionId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inspectionId.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inspection-ids} : get all the inspectionIds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionIds in body.
     */
    @GetMapping("/inspection-ids")
    public List<InspectionId> getAllInspectionIds() {
        log.debug("REST request to get all InspectionIds");
        return inspectionIdService.findAll();
    }

    /**
     * {@code GET  /inspection-ids/:id} : get the "id" inspectionId.
     *
     * @param id the id of the inspectionId to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionId, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspection-ids/{id}")
    public ResponseEntity<InspectionId> getInspectionId(@PathVariable Long id) {
        log.debug("REST request to get InspectionId : {}", id);
        Optional<InspectionId> inspectionId = inspectionIdService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inspectionId);
    }

    /**
     * {@code DELETE  /inspection-ids/:id} : delete the "id" inspectionId.
     *
     * @param id the id of the inspectionId to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspection-ids/{id}")
    public ResponseEntity<Void> deleteInspectionId(@PathVariable Long id) {
        log.debug("REST request to delete InspectionId : {}", id);
        inspectionIdService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
