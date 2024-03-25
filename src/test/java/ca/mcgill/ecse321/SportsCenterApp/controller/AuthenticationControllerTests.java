package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.LoginDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import ca.mcgill.ecse321.SportsCenterApp.repository.OwnerRepository;
import ca.mcgill.ecse321.SportsCenterApp.services.utilities.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@test.com");
        loginDto.setPassword("password");
        loginDto.setUserType(UserType.Owner);

        Owner owner = new Owner("Bob", "Bobby", "test@test.com", "password", "token1");
        Owner savedOwner = ownerRepository.save(owner);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@test2.com");
        loginDto.setPassword("password2");
        loginDto.setUserType(UserType.Owner);

        Owner owner = new Owner("Bob", "Bobby", "notgood@gmail.com", "password", "token1");
        Owner savedOwner = ownerRepository.save(owner);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid User Type or invalid Email.", response.getBody());
    }

    @Test
    public void testLogout() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setUserType(UserType.Owner);

        Owner owner = new Owner("Bob", "Bobby", "test@gmail.com", "password", "token1");
        Owner savedOwner = ownerRepository.save(owner);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/logout", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testInvalidLogout() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@gmail.com");
        loginDto.setUserType(UserType.Owner);

        Owner owner = new Owner("Bob", "Bobby", "ok@gmail.com", "password", "token1");
        Owner savedOwner = ownerRepository.save(owner);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/logout", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
