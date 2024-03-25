package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.SessionDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @BeforeEach
    @AfterEach
    void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    void testCreateAndGetCustomer() {
        int id = testCreateCustomer();
        testGetCustomer(id);
    }

    private int testCreateCustomer() {
        Customer customer = new Customer("kenny", "nguyen","kenner", "ken23", "aToken", 0.45f);

        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto customerDto = new CustomerDto("kenny", "nguyen", "kenner", "ken23", savedCustomer.getId(), "aToken", 0.45f);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer", customerDto, CustomerDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertTrue(response.hasBody());
        assertEquals(0.45f, response.getBody().getAccountBalance());
        assertTrue(response.getBody().getId() > 0);

        return response.getBody().getId();

    }
    private void testGetCustomer(int id){

        ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(0.45f, response.getBody().getAccountBalance());
    }
}
