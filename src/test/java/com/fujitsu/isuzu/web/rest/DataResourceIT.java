package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.Data;
import com.fujitsu.isuzu.repository.DataRepository;
import com.fujitsu.isuzu.service.DataService;
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
 * Integration tests for the {@Link DataResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class DataResourceIT {

    private static final String DEFAULT_VEHICLE_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_ID = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataService dataService;

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

    private MockMvc restDataMockMvc;

    private Data data;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataResource dataResource = new DataResource(dataService);
        this.restDataMockMvc = MockMvcBuilders.standaloneSetup(dataResource)
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
    public static Data createEntity(EntityManager em) {
        Data data = new Data()
            .vehicleIdNumber(DEFAULT_VEHICLE_ID_NUMBER)
            .dataId(DEFAULT_DATA_ID)
            .detail(DEFAULT_DETAIL)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return data;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Data createUpdatedEntity(EntityManager em) {
        Data data = new Data()
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .dataId(UPDATED_DATA_ID)
            .detail(UPDATED_DETAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return data;
    }

    @BeforeEach
    public void initTest() {
        data = createEntity(em);
    }

    @Test
    @Transactional
    public void createData() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // Create the Data
        restDataMockMvc.perform(post("/api/data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isCreated());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate + 1);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getVehicleIdNumber()).isEqualTo(DEFAULT_VEHICLE_ID_NUMBER);
        assertThat(testData.getDataId()).isEqualTo(DEFAULT_DATA_ID);
        assertThat(testData.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testData.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testData.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataRepository.findAll().size();

        // Create the Data with an existing ID
        data.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataMockMvc.perform(post("/api/data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get all the dataList
        restDataMockMvc.perform(get("/api/data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(data.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleIdNumber").value(hasItem(DEFAULT_VEHICLE_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dataId").value(hasItem(DEFAULT_DATA_ID.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getData() throws Exception {
        // Initialize the database
        dataRepository.saveAndFlush(data);

        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(data.getId().intValue()))
            .andExpect(jsonPath("$.vehicleIdNumber").value(DEFAULT_VEHICLE_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.dataId").value(DEFAULT_DATA_ID.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingData() throws Exception {
        // Get the data
        restDataMockMvc.perform(get("/api/data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateData() throws Exception {
        // Initialize the database
        dataService.save(data);

        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Update the data
        Data updatedData = dataRepository.findById(data.getId()).get();
        // Disconnect from session so that the updates on updatedData are not directly saved in db
        em.detach(updatedData);
        updatedData
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .dataId(UPDATED_DATA_ID)
            .detail(UPDATED_DETAIL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restDataMockMvc.perform(put("/api/data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedData)))
            .andExpect(status().isOk());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
        Data testData = dataList.get(dataList.size() - 1);
        assertThat(testData.getVehicleIdNumber()).isEqualTo(UPDATED_VEHICLE_ID_NUMBER);
        assertThat(testData.getDataId()).isEqualTo(UPDATED_DATA_ID);
        assertThat(testData.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testData.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testData.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingData() throws Exception {
        int databaseSizeBeforeUpdate = dataRepository.findAll().size();

        // Create the Data

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataMockMvc.perform(put("/api/data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(data)))
            .andExpect(status().isBadRequest());

        // Validate the Data in the database
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteData() throws Exception {
        // Initialize the database
        dataService.save(data);

        int databaseSizeBeforeDelete = dataRepository.findAll().size();

        // Delete the data
        restDataMockMvc.perform(delete("/api/data/{id}", data.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Data> dataList = dataRepository.findAll();
        assertThat(dataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Data.class);
        Data data1 = new Data();
        data1.setId(1L);
        Data data2 = new Data();
        data2.setId(data1.getId());
        assertThat(data1).isEqualTo(data2);
        data2.setId(2L);
        assertThat(data1).isNotEqualTo(data2);
        data1.setId(null);
        assertThat(data1).isNotEqualTo(data2);
    }
}
