package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.domain.InspectionResult;
import com.fujitsu.isuzu.service.InspectionResultService;
import com.fujitsu.isuzu.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fujitsu.isuzu.domain.InspectionResult}.
 */
@RestController
@RequestMapping("/api")
public class InspectionResultResource {

    private final Logger log = LoggerFactory.getLogger(InspectionResultResource.class);

    private static final String ENTITY_NAME = "inspectionResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspectionResultService inspectionResultService;

    public InspectionResultResource(InspectionResultService inspectionResultService) {
        this.inspectionResultService = inspectionResultService;
    }

    /**
     * {@code POST  /inspection-results} : Create a new inspectionResult.
     *
     * @param inspectionResult the inspectionResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspectionResult, or with status {@code 400 (Bad Request)} if the inspectionResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspection-results")
    public ResponseEntity<InspectionResult> createInspectionResult(@RequestBody InspectionResult inspectionResult) throws URISyntaxException {
        log.debug("REST request to save InspectionResult : {}", inspectionResult);
        if (inspectionResult.getId() != null) {
            throw new BadRequestAlertException("A new inspectionResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspectionResult result = inspectionResultService.save(inspectionResult);
        return ResponseEntity.created(new URI("/api/inspection-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspection-results} : Updates an existing inspectionResult.
     *
     * @param inspectionResult the inspectionResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspectionResult,
     * or with status {@code 400 (Bad Request)} if the inspectionResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspectionResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspection-results")
    public ResponseEntity<InspectionResult> updateInspectionResult(@RequestBody InspectionResult inspectionResult) throws URISyntaxException {
        log.debug("REST request to update InspectionResult : {}", inspectionResult);
        if (inspectionResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InspectionResult result = inspectionResultService.save(inspectionResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, inspectionResult.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inspection-results} : get all the inspectionResults.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspectionResults in body.
     */
    @GetMapping("/inspection-results")
    public List<InspectionResult> getAllInspectionResults() {
        log.debug("REST request to get all InspectionResults");
        return inspectionResultService.findAll();
    }

    /**
     * {@code GET  /inspection-results/:id} : get the "id" inspectionResult.
     *
     * @param id the id of the inspectionResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspectionResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspection-results/{id}")
    public ResponseEntity<InspectionResult> getInspectionResult(@PathVariable Long id) {
        log.debug("REST request to get InspectionResult : {}", id);
        Optional<InspectionResult> inspectionResult = inspectionResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inspectionResult);
    }

    /**
     * {@code DELETE  /inspection-results/:id} : delete the "id" inspectionResult.
     *
     * @param id the id of the inspectionResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspection-results/{id}")
    public ResponseEntity<Void> deleteInspectionResult(@PathVariable Long id) {
        log.debug("REST request to delete InspectionResult : {}", id);
        inspectionResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
