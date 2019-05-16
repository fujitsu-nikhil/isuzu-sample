package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.DataId;
import com.fujitsu.isuzu.repository.DataIdRepository;
import com.fujitsu.isuzu.service.DataIdService;
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
 * Integration tests for the {@Link DataIdResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class DataIdResourceIT {

    private static final String DEFAULT_DATA_ID = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SORT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SORT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DataIdRepository dataIdRepository;

    @Autowired
    private DataIdService dataIdService;

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

    private MockMvc restDataIdMockMvc;

    private DataId dataId;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataIdResource dataIdResource = new DataIdResource(dataIdService);
        this.restDataIdMockMvc = MockMvcBuilders.standaloneSetup(dataIdResource)
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
    public static DataId createEntity(EntityManager em) {
        DataId dataId = new DataId()
            .dataId(DEFAULT_DATA_ID)
            .dataName(DEFAULT_DATA_NAME)
            .sortNumber(DEFAULT_SORT_NUMBER)
            .updatedAt(DEFAULT_UPDATED_AT);
        return dataId;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataId createUpdatedEntity(EntityManager em) {
        DataId dataId = new DataId()
            .dataId(UPDATED_DATA_ID)
            .dataName(UPDATED_DATA_NAME)
            .sortNumber(UPDATED_SORT_NUMBER)
            .updatedAt(UPDATED_UPDATED_AT);
        return dataId;
    }

    @BeforeEach
    public void initTest() {
        dataId = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataId() throws Exception {
        int databaseSizeBeforeCreate = dataIdRepository.findAll().size();

        // Create the DataId
        restDataIdMockMvc.perform(post("/api/data-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataId)))
            .andExpect(status().isCreated());

        // Validate the DataId in the database
        List<DataId> dataIdList = dataIdRepository.findAll();
        assertThat(dataIdList).hasSize(databaseSizeBeforeCreate + 1);
        DataId testDataId = dataIdList.get(dataIdList.size() - 1);
        assertThat(testDataId.getDataId()).isEqualTo(DEFAULT_DATA_ID);
        assertThat(testDataId.getDataName()).isEqualTo(DEFAULT_DATA_NAME);
        assertThat(testDataId.getSortNumber()).isEqualTo(DEFAULT_SORT_NUMBER);
        assertThat(testDataId.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createDataIdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataIdRepository.findAll().size();

        // Create the DataId with an existing ID
        dataId.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataIdMockMvc.perform(post("/api/data-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataId)))
            .andExpect(status().isBadRequest());

        // Validate the DataId in the database
        List<DataId> dataIdList = dataIdRepository.findAll();
        assertThat(dataIdList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDataIds() throws Exception {
        // Initialize the database
        dataIdRepository.saveAndFlush(dataId);

        // Get all the dataIdList
        restDataIdMockMvc.perform(get("/api/data-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataId.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataId").value(hasItem(DEFAULT_DATA_ID.toString())))
            .andExpect(jsonPath("$.[*].dataName").value(hasItem(DEFAULT_DATA_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNumber").value(hasItem(DEFAULT_SORT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getDataId() throws Exception {
        // Initialize the database
        dataIdRepository.saveAndFlush(dataId);

        // Get the dataId
        restDataIdMockMvc.perform(get("/api/data-ids/{id}", dataId.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataId.getId().intValue()))
            .andExpect(jsonPath("$.dataId").value(DEFAULT_DATA_ID.toString()))
            .andExpect(jsonPath("$.dataName").value(DEFAULT_DATA_NAME.toString()))
            .andExpect(jsonPath("$.sortNumber").value(DEFAULT_SORT_NUMBER.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDataId() throws Exception {
        // Get the dataId
        restDataIdMockMvc.perform(get("/api/data-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataId() throws Exception {
        // Initialize the database
        dataIdService.save(dataId);

        int databaseSizeBeforeUpdate = dataIdRepository.findAll().size();

        // Update the dataId
        DataId updatedDataId = dataIdRepository.findById(dataId.getId()).get();
        // Disconnect from session so that the updates on updatedDataId are not directly saved in db
        em.detach(updatedDataId);
        updatedDataId
            .dataId(UPDATED_DATA_ID)
            .dataName(UPDATED_DATA_NAME)
            .sortNumber(UPDATED_SORT_NUMBER)
            .updatedAt(UPDATED_UPDATED_AT);

        restDataIdMockMvc.perform(put("/api/data-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataId)))
            .andExpect(status().isOk());

        // Validate the DataId in the database
        List<DataId> dataIdList = dataIdRepository.findAll();
        assertThat(dataIdList).hasSize(databaseSizeBeforeUpdate);
        DataId testDataId = dataIdList.get(dataIdList.size() - 1);
        assertThat(testDataId.getDataId()).isEqualTo(UPDATED_DATA_ID);
        assertThat(testDataId.getDataName()).isEqualTo(UPDATED_DATA_NAME);
        assertThat(testDataId.getSortNumber()).isEqualTo(UPDATED_SORT_NUMBER);
        assertThat(testDataId.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingDataId() throws Exception {
        int databaseSizeBeforeUpdate = dataIdRepository.findAll().size();

        // Create the DataId

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataIdMockMvc.perform(put("/api/data-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataId)))
            .andExpect(status().isBadRequest());

        // Validate the DataId in the database
        List<DataId> dataIdList = dataIdRepository.findAll();
        assertThat(dataIdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataId() throws Exception {
        // Initialize the database
        dataIdService.save(dataId);

        int databaseSizeBeforeDelete = dataIdRepository.findAll().size();

        // Delete the dataId
        restDataIdMockMvc.perform(delete("/api/data-ids/{id}", dataId.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DataId> dataIdList = dataIdRepository.findAll();
        assertThat(dataIdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataId.class);
        DataId dataId1 = new DataId();
        dataId1.setId(1L);
        DataId dataId2 = new DataId();
        dataId2.setId(dataId1.getId());
        assertThat(dataId1).isEqualTo(dataId2);
        dataId2.setId(2L);
        assertThat(dataId1).isNotEqualTo(dataId2);
        dataId1.setId(null);
        assertThat(dataId1).isNotEqualTo(dataId2);
    }
}
