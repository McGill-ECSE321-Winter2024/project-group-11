package ca.mcgill.ecse321.SportsCenterApp.controllers;

import ca.mcgill.ecse321.SportsCenterApp.dto.LoginDto;
import ca.mcgill.ecse321.SportsCenterApp.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.AuthenticationException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    /**
     * Log in a user
     * @param credentials LoginDto
     * @return login token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto credentials) {
        try {
            String token = authenticationService.logIn(credentials);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                return new ResponseEntity<>("Invalid Password.", HttpStatus.BAD_REQUEST); //Invalid password
            }
            return new ResponseEntity<>("Invalid User Type or invalid Email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Log out a user
     * @param credentials LoginDto ... password is ignored
     * @return response with message or error
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LoginDto credentials) {
        try {
            authenticationService.logout(credentials);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
