package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;

import ca.mcgill.ecse321.SportsCenterApp.dto.OwnerDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.OwnerRepository;
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
public class OwnerControllerTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase() {
        ownerRepository.deleteAll();
    }


    @Test
    void testCreateAndGetOwner() {
        List<String> parameters = testCreateOwner();
        int id = Integer.parseInt(parameters.get(0));
        testGetOwner(id);
        testGetAllOwners();
    }

    private List<String> testCreateOwner(){
        List<String> parameters = new ArrayList<>();

        OwnerDto ownerDto = new OwnerDto("owner", "boss", "boss@gmail.com", "bossman123", null, "aToken");
        OwnerDto ownerDto2 = new OwnerDto("rick", "ross", "rose@gmail.com", "huh21", null, "aToken");

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner", ownerDto, OwnerDto.class);
        client.postForEntity("/owner", ownerDto2, OwnerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertTrue(response.hasBody());

        assertEquals("boss@gmail.com",response.getBody().getEmail());
        assertTrue(response.getBody().getId()>0);

        parameters.add(String.valueOf(response.getBody().getId()));
        parameters.add(response.getBody().getEmail());

        return parameters;
    }


    private void testGetOwner(int id){

        ResponseEntity<OwnerDto> response = client.getForEntity("/owner/" + id, OwnerDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals("boss@gmail.com", response.getBody().getEmail());
    }

    private void testGetAllOwners(){
        ResponseEntity<List<OwnerDto>> response = client.exchange("/owners", HttpMethod.GET, null, new ParameterizedTypeReference<List<OwnerDto>>() {
        });
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("owner", response.getBody().get(0).getFirstName());
        assertEquals("rose@gmail.com", response.getBody().get(1).getEmail());

    }

    
    @Test
    public void testUpdateOwnerPassword(){
        OwnerDto ownerDto = new OwnerDto("Gary", "Bettman", "gary@nhl.com", "crosby87", null, "token1");

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner", ownerDto, OwnerDto.class);

        assertNotNull(response.getBody());
        Integer id = response.getBody().getId();

        String newPassword = "Geno71!Malkin";
        String url = "/owner/" + id + "/password?newPassword=" + newPassword;
        ResponseEntity<OwnerDto> putResponse = client.exchange(url, HttpMethod.PUT, null, OwnerDto.class);

        assertNotNull(putResponse);
        assertEquals(HttpStatus.OK, putResponse.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals("Gary", putResponse.getBody().getFirstName(), "The name is the same");
        assertEquals("Geno71!Malkin", putResponse.getBody().getPassword(), "the password has been changed");


    }







    @Test
    public void testDeleteOwner(){
        OwnerDto ownerDto = new OwnerDto("Roger", "Goodell", "commish@nfl.com","nfl123!",null, "aToken");
        OwnerDto ownerDto2 = new OwnerDto("Adam", "Silver", "commish@nba.com", "lebron123", null, "aToken");

        ResponseEntity<OwnerDto> response = client.postForEntity("/owner", ownerDto, OwnerDto.class);

        client.postForEntity("/owner", ownerDto2, CustomerDto.class);
        ResponseEntity<List<OwnerDto>> listResponse = client.exchange("/owners", HttpMethod.GET, null, new ParameterizedTypeReference<List<OwnerDto>>() {
        });

        assertEquals(2, listResponse.getBody().size());

        int id = response.getBody().getId();

        String url = "/owner/" + id;

        ResponseEntity<String> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        listResponse = client.exchange("/owners", HttpMethod.GET, null, new ParameterizedTypeReference<List<OwnerDto>>() {
        });

        assertEquals(1, listResponse.getBody().size());
        assertEquals("commish@nba.com", listResponse.getBody().get(0).getEmail());
    }

}
