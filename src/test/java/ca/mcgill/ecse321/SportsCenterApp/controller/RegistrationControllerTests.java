package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.*;
import ca.mcgill.ecse321.SportsCenterApp.model.*;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import ca.mcgill.ecse321.SportsCenterApp.utilities.DtoConverter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase() {
        registrationRepository.deleteAll();
    }

    @Test
    void testCreateAndGetRegistration() {
        int id = testCreateRegistration();
        testGetRegistration(id);
        testGetAllRegistrations();
    }

    private int testCreateRegistration() {

        Instructor instructor = new Instructor("Jeff", "Winger", "jeff@email.com",
                "password", "aToken", 3, "biography");
        Instructor savedInstructor = instructorRepository.save(instructor);
        InstructorDto instructorDto = DtoConverter.convertToDto(savedInstructor);

        ClassType classType = new ClassType("Yoga", "description", true, ClassType.DifficultyLevel.Intermediate);
        ClassType savedClassType = classTypeRepository.save(classType);
        ClassTypeDto classTypeDto = DtoConverter.convertToDto(savedClassType);

        Customer customer = new Customer("Bob", "Doe", "bob@email.com", "password", "aToken", 10);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto customerDto = DtoConverter.convertToDto(savedCustomer);

        Session session = new Session(new Date(1000), new Time(50), new Time(90), 5, 5,
                5, instructor, classType);
        Session savedSession = sessionRepository.save(session);
        SessionDto sessionDto = DtoConverter.convertToDto(savedSession);

        Time time = new Time(100);
        Date date = new Date(1000);
        RegistrationDto registrationDto = new RegistrationDto(date, time, customerDto, sessionDto);

        ResponseEntity<RegistrationDto> response = client.postForEntity("/register", registrationDto, RegistrationDto.class);

        assertNotNull(response);
        assertTrue(response.hasBody());
        assertNotNull(response.getBody().getTime());
        assertNotNull(response.getBody().getId());

        return response.getBody().getId();
    }

    private void testGetRegistration(int id) {
        ResponseEntity<RegistrationDto> response = client.getForEntity("/register/" + id, RegistrationDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(id, response.getBody().getId());
    }

    private void testGetAllRegistrations() {
        ResponseEntity<List<RegistrationDto>> response = client.exchange("/register", HttpMethod.GET, null, new ParameterizedTypeReference<List<RegistrationDto>>() {});
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCreateAndGetRegistrationInvalid() {
        Instructor instructor = new Instructor("Jeff", "Winger", "jeff@email.com",
                "password", "aToken", 3, "biography");
        Instructor savedInstructor = instructorRepository.save(instructor);
        InstructorDto instructorDto = DtoConverter.convertToDto(savedInstructor);

        ClassType classType = new ClassType("Yoga", "description", true, ClassType.DifficultyLevel.Intermediate);
        ClassType savedClassType = classTypeRepository.save(classType);
        ClassTypeDto classTypeDto = DtoConverter.convertToDto(savedClassType);


        CustomerDto customerDto = new CustomerDto("Bob", "Doe", "bob@email.com", "password", 99,"aToken", 10);

        Session session = new Session(new Date(1000), new Time(50), new Time(90), 5, 5,
                5, instructor, classType);
        Session savedSession = sessionRepository.save(session);
        SessionDto sessionDto = DtoConverter.convertToDto(savedSession);

        Time time = new Time(100);
        Date date = new Date(1000);
        RegistrationDto registrationDto = new RegistrationDto(date, time, customerDto, sessionDto);

        // Post the registration and check the response
        ResponseEntity<String> response = client.postForEntity("/register", registrationDto, String.class);

        // Assertions as previously written
        assertNotNull(response);
        assertTrue(response.hasBody());
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Could not find customer", response.getBody());
    }

    @Test
    void testDeleteRegistration(){
        int id = testCreateRegistration();
        testGetRegistration(id);
        testDeleteSpecificRegistration(id);
    }

    private void testDeleteSpecificRegistration(int id) {
        String url = "/register/" + id;
        ResponseEntity<String> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);
        assertNotNull(deleteResponse);

        ResponseEntity<List<RegistrationDto>> response = client.exchange("/register", HttpMethod.GET, null, new ParameterizedTypeReference<List<RegistrationDto>>() {});
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testDeleteRegistrationInvalid(){
        int id = testCreateRegistration();
        testGetRegistration(id);
        testDeleteSpecificRegistrationInvalid(99);
    }

    private void testDeleteSpecificRegistrationInvalid(int id) {
        String url = "/register/" + id;
        ResponseEntity<String> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);
        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals("Could not find registration with id: 99", deleteResponse.getBody());
    }
}
