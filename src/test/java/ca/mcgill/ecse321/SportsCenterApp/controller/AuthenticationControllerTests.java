package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.LoginDto;
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

    @BeforeEach
    public void setup() {
        // setup code here
    }

    @Test
    public void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@test.com");
        loginDto.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/login", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogout() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@test.com");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDto> entity = new HttpEntity<>(loginDto, headers);

        ResponseEntity<String> response = client.exchange("/authentication/logout", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
