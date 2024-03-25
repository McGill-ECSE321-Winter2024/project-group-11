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
        // Create and save the ClassType entity first

        InstructorDto instructorDto = new InstructorDto("Jeff", "Winger","jeff@email.com",
                "password", null, "aToken", 3, "biography");
        ClassTypeDto classTypeDto = new ClassTypeDto("Yoga", "description", true, ClassType.DifficultyLevel.Intermediate, null);
        CustomerDto customerDto = new CustomerDto("Bob", "Doe", "bob@email.com", "password", null, "aToken", 10);
        SessionDto sessionDto = new SessionDto(new Date(1000), new Time(50), new Time(90), 5, 5,
                5, classTypeDto, instructorDto);

        RegistrationDto registrationDto = new RegistrationDto(new Date(1000), new Time(100), customerDto, sessionDto);

        InstructorDto instructorDto2 = new InstructorDto("Troy", "Barnes","troy@email.com",
                "password", null, "aToken", 3, "biography");
        ClassTypeDto classTypeDto2 = new ClassTypeDto("Weights", "description", true, ClassType.DifficultyLevel.Intermediate, null);
        CustomerDto customerDto2 = new CustomerDto("Annie", "Edison", "annie@email.com", "password", null, "aToken", 10);
        SessionDto sessionDto2 = new SessionDto(new Date(400), new Time(40), new Time(80), 5, 5,
                5, classTypeDto2, instructorDto2);

        RegistrationDto registrationDto2 = new RegistrationDto(new Date(100), new Time(10), customerDto2, sessionDto2);

        // Post the registration and check the response
        ResponseEntity<RegistrationDto> response = client.postForEntity("/register", registrationDto, RegistrationDto.class);

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
