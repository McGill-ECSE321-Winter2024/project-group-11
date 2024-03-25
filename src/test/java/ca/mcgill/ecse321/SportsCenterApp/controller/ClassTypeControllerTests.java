package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.SessionDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.internal.Diff;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClassTypeControllerTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ClassTypeRepository classTypeRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase(){
        classTypeRepository.deleteAll();
    }


    @Test
    public void testCreateAndGetClassType(){
        List<String> parameters = testCreateClassType();
        int id = Integer.parseInt(parameters.get(0));
        String name = parameters.get(1);
        testGetClassType(id);
        testGetClassTypeByName(name);
        testGetAllClassType();
        testGetAllApprovedClassType();
    }



    private List<String> testCreateClassType(){



        List<String> parameters = new ArrayList<>();
        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, null);

        ClassTypeDto classTypeDto2 = new ClassTypeDto("esports", "valorant", false, ClassType.DifficultyLevel.Beginner, null);

        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);
        
        client.postForEntity("/classtype", classTypeDto2, ClassTypeDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(),"Response has correct status" );

        assertTrue(response.hasBody(), "Response has body");

        assertEquals("yoga", response.getBody().getName(), "Response has correct name");
        assertTrue(response.getBody().getId() > 0, "Response has valid ID");

        
        parameters.add(String.valueOf(response.getBody().getId()));
        parameters.add(response.getBody().getName());
        return parameters;


    }


    private void testGetClassType(int id){

        ResponseEntity<ClassTypeDto> response = client.getForEntity("/classtypes/" + id, ClassTypeDto.class);

        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertEquals("yoga", response.getBody().getName(), "Response has correct name");
		assertTrue(response.getBody().getId() == id, "Response has correct ID");

    }


    private void testGetClassTypeByName(String name){

        ResponseEntity<ClassTypeDto> response = client.getForEntity("/classtypes/name/" + name, ClassTypeDto.class);

        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
		assertTrue(response.getBody().getName().equals(name), "Response has correct name");



    }


    private void testGetAllClassType() {

        ResponseEntity<List<ClassTypeDto>> response = client.exchange("/classtypes", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(DifficultyLevel.Advanced, response.getBody().get(0).getDifficultyLevel());
        assertEquals("esports", response.getBody().get(1).getName());

    }



    private void testGetAllApprovedClassType() {

        ResponseEntity<List<ClassTypeDto>> response = client.exchange("/classtypes/approved", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(DifficultyLevel.Advanced, response.getBody().get(0).getDifficultyLevel());
        assertEquals("yoga", response.getBody().get(0).getName());

    }



    @Test
    public void createInvalidClassType1(){


        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "", true, ClassType.DifficultyLevel.Advanced, null);

        ResponseEntity<String> response = client.postForEntity("/classtype", classTypeDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("description cannot be empty!", response.getBody());

    }



    @Test
    public void createInvalidClassType2(){


        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, null, null);

        ResponseEntity<String> response = client.postForEntity("/classtype", classTypeDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Difficulty Level cannot be null!", response.getBody());
    }


    @Test
    public void getInvalidClassType1(){

        ResponseEntity<String> response = client.getForEntity("/classtypes/" + 99, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Class not found for id: 99", response.getBody());
    }


    @Test
    public void getInvalidClassType2(){

        ResponseEntity<String> response = client.getForEntity("/classtypes/name/nonexistent", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Class nonexistent not found", response.getBody());

    }


    @Test
    public void updateClassType1(){


        //Updating the description

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, DifficultyLevel.Advanced, null);



        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);

        Integer id = response.getBody().getId();

        ClassTypeDto updatedClassTypeDto = new ClassTypeDto(null, "really nice yoga", false, null, id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClassTypeDto> requestEntity = new HttpEntity<>(updatedClassTypeDto, headers);
        String url = "/classtypes/" + id;


        

        ResponseEntity<ClassTypeDto> putresponse = client.exchange(url, HttpMethod.PUT, requestEntity, ClassTypeDto.class);


        assertNotNull(putresponse);
        assertEquals(HttpStatus.OK, putresponse.getStatusCode());
        assertNotNull(response.getBody());

        
        assertEquals("yoga", putresponse.getBody().getName(), "The name is the same");
        assertEquals("really nice yoga", putresponse.getBody().getDescription(), "The description changed");
        assertEquals(true, putresponse.getBody().isApproved(), "The approved didnt change");

        //Check in database

        ResponseEntity<ClassTypeDto> response2 = client.getForEntity(url, ClassTypeDto.class);
        assertEquals("yoga", response2.getBody().getName(), "The name is the same in database");
        assertEquals("really nice yoga", response2.getBody().getDescription(), "The description changed in database");
        assertEquals(true, response2.getBody().isApproved(), "The approved didnt change in database");


    }



    @Test
    public void updateClassType2(){


        //Updating the name and difficulty level

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, DifficultyLevel.Advanced, null);



        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);

        Integer id = response.getBody().getId();

        ClassTypeDto updatedClassTypeDto = new ClassTypeDto("Yoga yogourt", null, false, DifficultyLevel.Beginner, id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClassTypeDto> requestEntity = new HttpEntity<>(updatedClassTypeDto, headers);
        String url = "/classtypes/" + id;


        

        response = client.exchange(url, HttpMethod.PUT, requestEntity, ClassTypeDto.class);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        
        assertEquals("Yoga yogourt", response.getBody().getName(), "The name successfully changed");
        assertEquals("nice yoga", response.getBody().getDescription(), "The description is same");
        assertEquals(true, response.getBody().isApproved(), "The approved didnt change");
        assertEquals(DifficultyLevel.Beginner, response.getBody().getDifficultyLevel());

    }





    @Test
    public void updateInvalidClassType1(){

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, DifficultyLevel.Advanced, null);



        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);

        Integer id = response.getBody().getId();

        //Provide a new name and an empty description
        ClassTypeDto updatedClassTypeDto = new ClassTypeDto("Yoga yogourt", "", false, DifficultyLevel.Beginner, id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClassTypeDto> requestEntity = new HttpEntity<>(updatedClassTypeDto, headers);
        String url = "/classtypes/" + id;

        

        ResponseEntity<String> badresponse = client.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertNotNull(badresponse);
        assertEquals(HttpStatus.BAD_REQUEST, badresponse.getStatusCode());
        assertEquals("description cannot be empty!", badresponse.getBody());

        //Since its a bad request, nothing should have changed in the database

        ResponseEntity<ClassTypeDto> response2 = client.getForEntity(url, ClassTypeDto.class);
        assertEquals("yoga", response2.getBody().getName(), "The name is the same in database");
        assertEquals("nice yoga", response2.getBody().getDescription(), "The description is the same in database");
        assertEquals(true, response2.getBody().isApproved(), "The approved didnt change in database");


        


    }


    @Test
    public void updateInvalidClassType2(){

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, DifficultyLevel.Advanced, null);



        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);

        Integer id = response.getBody().getId() + 3; //Provide a wrong id

        //Provide a new name
        ClassTypeDto updatedClassTypeDto = new ClassTypeDto("Yoga yogourt", null, false, null, id);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ClassTypeDto> requestEntity = new HttpEntity<>(updatedClassTypeDto, headers);
        String url = "/classtypes/" + id;

        

        ResponseEntity<String> badresponse = client.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertNotNull(badresponse);
        assertEquals(HttpStatus.BAD_REQUEST, badresponse.getStatusCode());
        assertEquals("ClassType not found for id: " + id, badresponse.getBody());

    }


    @Test
    public void testApproveDisaprove(){

        //Note: Approve and Disaprove methods work similar to update, so im not testing for errors i.e passing a wrong id

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "nice yoga", true, DifficultyLevel.Advanced, null);

        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);

        Integer id = response.getBody().getId();

        HttpEntity<ClassTypeDto> requestEntity = new HttpEntity<>(classTypeDto);
        String url = "/classtypes/" + id + "/disapprove";

        response = client.exchange(url, HttpMethod.PUT, requestEntity, ClassTypeDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        assertEquals(false, response.getBody().isApproved());
        assertEquals("yoga", response.getBody().getName());


        url = "/classtypes/" + id + "/approve";


        response = client.exchange(url, HttpMethod.PUT, requestEntity, ClassTypeDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        assertEquals(true, response.getBody().isApproved());
        assertEquals("yoga", response.getBody().getName());


    }



    @Test
    public void testDeleteClassType(){

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, null);

        ClassTypeDto classTypeDto2 = new ClassTypeDto("esports", "valorant", false, ClassType.DifficultyLevel.Beginner, null);

        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);
        
        client.postForEntity("/classtype", classTypeDto2, ClassTypeDto.class);


        ResponseEntity<List<ClassTypeDto>> listresponse = client.exchange("/classtypes", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        
        assertEquals(2, listresponse.getBody().size());


        int id = response.getBody().getId();

        String url = "/classtype/" + id;


        ResponseEntity<String> delresponse = client.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(delresponse);
        assertEquals(HttpStatus.NO_CONTENT, delresponse.getStatusCode());


        listresponse = client.exchange("/classtypes", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        
        assertEquals(1, listresponse.getBody().size());
        assertEquals("esports", listresponse.getBody().get(0).getName());


    }





    @Test
    public void testDeleteClassType2(){

        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, null);

        ClassTypeDto classTypeDto2 = new ClassTypeDto("esports", "valorant", false, ClassType.DifficultyLevel.Beginner, null);

        ResponseEntity<ClassTypeDto> response = client.postForEntity("/classtype", classTypeDto, ClassTypeDto.class);
        
        client.postForEntity("/classtype", classTypeDto2, ClassTypeDto.class);


        ResponseEntity<List<ClassTypeDto>> listresponse = client.exchange("/classtypes", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        
        assertEquals(2, listresponse.getBody().size());

        //Provide wrong id
        int id = response.getBody().getId() + 3;

        
        String url = "/classtype/" + id;


        ResponseEntity<String> delresponse = client.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(delresponse);
        assertEquals(HttpStatus.NO_CONTENT, delresponse.getStatusCode());


        listresponse = client.exchange("/classtypes", HttpMethod.GET, null, new ParameterizedTypeReference<List<ClassTypeDto>>() {
        });

        
        assertEquals(2, listresponse.getBody().size());
        assertEquals("yoga", listresponse.getBody().get(0).getName());


    }




    
}
