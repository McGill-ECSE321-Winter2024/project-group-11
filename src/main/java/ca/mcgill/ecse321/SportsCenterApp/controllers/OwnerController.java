package ca.mcgill.ecse321.SportsCenterApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Owner;

import ca.mcgill.ecse321.SportsCenterApp.dto.OwnerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.OwnerService;

@RestController
@RequestMapping("/user")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping(value = { "/owner/{aId}" , "/owner/{aId}/"})
    public OwnerDto createOwner(@RequestParam("aFirstName") String aFirstName, @RequestParam("aLastName")  String aLastName, @RequestParam("aEmail") String aEmail,@RequestParam("aPassword") String aPassword,
                                    @RequestParam("aId") Integer aId, @RequestParam("aToken") String aToken){
                                        try{
                                            Owner owner = ownerService.createOwner(aFirstName, aLastName, aEmail, aPassword, aId, aToken);
                                            return convertToDto(owner);
                                        }catch (Exception e){
                                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating owner", e);
                                        }
                                    }

    

    @GetMapping(value = { "/owner/{aId}", "/owner/{aId}/"})
    public OwnerDto getOwner(@PathVariable("aId") Integer aId){
        try{
            Owner owner = ownerService.getOwner(aId);
            return convertToDto(owner);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating owner", e);
        }
    }

    @DeleteMapping(value = {"/owner/delete/{aId}" , "/owner/delete/{aId}/"})
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