package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.InspectionId;
import com.fujitsu.isuzu.repository.InspectionIdRepository;
import com.fujitsu.isuzu.service.InspectionIdService;
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
 * Integration tests for the {@Link InspectionIdResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class InspectionIdResourceIT {

    private static final String DEFAULT_INSPECTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INSPECTION_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_INSPECTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSPECTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SORT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InspectionIdRepository inspectionIdRepository;

    @Autowired
    private InspectionIdService inspectionIdService;

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

    private MockMvc restInspectionIdMockMvc;

    private InspectionId inspectionId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspectionIdResource inspectionIdResource = new InspectionIdResource(inspectionIdService);
        this.restInspectionIdMockMvc = MockMvcBuilders.standaloneSetup(inspectionIdResource)
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
    public static InspectionId createEntity(EntityManager em) {
        InspectionId inspectionId = new InspectionId()
            .inspectionId(DEFAULT_INSPECTION_ID)
            .inspectionFlag(DEFAULT_INSPECTION_FLAG)
            .inspectionName(DEFAULT_INSPECTION_NAME)
            .sortNumber(DEFAULT_SORT_NUMBER)
            .updatedAt(DEFAULT_UPDATED_AT);
        return inspectionId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InspectionId createUpdatedEntity(EntityManager em) {
        InspectionId inspectionId = new InspectionId()
            .inspectionId(UPDATED_INSPECTION_ID)
            .inspectionFlag(UPDATED_INSPECTION_FLAG)
            .inspectionName(UPDATED_INSPECTION_NAME)
            .sortNumber(UPDATED_SORT_NUMBER)
            .updatedAt(UPDATED_UPDATED_AT);
        return inspectionId;
    }

    @BeforeEach
    public void initTest() {
        inspectionId = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspectionId() throws Exception {
        int databaseSizeBeforeCreate = inspectionIdRepository.findAll().size();

        // Create the InspectionId
        restInspectionIdMockMvc.perform(post("/api/inspection-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionId)))
            .andExpect(status().isCreated());

        // Validate the InspectionId in the database
        List<InspectionId> inspectionIdList = inspectionIdRepository.findAll();
        assertThat(inspectionIdList).hasSize(databaseSizeBeforeCreate + 1);
        InspectionId testInspectionId = inspectionIdList.get(inspectionIdList.size() - 1);
        assertThat(testInspectionId.getInspectionId()).isEqualTo(DEFAULT_INSPECTION_ID);
        assertThat(testInspectionId.getInspectionFlag()).isEqualTo(DEFAULT_INSPECTION_FLAG);
        assertThat(testInspectionId.getInspectionName()).isEqualTo(DEFAULT_INSPECTION_NAME);
        assertThat(testInspectionId.getSortNumber()).isEqualTo(DEFAULT_SORT_NUMBER);
        assertThat(testInspectionId.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createInspectionIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspectionIdRepository.findAll().size();

        // Create the InspectionId with an existing ID
        inspectionId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspectionIdMockMvc.perform(post("/api/inspection-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionId)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionId in the database
        List<InspectionId> inspectionIdList = inspectionIdRepository.findAll();
        assertThat(inspectionIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInspectionIds() throws Exception {
        // Initialize the database
        inspectionIdRepository.saveAndFlush(inspectionId);

        // Get all the inspectionIdList
        restInspectionIdMockMvc.perform(get("/api/inspection-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspectionId.getId().intValue())))
            .andExpect(jsonPath("$.[*].inspectionId").value(hasItem(DEFAULT_INSPECTION_ID.toString())))
            .andExpect(jsonPath("$.[*].inspectionFlag").value(hasItem(DEFAULT_INSPECTION_FLAG.toString())))
            .andExpect(jsonPath("$.[*].inspectionName").value(hasItem(DEFAULT_INSPECTION_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNumber").value(hasItem(DEFAULT_SORT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getInspectionId() throws Exception {
        // Initialize the database
        inspectionIdRepository.saveAndFlush(inspectionId);

        // Get the inspectionId
        restInspectionIdMockMvc.perform(get("/api/inspection-ids/{id}", inspectionId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspectionId.getId().intValue()))
            .andExpect(jsonPath("$.inspectionId").value(DEFAULT_INSPECTION_ID.toString()))
            .andExpect(jsonPath("$.inspectionFlag").value(DEFAULT_INSPECTION_FLAG.toString()))
            .andExpect(jsonPath("$.inspectionName").value(DEFAULT_INSPECTION_NAME.toString()))
            .andExpect(jsonPath("$.sortNumber").value(DEFAULT_SORT_NUMBER.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInspectionId() throws Exception {
        // Get the inspectionId
        restInspectionIdMockMvc.perform(get("/api/inspection-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspectionId() throws Exception {
        // Initialize the database
        inspectionIdService.save(inspectionId);

        int databaseSizeBeforeUpdate = inspectionIdRepository.findAll().size();

        // Update the inspectionId
        InspectionId updatedInspectionId = inspectionIdRepository.findById(inspectionId.getId()).get();
        // Disconnect from session so that the updates on updatedInspectionId are not directly saved in db
        em.detach(updatedInspectionId);
        updatedInspectionId
            .inspectionId(UPDATED_INSPECTION_ID)
            .inspectionFlag(UPDATED_INSPECTION_FLAG)
            .inspectionName(UPDATED_INSPECTION_NAME)
            .sortNumber(UPDATED_SORT_NUMBER)
            .updatedAt(UPDATED_UPDATED_AT);

        restInspectionIdMockMvc.perform(put("/api/inspection-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspectionId)))
            .andExpect(status().isOk());

        // Validate the InspectionId in the database
        List<InspectionId> inspectionIdList = inspectionIdRepository.findAll();
        assertThat(inspectionIdList).hasSize(databaseSizeBeforeUpdate);
        InspectionId testInspectionId = inspectionIdList.get(inspectionIdList.size() - 1);
        assertThat(testInspectionId.getInspectionId()).isEqualTo(UPDATED_INSPECTION_ID);
        assertThat(testInspectionId.getInspectionFlag()).isEqualTo(UPDATED_INSPECTION_FLAG);
        assertThat(testInspectionId.getInspectionName()).isEqualTo(UPDATED_INSPECTION_NAME);
        assertThat(testInspectionId.getSortNumber()).isEqualTo(UPDATED_SORT_NUMBER);
        assertThat(testInspectionId.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingInspectionId() throws Exception {
        int databaseSizeBeforeUpdate = inspectionIdRepository.findAll().size();

        // Create the InspectionId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspectionIdMockMvc.perform(put("/api/inspection-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspectionId)))
            .andExpect(status().isBadRequest());

        // Validate the InspectionId in the database
        List<InspectionId> inspectionIdList = inspectionIdRepository.findAll();
        assertThat(inspectionIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInspectionId() throws Exception {
        // Initialize the database
        inspectionIdService.save(inspectionId);

        int databaseSizeBeforeDelete = inspectionIdRepository.findAll().size();

        // Delete the inspectionId
        restInspectionIdMockMvc.perform(delete("/api/inspection-ids/{id}", inspectionId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<InspectionId> inspectionIdList = inspectionIdRepository.findAll();
        assertThat(inspectionIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspectionId.class);
        InspectionId inspectionId1 = new InspectionId();
        inspectionId1.setId(1L);
        InspectionId inspectionId2 = new InspectionId();
        inspectionId2.setId(inspectionId1.getId());
        assertThat(inspectionId1).isEqualTo(inspectionId2);
        inspectionId2.setId(2L);
        assertThat(inspectionId1).isNotEqualTo(inspectionId2);
        inspectionId1.setId(null);
        assertThat(inspectionId1).isNotEqualTo(inspectionId2);
    }
}
