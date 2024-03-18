package ca.mcgill.ecse321.SportsCenterApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.SportsCenterApp.model.*;

import ca.mcgill.ecse321.SportsCenterApp.dto.RegistrationDto;

import ca.mcgill.ecse321.SportsCenterApp.services.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = {"/new-registration", "/new-registration/"})
    public RegistrationDto createRegistration(@RequestParam("aDate") Date aDate,@RequestParam("aTime") Time aTime,@RequestParam("aCustomer") Customer aCustomer,
                                                @RequestParam("aSession") Session aSession){
                                                    try{
                                                        Registration registration = registrationService.createRegistration(aDate, aTime, aCustomer, aSession);
                                                        return convertToDto(registration);
                                                    }catch (Exception e){
                                                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating registration", e);
                                                    }
                                                }
    
    @GetMapping(value = {"/{aId}", "/{aId}/"})
    public RegistrationDto getRegistration(@RequestParam("aId") Integer aId){
        try {
            Registration registration = registrationService.getRegistration(aId);
            return convertToDto(registration);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating registration", e);
        }
    }
    /**@GetMapping(value = {"/registrations", "/registrations/"})
    public RegistrationDto getAllRegistrations(){
        try {
            Iterable<Registration> registration = registrationService.getAllRegistrations(); //ITERABLE??
            return convertToDto(registration);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating registration", e);
        }
    }
    **/ //ITERABLE CAUSES ISSUES

    @DeleteMapping(value = {"/delete/{aId}", "/delete/{aId}/"})
    public void deleteRegistration(@RequestParam("aId") Integer aId){
        registrationService.deleteRegistration(aId);
    }

    //updateRegistrationBySession
    @PutMapping(value = {"/registration/{registrationId}/session/{sessionId}", "/registration/{registrationId}/session/{sessionId}/"})
    public RegistrationDto updateRegistrationBySession(@RequestParam("registrationId") Integer registrationId,@RequestParam("sessionId") Integer sessionId){
        try {
            Registration registration = registrationService.updateRegistrationBySession(registrationId, sessionId); 
            return convertToDto(registration);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating registration", e);
        }
    }

     //updateRegistrationByCustomer
    @PutMapping(value = {"/registration/{registrationId}/customer/{customerId}", "/registration/{registrationId}/customer/{customerId}/"})
    public RegistrationDto updateRegistrationByCustomer(@RequestParam("registrationId") Integer registrationId, @RequestParam("customerId") Integer customerId){
        try {
            Registration registration = registrationService.updateRegistrationByCustomer(registrationId, customerId);
            return convertToDto(registration);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating registration", e);
        }
    }

    private RegistrationDto convertToDto(Registration registration){
        if (registration == null){
            throw new IllegalArgumentException("There is no such registration");
        }
        RegistrationDto registrationDto = new RegistrationDto(registration.getDate(), registration.getTime(), registration.getCustomer(), registration.getSession());
        return registrationDto;
    }




}
