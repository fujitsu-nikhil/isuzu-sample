package com.fujitsu.isuzu.web.rest;

import com.fujitsu.isuzu.IsuzuSampleApplicationApp;
import com.fujitsu.isuzu.domain.Vehicle;
import com.fujitsu.isuzu.repository.VehicleRepository;
import com.fujitsu.isuzu.service.VehicleService;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.fujitsu.isuzu.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link VehicleResource} REST controller.
 */
@SpringBootTest(classes = IsuzuSampleApplicationApp.class)
public class VehicleResourceIT {

    private static final String DEFAULT_VEHICLE_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OVERALL_JUDGMENT = "AAAAAAAAAA";
    private static final String UPDATED_OVERALL_JUDGMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_OVERALL_JUDGMENT_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OVERALL_JUDGMENT_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODEL_YEAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODEL_YEAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MODEL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ESTIMATED_PRODUCTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_PRODUCTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

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

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleResource vehicleResource = new VehicleResource(vehicleService);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
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
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .vehicleIdNumber(DEFAULT_VEHICLE_ID_NUMBER)
            .overallJudgment(DEFAULT_OVERALL_JUDGMENT)
            .overallJudgmentAt(DEFAULT_OVERALL_JUDGMENT_AT)
            .modelYear(DEFAULT_MODEL_YEAR)
            .modelCode(DEFAULT_MODEL_CODE)
            .lotNumber(DEFAULT_LOT_NUMBER)
            .unitNumber(DEFAULT_UNIT_NUMBER)
            .estimatedProductionDate(DEFAULT_ESTIMATED_PRODUCTION_DATE)
            .updatedAt(DEFAULT_UPDATED_AT);
        return vehicle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createUpdatedEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .overallJudgment(UPDATED_OVERALL_JUDGMENT)
            .overallJudgmentAt(UPDATED_OVERALL_JUDGMENT_AT)
            .modelYear(UPDATED_MODEL_YEAR)
            .modelCode(UPDATED_MODEL_CODE)
            .lotNumber(UPDATED_LOT_NUMBER)
            .unitNumber(UPDATED_UNIT_NUMBER)
            .estimatedProductionDate(UPDATED_ESTIMATED_PRODUCTION_DATE)
            .updatedAt(UPDATED_UPDATED_AT);
        return vehicle;
    }

    @BeforeEach
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getVehicleIdNumber()).isEqualTo(DEFAULT_VEHICLE_ID_NUMBER);
        assertThat(testVehicle.getOverallJudgment()).isEqualTo(DEFAULT_OVERALL_JUDGMENT);
        assertThat(testVehicle.getOverallJudgmentAt()).isEqualTo(DEFAULT_OVERALL_JUDGMENT_AT);
        assertThat(testVehicle.getModelYear()).isEqualTo(DEFAULT_MODEL_YEAR);
        assertThat(testVehicle.getModelCode()).isEqualTo(DEFAULT_MODEL_CODE);
        assertThat(testVehicle.getLotNumber()).isEqualTo(DEFAULT_LOT_NUMBER);
        assertThat(testVehicle.getUnitNumber()).isEqualTo(DEFAULT_UNIT_NUMBER);
        assertThat(testVehicle.getEstimatedProductionDate()).isEqualTo(DEFAULT_ESTIMATED_PRODUCTION_DATE);
        assertThat(testVehicle.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVehicleIdNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setVehicleIdNumber(null);

        // Create the Vehicle, which fails.

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleIdNumber").value(hasItem(DEFAULT_VEHICLE_ID_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].overallJudgment").value(hasItem(DEFAULT_OVERALL_JUDGMENT.toString())))
            .andExpect(jsonPath("$.[*].overallJudgmentAt").value(hasItem(DEFAULT_OVERALL_JUDGMENT_AT.toString())))
            .andExpect(jsonPath("$.[*].modelYear").value(hasItem(DEFAULT_MODEL_YEAR.toString())))
            .andExpect(jsonPath("$.[*].modelCode").value(hasItem(DEFAULT_MODEL_CODE.toString())))
            .andExpect(jsonPath("$.[*].lotNumber").value(hasItem(DEFAULT_LOT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].unitNumber").value(hasItem(DEFAULT_UNIT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].estimatedProductionDate").value(hasItem(DEFAULT_ESTIMATED_PRODUCTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.vehicleIdNumber").value(DEFAULT_VEHICLE_ID_NUMBER.toString()))
            .andExpect(jsonPath("$.overallJudgment").value(DEFAULT_OVERALL_JUDGMENT.toString()))
            .andExpect(jsonPath("$.overallJudgmentAt").value(DEFAULT_OVERALL_JUDGMENT_AT.toString()))
            .andExpect(jsonPath("$.modelYear").value(DEFAULT_MODEL_YEAR.toString()))
            .andExpect(jsonPath("$.modelCode").value(DEFAULT_MODEL_CODE.toString()))
            .andExpect(jsonPath("$.lotNumber").value(DEFAULT_LOT_NUMBER.toString()))
            .andExpect(jsonPath("$.unitNumber").value(DEFAULT_UNIT_NUMBER.toString()))
            .andExpect(jsonPath("$.estimatedProductionDate").value(DEFAULT_ESTIMATED_PRODUCTION_DATE.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleService.save(vehicle);

        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findById(vehicle.getId()).get();
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .vehicleIdNumber(UPDATED_VEHICLE_ID_NUMBER)
            .overallJudgment(UPDATED_OVERALL_JUDGMENT)
            .overallJudgmentAt(UPDATED_OVERALL_JUDGMENT_AT)
            .modelYear(UPDATED_MODEL_YEAR)
            .modelCode(UPDATED_MODEL_CODE)
            .lotNumber(UPDATED_LOT_NUMBER)
            .unitNumber(UPDATED_UNIT_NUMBER)
            .estimatedProductionDate(UPDATED_ESTIMATED_PRODUCTION_DATE)
            .updatedAt(UPDATED_UPDATED_AT);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicle)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getVehicleIdNumber()).isEqualTo(UPDATED_VEHICLE_ID_NUMBER);
        assertThat(testVehicle.getOverallJudgment()).isEqualTo(UPDATED_OVERALL_JUDGMENT);
        assertThat(testVehicle.getOverallJudgmentAt()).isEqualTo(UPDATED_OVERALL_JUDGMENT_AT);
        assertThat(testVehicle.getModelYear()).isEqualTo(UPDATED_MODEL_YEAR);
        assertThat(testVehicle.getModelCode()).isEqualTo(UPDATED_MODEL_CODE);
        assertThat(testVehicle.getLotNumber()).isEqualTo(UPDATED_LOT_NUMBER);
        assertThat(testVehicle.getUnitNumber()).isEqualTo(UPDATED_UNIT_NUMBER);
        assertThat(testVehicle.getEstimatedProductionDate()).isEqualTo(UPDATED_ESTIMATED_PRODUCTION_DATE);
        assertThat(testVehicle.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Create the Vehicle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicle)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleService.save(vehicle);

        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Delete the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicle.class);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(vehicle1.getId());
        assertThat(vehicle1).isEqualTo(vehicle2);
        vehicle2.setId(2L);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
        vehicle1.setId(null);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
    }
}
