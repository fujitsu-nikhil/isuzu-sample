package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.Inspection;
import com.fujitsu.isuzu.repository.InspectionRepository;
import com.fujitsu.isuzu.service.InspectionService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.fujitsu.isuzu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link InspectionResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class InspectionResourceIT {

    private static final String DEFAULT_MODEL_YEAR = "AAA";
    private static final String UPDATED_MODEL_YEAR = "BBB";

    private static final String DEFAULT_MODEL_CODE = "AAAAA";
    private static final String UPDATED_MODEL_CODE = "BBBBB";

    private static final String DEFAULT_LOT_START = "AAA";
    private static final String UPDATED_LOT_START = "BBB";

    private static final String DEFAULT_UNIT_START = "AAA";
    private static final String UPDATED_UNIT_START = "BBB";

    private static final String DEFAULT_LOT_END = "AAA";
    private static final String UPDATED_LOT_END = "BBB";

    private static final String DEFAULT_UNIT_END = "AAA";
    private static final String UPDATED_UNIT_END = "BBB";

    private static final LocalDate DEFAULT_ESTIMATED_PRODUCTION_DATE_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_PRODUCTION_DATE_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ESTIMATED_PRODUCTION_DATE_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_PRODUCTION_DATE_END = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INSPECTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SYSTEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_SYSTEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN_DIVISION_NUMBER = "A";
    private static final String UPDATED_PATTERN_DIVISION_NUMBER = "B";

    private static final String DEFAULT_PATTERN_DIVISION_NUMBER_TOTAL = "A";
    private static final String UPDATED_PATTERN_DIVISION_NUMBER_TOTAL = "B";

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private InspectionService inspectionService;

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

    private MockMvc restInspectionMockMvc;

    private Inspection inspection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspectionResource inspectionResource = new InspectionResource(inspectionService);
        this.restInspectionMockMvc = MockMvcBuilders.standaloneSetup(inspectionResource)
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
    public static Inspection createEntity(EntityManager em) {
        Inspection inspection = new Inspection()
            .modelYear(DEFAULT_MODEL_YEAR)
            .modelCode(DEFAULT_MODEL_CODE)
            .lotStart(DEFAULT_LOT_START)
            .unitStart(DEFAULT_UNIT_START)
            .lotEnd(DEFAULT_LOT_END)
            .unitEnd(DEFAULT_UNIT_END)
            .estimatedProductionDateStart(DEFAULT_ESTIMATED_PRODUCTION_DATE_START)
            .estimatedProductionDateEnd(DEFAULT_ESTIMATED_PRODUCTION_DATE_END)
            .inspectionId(DEFAULT_INSPECTION_ID)
            .systemId(DEFAULT_SYSTEM_ID)
            .pattern(DEFAULT_PATTERN)
            .patternDivisionNumber(DEFAULT_PATTERN_DIVISION_NUMBER)
            .patternDivisionNumberTotal(DEFAULT_PATTERN_DIVISION_NUMBER_TOTAL);
        return inspection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspection createUpdatedEntity(EntityManager em) {
        Inspection inspection = new Inspection()
            .modelYear(UPDATED_MODEL_YEAR)
            .modelCode(UPDATED_MODEL_CODE)
            .lotStart(UPDATED_LOT_START)
            .unitStart(UPDATED_UNIT_START)
            .lotEnd(UPDATED_LOT_END)
            .unitEnd(UPDATED_UNIT_END)
            .estimatedProductionDateStart(UPDATED_ESTIMATED_PRODUCTION_DATE_START)
            .estimatedProductionDateEnd(UPDATED_ESTIMATED_PRODUCTION_DATE_END)
            .inspectionId(UPDATED_INSPECTION_ID)
            .systemId(UPDATED_SYSTEM_ID)
            .pattern(UPDATED_PATTERN)
            .patternDivisionNumber(UPDATED_PATTERN_DIVISION_NUMBER)
            .patternDivisionNumberTotal(UPDATED_PATTERN_DIVISION_NUMBER_TOTAL);
        return inspection;
    }

    @BeforeEach
    public void initTest() {
        inspection = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspection() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isCreated());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getModelYear()).isEqualTo(DEFAULT_MODEL_YEAR);
        assertThat(testInspection.getModelCode()).isEqualTo(DEFAULT_MODEL_CODE);
        assertThat(testInspection.getLotStart()).isEqualTo(DEFAULT_LOT_START);
        assertThat(testInspection.getUnitStart()).isEqualTo(DEFAULT_UNIT_START);
        assertThat(testInspection.getLotEnd()).isEqualTo(DEFAULT_LOT_END);
        assertThat(testInspection.getUnitEnd()).isEqualTo(DEFAULT_UNIT_END);
        assertThat(testInspection.getEstimatedProductionDateStart()).isEqualTo(DEFAULT_ESTIMATED_PRODUCTION_DATE_START);
        assertThat(testInspection.getEstimatedProductionDateEnd()).isEqualTo(DEFAULT_ESTIMATED_PRODUCTION_DATE_END);
        assertThat(testInspection.getInspectionId()).isEqualTo(DEFAULT_INSPECTION_ID);
        assertThat(testInspection.getSystemId()).isEqualTo(DEFAULT_SYSTEM_ID);
        assertThat(testInspection.getPattern()).isEqualTo(DEFAULT_PATTERN);
        assertThat(testInspection.getPatternDivisionNumber()).isEqualTo(DEFAULT_PATTERN_DIVISION_NUMBER);
        assertThat(testInspection.getPatternDivisionNumberTotal()).isEqualTo(DEFAULT_PATTERN_DIVISION_NUMBER_TOTAL);
    }

    @Test
    @Transactional
    public void createInspectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectionRepository.findAll().size();

        // Create the Inspection with an existing ID
        inspection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkModelYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setModelYear(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setModelCode(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLotStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setLotStart(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setUnitStart(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLotEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setLotEnd(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setUnitEnd(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstimatedProductionDateStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setEstimatedProductionDateStart(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstimatedProductionDateEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspectionRepository.findAll().size();
        // set the field null
        inspection.setEstimatedProductionDateEnd(null);

        // Create the Inspection, which fails.

        restInspectionMockMvc.perform(post("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInspections() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get all the inspectionList
        restInspectionMockMvc.perform(get("/api/inspections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspection.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelYear").value(hasItem(DEFAULT_MODEL_YEAR.toString())))
            .andExpect(jsonPath("$.[*].modelCode").value(hasItem(DEFAULT_MODEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].lotStart").value(hasItem(DEFAULT_LOT_START.toString())))
            .andExpect(jsonPath("$.[*].unitStart").value(hasItem(DEFAULT_UNIT_START.toString())))
            .andExpect(jsonPath("$.[*].lotEnd").value(hasItem(DEFAULT_LOT_END.toString())))
            .andExpect(jsonPath("$.[*].unitEnd").value(hasItem(DEFAULT_UNIT_END.toString())))
            .andExpect(jsonPath("$.[*].estimatedProductionDateStart").value(hasItem(DEFAULT_ESTIMATED_PRODUCTION_DATE_START.toString())))
            .andExpect(jsonPath("$.[*].estimatedProductionDateEnd").value(hasItem(DEFAULT_ESTIMATED_PRODUCTION_DATE_END.toString())))
            .andExpect(jsonPath("$.[*].inspectionId").value(hasItem(DEFAULT_INSPECTION_ID.toString())))
            .andExpect(jsonPath("$.[*].systemId").value(hasItem(DEFAULT_SYSTEM_ID.toString())))
            .andExpect(jsonPath("$.[*].pattern").value(hasItem(DEFAULT_PATTERN.toString())))
            .andExpect(jsonPath("$.[*].patternDivisionNumber").value(hasItem(DEFAULT_PATTERN_DIVISION_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].patternDivisionNumberTotal").value(hasItem(DEFAULT_PATTERN_DIVISION_NUMBER_TOTAL.toString())));
    }
    
    @Test
    @Transactional
    public void getInspection() throws Exception {
        // Initialize the database
        inspectionRepository.saveAndFlush(inspection);

        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", inspection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspection.getId().intValue()))
            .andExpect(jsonPath("$.modelYear").value(DEFAULT_MODEL_YEAR.toString()))
            .andExpect(jsonPath("$.modelCode").value(DEFAULT_MODEL_CODE.toString()))
            .andExpect(jsonPath("$.lotStart").value(DEFAULT_LOT_START.toString()))
            .andExpect(jsonPath("$.unitStart").value(DEFAULT_UNIT_START.toString()))
            .andExpect(jsonPath("$.lotEnd").value(DEFAULT_LOT_END.toString()))
            .andExpect(jsonPath("$.unitEnd").value(DEFAULT_UNIT_END.toString()))
            .andExpect(jsonPath("$.estimatedProductionDateStart").value(DEFAULT_ESTIMATED_PRODUCTION_DATE_START.toString()))
            .andExpect(jsonPath("$.estimatedProductionDateEnd").value(DEFAULT_ESTIMATED_PRODUCTION_DATE_END.toString()))
            .andExpect(jsonPath("$.inspectionId").value(DEFAULT_INSPECTION_ID.toString()))
            .andExpect(jsonPath("$.systemId").value(DEFAULT_SYSTEM_ID.toString()))
            .andExpect(jsonPath("$.pattern").value(DEFAULT_PATTERN.toString()))
            .andExpect(jsonPath("$.patternDivisionNumber").value(DEFAULT_PATTERN_DIVISION_NUMBER.toString()))
            .andExpect(jsonPath("$.patternDivisionNumberTotal").value(DEFAULT_PATTERN_DIVISION_NUMBER_TOTAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInspection() throws Exception {
        // Get the inspection
        restInspectionMockMvc.perform(get("/api/inspections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspection() throws Exception {
        // Initialize the database
        inspectionService.save(inspection);

        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Update the inspection
        Inspection updatedInspection = inspectionRepository.findById(inspection.getId()).get();
        // Disconnect from session so that the updates on updatedInspection are not directly saved in db
        em.detach(updatedInspection);
        updatedInspection
            .modelYear(UPDATED_MODEL_YEAR)
            .modelCode(UPDATED_MODEL_CODE)
            .lotStart(UPDATED_LOT_START)
            .unitStart(UPDATED_UNIT_START)
            .lotEnd(UPDATED_LOT_END)
            .unitEnd(UPDATED_UNIT_END)
            .estimatedProductionDateStart(UPDATED_ESTIMATED_PRODUCTION_DATE_START)
            .estimatedProductionDateEnd(UPDATED_ESTIMATED_PRODUCTION_DATE_END)
            .inspectionId(UPDATED_INSPECTION_ID)
            .systemId(UPDATED_SYSTEM_ID)
            .pattern(UPDATED_PATTERN)
            .patternDivisionNumber(UPDATED_PATTERN_DIVISION_NUMBER)
            .patternDivisionNumberTotal(UPDATED_PATTERN_DIVISION_NUMBER_TOTAL);

        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspection)))
            .andExpect(status().isOk());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
        Inspection testInspection = inspectionList.get(inspectionList.size() - 1);
        assertThat(testInspection.getModelYear()).isEqualTo(UPDATED_MODEL_YEAR);
        assertThat(testInspection.getModelCode()).isEqualTo(UPDATED_MODEL_CODE);
        assertThat(testInspection.getLotStart()).isEqualTo(UPDATED_LOT_START);
        assertThat(testInspection.getUnitStart()).isEqualTo(UPDATED_UNIT_START);
        assertThat(testInspection.getLotEnd()).isEqualTo(UPDATED_LOT_END);
        assertThat(testInspection.getUnitEnd()).isEqualTo(UPDATED_UNIT_END);
        assertThat(testInspection.getEstimatedProductionDateStart()).isEqualTo(UPDATED_ESTIMATED_PRODUCTION_DATE_START);
        assertThat(testInspection.getEstimatedProductionDateEnd()).isEqualTo(UPDATED_ESTIMATED_PRODUCTION_DATE_END);
        assertThat(testInspection.getInspectionId()).isEqualTo(UPDATED_INSPECTION_ID);
        assertThat(testInspection.getSystemId()).isEqualTo(UPDATED_SYSTEM_ID);
        assertThat(testInspection.getPattern()).isEqualTo(UPDATED_PATTERN);
        assertThat(testInspection.getPatternDivisionNumber()).isEqualTo(UPDATED_PATTERN_DIVISION_NUMBER);
        assertThat(testInspection.getPatternDivisionNumberTotal()).isEqualTo(UPDATED_PATTERN_DIVISION_NUMBER_TOTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingInspection() throws Exception {
        int databaseSizeBeforeUpdate = inspectionRepository.findAll().size();

        // Create the Inspection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionMockMvc.perform(put("/api/inspections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspection)))
            .andExpect(status().isBadRequest());

        // Validate the Inspection in the database
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInspection() throws Exception {
        // Initialize the database
        inspectionService.save(inspection);

        int databaseSizeBeforeDelete = inspectionRepository.findAll().size();

        // Delete the inspection
        restInspectionMockMvc.perform(delete("/api/inspections/{id}", inspection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Inspection> inspectionList = inspectionRepository.findAll();
        assertThat(inspectionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspection.class);
        Inspection inspection1 = new Inspection();
        inspection1.setId(1L);
        Inspection inspection2 = new Inspection();
        inspection2.setId(inspection1.getId());
        assertThat(inspection1).isEqualTo(inspection2);
        inspection2.setId(2L);
        assertThat(inspection1).isNotEqualTo(inspection2);
        inspection1.setId(null);
        assertThat(inspection1).isNotEqualTo(inspection2);
    }
}
