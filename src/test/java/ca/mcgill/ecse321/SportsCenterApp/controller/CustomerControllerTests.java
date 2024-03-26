package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;

import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    void testCreateAndGetCustomer() {
        List<String> parameters = testCreateCustomer();
        int id = Integer.parseInt(parameters.get(0));

        testGetCustomer(id);
        testGetAllCustomers();
    }



    private List<String> testCreateCustomer() {
        List<String> parameters = new ArrayList<>();

        CustomerDto customerDto = new CustomerDto("kenny", "nguyen", "kenner@email.com", "ken23", null, "aToken", 0.45f);
        CustomerDto customerDto2 = new CustomerDto("lee", "dee", "cee@email.com", "sup21!",null, "aToken", 135f);


        ResponseEntity<CustomerDto> response = client.postForEntity("/customer", customerDto, CustomerDto.class);
        client.postForEntity("/customer", customerDto2, CustomerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertTrue(response.hasBody());

        assertEquals(0.45f, response.getBody().getAccountBalance());
        assertTrue(response.getBody().getId() > 0);

        parameters.add(String.valueOf(response.getBody().getId()));
        parameters.add(response.getBody().getEmail());

        return parameters;

    }

    private void testGetCustomer(int id){

        ResponseEntity<CustomerDto> response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(0.45f, response.getBody().getAccountBalance());
    }


    private void testGetAllCustomers(){
        ResponseEntity<List<CustomerDto>> response = client.exchange("/customers", HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>() {
        });
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("kenner@email.com", response.getBody().get(0).getEmail());
        assertEquals("sup21!", response.getBody().get(1).getPassword());
    }


    @Test
    public void createInvalidCustomer(){
        CustomerDto customerDto = new CustomerDto("Spice", "Adams", null, "123@password", null, "atoken", 34f);

        ResponseEntity<String> response = client.postForEntity("/customer", customerDto, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email cannot be null", response.getBody());
    }
    @Test
    public void getInvalidCustomer(){
        ResponseEntity<String> response = client.getForEntity("/customer/" + 99, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Customer not found for id: 99", response.getBody());
    }




    @Test
    public void testUpdateCustomerAccountBalance(){
        CustomerDto customerDto = new CustomerDto("patty", "stluck", "patty@gmail.com", "123era", null, "aToken", 100f);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer", customerDto, CustomerDto.class);
        assertNotNull(response.getBody());
        Integer id = response.getBody().getId();

        float newBalance = 81.33f;


        String url = "/customer/" + id + "/update-balance?updated-balance=" + newBalance;
        ResponseEntity<CustomerDto> putResponse = client.exchange(url, HttpMethod.PUT, null, CustomerDto.class);

        assertNotNull(putResponse);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(putResponse.getBody());
        assertEquals("patty@gmail.com", putResponse.getBody().getEmail(), "The email should remain the same.");
        assertEquals(81.33f, putResponse.getBody().getAccountBalance(), "The account balance should be updated.");
    }

    @Test
    public void testDeleteCustomer(){
        CustomerDto customerDto = new CustomerDto("Antonio", "Brown", "CTESPN@yahoo.ca","cte123!",null, "aToken", 84.84f);

        CustomerDto customerDto2 = new CustomerDto("Tom", "Brady", "tb12@gmail.com", "tb12!", null, "aToken", 41.6f);

        ResponseEntity<CustomerDto> response = client.postForEntity("/customer", customerDto, CustomerDto.class);

        client.postForEntity("/customer", customerDto2, CustomerDto.class);
        ResponseEntity<List<CustomerDto>> listResponse = client.exchange("/customers", HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>() {
        });

        assertEquals(2, listResponse.getBody().size());

        int id = response.getBody().getId();

        String url = "/customer/" + id;

        ResponseEntity<String> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);


        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        listResponse = client.exchange("/customers", HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>() {
        });

        assertEquals(1, listResponse.getBody().size());
        assertEquals("tb12@gmail.com", listResponse.getBody().get(0).getEmail());


    }


}
