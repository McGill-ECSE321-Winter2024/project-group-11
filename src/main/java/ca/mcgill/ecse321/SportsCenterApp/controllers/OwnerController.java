package ca.mcgill.ecse321.SportsCenterApp.controllers;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Owner;

import ca.mcgill.ecse321.SportsCenterApp.dto.OwnerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.OwnerService;

@RestController
@RequestMapping("")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping(value = { "/owner" , "/owner/"})
    public ResponseEntity<?> createOwner(@RequestParam("aFirstName") String aFirstName, @RequestParam("aLastName")  String aLastName, @RequestParam("aEmail") String aEmail, @RequestParam("aPassword") String aPassword,
                                      @RequestParam("aToken") String aToken){
                                        try{
                                            Owner owner = ownerService.createOwner(aFirstName, aLastName, aEmail, aPassword, aToken);
                                            return new ResponseEntity<>(convertToDto(owner), HttpStatus.OK);
                                        }catch (Exception e){
                                            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                                        }
                                    }

    

    @GetMapping(value = { "/owner/{aId}", "/owner/{aId}/"})
    public ResponseEntity<?> getOwner(@PathVariable("aId") Integer aId){
        try{
            Owner owner = ownerService.getOwner(aId);
            return new ResponseEntity<>(convertToDto(owner), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = {"/owner/{aId}" , "/owner/{aId}/"})
    public void deleteOwner(@PathVariable("aId") Integer aId){
        try{
            ownerService.deleteOwner(aId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating owner", e);
        }
    }

    private OwnerDto convertToDto(Owner owner){
        if (owner == null){
            throw new IllegalArgumentException("There is no such owner");
        }
        OwnerDto ownerDto = new OwnerDto(owner.getFirstName(), owner.getLastName(), owner.getEmail(), owner.getPassword(), owner.getId(), owner.getToken());
        return ownerDto;

    }

}
