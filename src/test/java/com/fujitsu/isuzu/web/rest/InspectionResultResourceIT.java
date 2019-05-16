package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.InspectionResult;
import com.fujitsu.isuzu.repository.InspectionResultRepository;
import com.fujitsu.isuzu.service.InspectionResultService;
import com.fujitsu.isuzu.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.fujitsu.isuzu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InspectionResultResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class InspectionResultResourceIT {

    private static final String DEFAULT_VEHICLE_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INSPECTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN_1 = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN_1 = "BBBBBBBBBB";

    private static final String DEFAULT_JUDGMENT = "AAAAAAAAAA";
    private static final String UPDATED_JUDGMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PARTS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PARTS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InspectionResultRepository inspectionResultRepository;

    @Autowired
    private InspectionResultService inspectionResultService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restInspectionResultMockMvc;

    private InspectionResult inspectionResult;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspectionResultResource inspectionResultResource = new InspectionResultResource(inspectionResultService);
        this.restInspectionResultMockMvc = MockMvcBuilders.standaloneSetup(inspectionResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionResult createEntity(EntityManager em) {
        InspectionResult inspectionResult = new InspectionResult()
            .vehicleIdNumber(DEFAULT_VEHICLE_ID_NUMBER)
            .inspectionId(DEFAULT_INSPECTION_ID)
            .systemId(DEFAULT_SYSTEM_ID)
            .pattern1(DEFAULT_PATTERN_1)
            .judgment(DEFAULT_JUDGMENT)
            .createdAt(DEFAULT_CREATED_AT)
            .partsNumber(DEFAULT_PARTS_NUMBER)
            .detail(DEFAULT_DETAIL)
            .updatedAt(DEFAULT_UPDATED_AT);
        return inspectionResult;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionResult createUpdatedEntity(EntityManager em) {
        InspectionResult inspectionResult = new InspectionResult()
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .inspectionId(UPDATED_INSPECTION_ID)
            .systemId(UPDATED_SYSTEM_ID)
            .pattern1(UPDATED_PATTERN_1)
            .judgment(UPDATED_JUDGMENT)
            .createdAt(UPDATED_CREATED_AT)
            .partsNumber(UPDATED_PARTS_NUMBER)
            .detail(UPDATED_DETAIL)
            .updatedAt(UPDATED_UPDATED_AT);
        return inspectionResult;
    }

    @BeforeEach
    public void initTest() {
        inspectionResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspectionResult() throws Exception {
        int databaseSizeBeforeCreate = inspectionResultRepository.findAll().size();

        // Create the InspectionResult
        restInspectionResultMockMvc.perform(post("/api/inspection-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionResult)))
            .andExpect(status().isCreated());

        // Validate the InspectionResult in the database
        List<InspectionResult> inspectionResultList = inspectionResultRepository.findAll();
        assertThat(inspectionResultList).hasSize(databaseSizeBeforeCreate + 1);
        InspectionResult testInspectionResult = inspectionResultList.get(inspectionResultList.size() - 1);
        assertThat(testInspectionResult.getVehicleIdNumber()).isEqualTo(DEFAULT_VEHICLE_ID_NUMBER);
        assertThat(testInspectionResult.getInspectionId()).isEqualTo(DEFAULT_INSPECTION_ID);
        assertThat(testInspectionResult.getSystemId()).isEqualTo(DEFAULT_SYSTEM_ID);
        assertThat(testInspectionResult.getPattern1()).isEqualTo(DEFAULT_PATTERN_1);
        assertThat(testInspectionResult.getJudgment()).isEqualTo(DEFAULT_JUDGMENT);
        assertThat(testInspectionResult.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testInspectionResult.getPartsNumber()).isEqualTo(DEFAULT_PARTS_NUMBER);
        assertThat(testInspectionResult.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testInspectionResult.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createInspectionResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectionResultRepository.findAll().size();

        // Create the InspectionResult with an existing ID
        inspectionResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionResultMockMvc.perform(post("/api/inspection-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionResult)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionResult in the database
        List<InspectionResult> inspectionResultList = inspectionResultRepository.findAll();
        assertThat(inspectionResultList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInspectionResults() throws Exception {
        // Initialize the database
        inspectionResultRepository.saveAndFlush(inspectionResult);

        // Get all the inspectionResultList
        restInspectionResultMockMvc.perform(get("/api/inspection-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleIdNumber").value(hasItem(DEFAULT_VEHICLE_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].inspectionId").value(hasItem(DEFAULT_INSPECTION_ID.toString())))
            .andExpect(jsonPath("$.[*].systemId").value(hasItem(DEFAULT_SYSTEM_ID.toString())))
            .andExpect(jsonPath("$.[*].pattern1").value(hasItem(DEFAULT_PATTERN_1.toString())))
            .andExpect(jsonPath("$.[*].judgment").value(hasItem(DEFAULT_JUDGMENT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].partsNumber").value(hasItem(DEFAULT_PARTS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getInspectionResult() throws Exception {
        // Initialize the database
        inspectionResultRepository.saveAndFlush(inspectionResult);

        // Get the inspectionResult
        restInspectionResultMockMvc.perform(get("/api/inspection-results/{id}", inspectionResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionResult.getId().intValue()))
            .andExpect(jsonPath("$.vehicleIdNumber").value(DEFAULT_VEHICLE_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.inspectionId").value(DEFAULT_INSPECTION_ID.toString()))
            .andExpect(jsonPath("$.systemId").value(DEFAULT_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.pattern1").value(DEFAULT_PATTERN_1.toString()))
            .andExpect(jsonPath("$.judgment").value(DEFAULT_JUDGMENT.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.partsNumber").value(DEFAULT_PARTS_NUMBER.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInspectionResult() throws Exception {
        // Get the inspectionResult
        restInspectionResultMockMvc.perform(get("/api/inspection-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspectionResult() throws Exception {
        // Initialize the database
        inspectionResultService.save(inspectionResult);

        int databaseSizeBeforeUpdate = inspectionResultRepository.findAll().size();

        // Update the inspectionResult
        InspectionResult updatedInspectionResult = inspectionResultRepository.findById(inspectionResult.getId()).get();
        // Disconnect from session so that the updates on updatedInspectionResult are not directly saved in db
        em.detach(updatedInspectionResult);
        updatedInspectionResult
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .inspectionId(UPDATED_INSPECTION_ID)
            .systemId(UPDATED_SYSTEM_ID)
            .pattern1(UPDATED_PATTERN_1)
            .judgment(UPDATED_JUDGMENT)
            .createdAt(UPDATED_CREATED_AT)
            .partsNumber(UPDATED_PARTS_NUMBER)
            .detail(UPDATED_DETAIL)
            .updatedAt(UPDATED_UPDATED_AT);

        restInspectionResultMockMvc.perform(put("/api/inspection-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspectionResult)))
            .andExpect(status().isOk());

        // Validate the InspectionResult in the database
        List<InspectionResult> inspectionResultList = inspectionResultRepository.findAll();
        assertThat(inspectionResultList).hasSize(databaseSizeBeforeUpdate);
        InspectionResult testInspectionResult = inspectionResultList.get(inspectionResultList.size() - 1);
        assertThat(testInspectionResult.getVehicleIdNumber()).isEqualTo(UPDATED_VEHICLE_ID_NUMBER);
        assertThat(testInspectionResult.getInspectionId()).isEqualTo(UPDATED_INSPECTION_ID);
        assertThat(testInspectionResult.getSystemId()).isEqualTo(UPDATED_SYSTEM_ID);
        assertThat(testInspectionResult.getPattern1()).isEqualTo(UPDATED_PATTERN_1);
        assertThat(testInspectionResult.getJudgment()).isEqualTo(UPDATED_JUDGMENT);
        assertThat(testInspectionResult.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testInspectionResult.getPartsNumber()).isEqualTo(UPDATED_PARTS_NUMBER);
        assertThat(testInspectionResult.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testInspectionResult.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingInspectionResult() throws Exception {
        int databaseSizeBeforeUpdate = inspectionResultRepository.findAll().size();

        // Create the InspectionResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionResultMockMvc.perform(put("/api/inspection-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionResult)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionResult in the database
        List<InspectionResult> inspectionResultList = inspectionResultRepository.findAll();
        assertThat(inspectionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInspectionResult() throws Exception {
        // Initialize the database
        inspectionResultService.save(inspectionResult);

        int databaseSizeBeforeDelete = inspectionResultRepository.findAll().size();

        // Delete the inspectionResult
        restInspectionResultMockMvc.perform(delete("/api/inspection-results/{id}", inspectionResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InspectionResult> inspectionResultList = inspectionResultRepository.findAll();
        assertThat(inspectionResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionResult.class);
        InspectionResult inspectionResult1 = new InspectionResult();
        inspectionResult1.setId(1L);
        InspectionResult inspectionResult2 = new InspectionResult();
        inspectionResult2.setId(inspectionResult1.getId());
        assertThat(inspectionResult1).isEqualTo(inspectionResult2);
        inspectionResult2.setId(2L);
        assertThat(inspectionResult1).isNotEqualTo(inspectionResult2);
        inspectionResult1.setId(null);
        assertThat(inspectionResult1).isNotEqualTo(inspectionResult2);
    }
}
