package ca.mcgill.ecse321.SportsCenterApp.controllers;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;
import ca.mcgill.ecse321.SportsCenterApp.utilities.DtoConverter;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Owner;

import ca.mcgill.ecse321.SportsCenterApp.dto.OwnerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.OwnerService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping(value = { "/owner" , "/owner/"})
    public ResponseEntity<?> createOwner(@RequestBody OwnerDto ownerDto){
                                        try{
                                            Owner owner = ownerService.createOwner(ownerDto.getFirstName(), ownerDto.getLastName(), ownerDto.getEmail(), ownerDto.getPassword(), ownerDto.getToken());
                                            return new ResponseEntity<>(convertToDto(owner), HttpStatus.CREATED);
                                        }catch (Exception e){
                                            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                                        }
                                    }



    @GetMapping(value = { "/owner/{aId}", "/owner/{aId}/"})
    public ResponseEntity<?> getOwner(@PathVariable("aId") Integer aId){
        try{
            OwnerDto ownerDto = DtoConverter.convertToDto(ownerService.getOwner(aId));
            return new ResponseEntity<>(ownerDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/owners")
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
    }

    @PutMapping("/owner/{id}/password")
    public ResponseEntity<?> updateOwnerPassword (@PathVariable("id") Integer id, @RequestParam String newPassword){
        try{
            Owner owner = ownerService.updateOwnerPassword(id, newPassword);
            return new ResponseEntity<>(convertToDto(owner), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(value = {"/owner/{aId}" , "/owner/{aId}/"})
    public ResponseEntity<?> deleteOwner(@PathVariable("aId") Integer aId){
        try{
            ownerService.deleteOwner(aId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
