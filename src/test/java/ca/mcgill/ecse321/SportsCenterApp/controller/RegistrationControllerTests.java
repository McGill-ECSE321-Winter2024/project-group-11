package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.*;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Registration;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.RegistrationRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
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
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase() {
        registrationRepository.deleteAll();
        sessionRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void testCreateAndGetRegistration() {
        int id = testCreateRegistration();
        testGetRegistration(id);
        testGetAllRegistrations();
    }

    private int testCreateRegistration() {
        // Create and save the ClassType entity first
        ClassType classType = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Intermediate);
        ClassType savedClassType = classTypeRepository.save(classType);

        Session session = new Session(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()),
                new Time(System.currentTimeMillis() + 3600000), 25, 10, 100, savedClassType);

        Customer customer = new Customer("John", "Doe", "john.doe@example.com", "1234567890", "12345", 10);
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDto customerDto = new CustomerDto()
        RegistrationDto registrationDto = new RegistrationDto()


        // Post the registration and check the response
        ResponseEntity<RegistrationDto> response = client.postForEntity("/register/", registrationDto, RegistrationDto.class);

        // Assertions as previously written
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.hasBody());
        assertNotNull(response.getBody().getId());

        return response.getBody().getId();
    }



    private void testGetRegistration(int id) {
        ResponseEntity<RegistrationDto> response = client.getForEntity("/register/" + id, RegistrationDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        // assert other fields as necessary
        assertEquals(id, response.getBody().getId());
    }

    private void testGetAllRegistrations() {
        ResponseEntity<List<RegistrationDto>> response = client.exchange("/register/", HttpMethod.GET, null, new ParameterizedTypeReference<List<RegistrationDto>>() {});

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        // assert other fields as necessary
    }

}
