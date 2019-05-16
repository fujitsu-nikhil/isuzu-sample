package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.domain.Data;
import com.fujitsu.isuzu.service.DataService;
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
 * REST controller for managing {@link com.fujitsu.isuzu.domain.Data}.
 */
@RestController
@RequestMapping("/api")
public class DataResource {

    private final Logger log = LoggerFactory.getLogger(DataResource.class);

    private static final String ENTITY_NAME = "data";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataService dataService;

    public DataResource(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * {@code POST  /data} : Create a new data.
     *
     * @param data the data to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new data, or with status {@code 400 (Bad Request)} if the data has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data")
    public ResponseEntity<Data> createData(@RequestBody Data data) throws URISyntaxException {
        log.debug("REST request to save Data : {}", data);
        if (data.getId() != null) {
            throw new BadRequestAlertException("A new data cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Data result = dataService.save(data);
        return ResponseEntity.created(new URI("/api/data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data} : Updates an existing data.
     *
     * @param data the data to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated data,
     * or with status {@code 400 (Bad Request)} if the data is not valid,
     * or with status {@code 500 (Internal Server Error)} if the data couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data")
    public ResponseEntity<Data> updateData(@RequestBody Data data) throws URISyntaxException {
        log.debug("REST request to update Data : {}", data);
        if (data.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Data result = dataService.save(data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, data.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data} : get all the data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of data in body.
     */
    @GetMapping("/data")
    public List<Data> getAllData() {
        log.debug("REST request to get all Data");
        return dataService.findAll();
    }

    /**
     * {@code GET  /data/:id} : get the "id" data.
     *
     * @param id the id of the data to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the data, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data/{id}")
    public ResponseEntity<Data> getData(@PathVariable Long id) {
        log.debug("REST request to get Data : {}", id);
        Optional<Data> data = dataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(data);
    }

    /**
     * {@code DELETE  /data/:id} : delete the "id" data.
     *
     * @param id the id of the data to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        log.debug("REST request to delete Data : {}", id);
        dataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
