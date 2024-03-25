package ca.mcgill.ecse321.SportsCenterApp.controller;




import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.InstructorDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InstructorControllerTest {


    @Autowired
    private TestRestTemplate client;

    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    void clearDatabase(){
        instructorRepository.deleteAll();
    }


    @Test
    public void testCreateAndGetInstructor(){

        List<String> parameters = testCreateInstructor();
        int id = Integer.parseInt(parameters.get(0));
        String mail = parameters.get(1);
        testGetInstructor(id);
        testGetInstructorByEmail(mail);
        testGetAllInstructors();

    }


    private List<String> testCreateInstructor(){

        List<String> parameters = new ArrayList<>();

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        InstructorDto instructorDto2 = new InstructorDto("Ronald", "Mcdonald", "mcdo@gmail.com", "P@ssword321", null, "token2", 10, "mcdonaldceo");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);
        
        client.postForEntity("/instructor", instructorDto2, InstructorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(),"Response has correct status" );

        assertTrue(response.hasBody(), "Response has body");

        assertEquals("Yazid", response.getBody().getFirstName(), "Response has correct name");
        assertTrue(response.getBody().getId() > 0, "Response has valid id");

        parameters.add(String.valueOf(response.getBody().getId()));
        parameters.add(response.getBody().getEmail());

        return parameters;
    }


    private void testGetInstructor(int id){

        ResponseEntity<InstructorDto> response = client.getForEntity("/instructors/" + id, InstructorDto.class);

        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Yazid", response.getBody().getFirstName(), "Response has correct name");
        assertTrue(response.getBody().getId() == id, "Response has correct ID");


    }

    private void testGetInstructorByEmail(String email){

        ResponseEntity<InstructorDto> response = client.getForEntity("/instructors/email/" + email, InstructorDto.class);

        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
        assertEquals("Yazid", response.getBody().getFirstName(), "Response has correct name");
		assertTrue(response.getBody().getEmail().equals(email), "Response has correct name");
    }


    private void testGetAllInstructors(){


        ResponseEntity<List<InstructorDto>> response = client.exchange("/instructors", HttpMethod.GET, null, new ParameterizedTypeReference<List<InstructorDto>>() {
        });

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("cool", response.getBody().get(0).getBiography());
        assertEquals("P@ssword321", response.getBody().get(1).getPassword());


    }


    @Test
    public void createInvalidInstructor1(){

        //passing an empty name
        InstructorDto instructorDto = new InstructorDto("", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        ResponseEntity<String> response = client.postForEntity("/instructor", instructorDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name fields cannot be empty", response.getBody());

    }


    @Test
    public void createInvalidInstructor2(){

        //passing an invalid email
        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail", "P@ssword123", null, "token1", 10, "cool");

        ResponseEntity<String> response = client.postForEntity("/instructor", instructorDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Email adress", response.getBody());

    }


    @Test
    public void createInvalidInstructor3(){

        //passing an invalid password
        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "Password123", null, "token1", 10, "cool");

        ResponseEntity<String> response = client.postForEntity("/instructor", instructorDto, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", response.getBody());

    }


    @Test
    public void getInvalidInstructor1(){
        ResponseEntity<String> response = client.getForEntity("/instructors/" + 99, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Instructor not found for id: 99", response.getBody());

    }


    @Test
    public void getInvalidInstructor2(){
        ResponseEntity<String> response = client.getForEntity("/instructors/email/nonexistent@gmail.com", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Instructor not found for email: nonexistent@gmail.com", response.getBody());

    }

    @Test
    public void updateInstructorPassword1(){

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);

        Integer id = response.getBody().getId();


        String newPassword = "P@ssword321";
        String url = "/instructors/" + id + "/password?newPassword=" + newPassword;
        ResponseEntity<InstructorDto> putresponse = client.exchange(url, HttpMethod.PUT, null, InstructorDto.class);

        assertNotNull(putresponse);
        assertEquals(HttpStatus.OK, putresponse.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals("Yazid", putresponse.getBody().getFirstName(), "The name is the same");
        assertEquals("P@ssword321", putresponse.getBody().getPassword(), "the password has been changed");

        //Check in database
        String requestUrl = "/instructors/" + id;
        ResponseEntity<InstructorDto> response2 = client.getForEntity(requestUrl, InstructorDto.class);
        assertEquals("Yazid", response2.getBody().getFirstName(), "The name is the same");
        assertEquals("P@ssword321", response2.getBody().getPassword(), "the password has been changed");



    }


    @Test
    public void updateInstructorPassword2(){

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);

        Integer id = response.getBody().getId();
        String email = response.getBody().getEmail();


        String newPassword = "P@ssword321";
        String url = "/instructors/email/" + email + "/password?newPassword=" + newPassword;
        ResponseEntity<InstructorDto> putresponse = client.exchange(url, HttpMethod.PUT, null, InstructorDto.class);

        assertNotNull(putresponse);
        assertEquals(HttpStatus.OK, putresponse.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals("Yazid", putresponse.getBody().getFirstName(), "The name is the same");
        assertEquals("P@ssword321", putresponse.getBody().getPassword(), "the password has been changed");

        //Check in database
        String requestUrl = "/instructors/" + id;
        ResponseEntity<InstructorDto> response2 = client.getForEntity(requestUrl, InstructorDto.class);
        assertEquals("Yazid", response2.getBody().getFirstName(), "The name is the same");
        assertEquals("P@ssword321", response2.getBody().getPassword(), "the password has been changed");


    }


    @Test
    public void updateInvalidInstructor1(){

        //Update with wrong password

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);

        Integer id = response.getBody().getId();


        String newPassword = "Pass";
        String url = "/instructors/" + id + "/password?newPassword=" + newPassword;
        ResponseEntity<String> putresponse = client.exchange(url, HttpMethod.PUT, null, String.class);

        assertNotNull(putresponse);
        assertEquals(HttpStatus.BAD_REQUEST, putresponse.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", putresponse.getBody());


    }

    @Test
    public void updateInvalidInstructor2(){

          //Update with invalid id

          InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

          ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);
  
          Integer id = response.getBody().getId() + 3;
  
  
          String newPassword = "P@ssword321";
          String url = "/instructors/" + id + "/password?newPassword=" + newPassword;
          ResponseEntity<String> putresponse = client.exchange(url, HttpMethod.PUT, null, String.class);
  
          assertNotNull(putresponse);
          assertEquals(HttpStatus.BAD_REQUEST, putresponse.getStatusCode());
          assertNotNull(response.getBody());
  
          assertEquals("Instructor not found for id: " + id, putresponse.getBody());
  



    }


    @Test
    public void testDeleteInstructor() {

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        InstructorDto instructorDto2 = new InstructorDto("Ronald", "Mcdonald", "mcdo@gmail.com", "P@ssword321", null, "token2", 10, "mcdonaldceo");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);
        
        client.postForEntity("/instructor", instructorDto2, InstructorDto.class);


        ResponseEntity<List<InstructorDto>> listresponse = client.exchange("/instructors", HttpMethod.GET, null, new ParameterizedTypeReference<List<InstructorDto>>() {
        });

        assertEquals(2, listresponse.getBody().size());

        int id = response.getBody().getId();

        String url = "/instructors/" + id;

        ResponseEntity<String> delResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(delResponse);
        assertEquals(HttpStatus.NO_CONTENT, delResponse.getStatusCode());


        listresponse = client.exchange("/instructors", HttpMethod.GET, null, new ParameterizedTypeReference<List<InstructorDto>>() {
        });

        assertEquals(1, listresponse.getBody().size());
        assertEquals("Ronald", listresponse.getBody().get(0).getFirstName());


    }



    @Test
    public void testDeleteInstructor2() {

        InstructorDto instructorDto = new InstructorDto("Yazid", "Asselah", "asselahyazid@gmail.com", "P@ssword123", null, "token1", 10, "cool");

        InstructorDto instructorDto2 = new InstructorDto("Ronald", "Mcdonald", "mcdo@gmail.com", "P@ssword321", null, "token2", 10, "mcdonaldceo");

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructor", instructorDto, InstructorDto.class);
        
        client.postForEntity("/instructor", instructorDto2, InstructorDto.class);


        ResponseEntity<List<InstructorDto>> listresponse = client.exchange("/instructors", HttpMethod.GET, null, new ParameterizedTypeReference<List<InstructorDto>>() {
        });

        assertEquals(2, listresponse.getBody().size());

        //Delete a non-existent instructor 
        int id = response.getBody().getId() + 3;

        String url = "/instructors/" + id;

        ResponseEntity<String> delResponse = client.exchange(url, HttpMethod.DELETE, null, String.class);

        assertNotNull(delResponse);
        assertEquals(HttpStatus.NO_CONTENT, delResponse.getStatusCode());


        listresponse = client.exchange("/instructors", HttpMethod.GET, null, new ParameterizedTypeReference<List<InstructorDto>>() {
        });

        assertEquals(2, listresponse.getBody().size());
        assertEquals("Yazid", listresponse.getBody().get(0).getFirstName());




    }












    
    
}
