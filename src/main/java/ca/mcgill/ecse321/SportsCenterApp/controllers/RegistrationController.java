package ca.mcgill.ecse321.SportsCenterApp.controllers;

import ca.mcgill.ecse321.SportsCenterApp.dto.RegistrationDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Registration;
import ca.mcgill.ecse321.SportsCenterApp.services.RegistrationService;
import ca.mcgill.ecse321.SportsCenterApp.utilities.DtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegistration(@PathVariable("id") Integer id) {
        try {
            Registration registration = registrationService.getRegistration(id);
            RegistrationDto dto = DtoConverter.convertToDto(registration);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRegistrations() {
        try {
            Iterable<Registration> registrations = registrationService.getAllRegistrations();
            List<RegistrationDto> res = DtoConverter.convertRegistrationsToDtos(registrations);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createRegistration(@RequestBody RegistrationDto registrationDto) {
        try {
            Registration registration = registrationService.createRegistration(registrationDto.getDate(), registrationDto.getTime(), registrationDto.getCustomer().getId(), registrationDto.getSession().getId());
            RegistrationDto dto = DtoConverter.convertToDto(registration);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable("id") Integer id) {
        try {
            registrationService.deleteRegistration(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
