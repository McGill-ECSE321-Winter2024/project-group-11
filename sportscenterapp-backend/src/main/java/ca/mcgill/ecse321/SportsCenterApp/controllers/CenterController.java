package ca.mcgill.ecse321.SportsCenterApp.controllers;

import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.SportsCenterApp.dto.CenterDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Center;
import ca.mcgill.ecse321.SportsCenterApp.services.CenterService;

import java.util.*;




@CrossOrigin(origins = "*")
@RestController
public class CenterController {

    @Autowired
    private CenterService service;




    @PostMapping("/center")
    public ResponseEntity<?> createCenter(@RequestBody CenterDto centerDto){

        try {
            Center center = service.createCenter(centerDto.getWeekSchedule(), centerDto.getWeekendSchedule(), centerDto.getAdress());
            return new ResponseEntity<>(convertToDto(center), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/center")
    public List<CenterDto> getAllCenter(){

        return service.getAllCenters().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
    }


    @GetMapping("/center/{id}")
    public ResponseEntity<?> getCenterById(@PathVariable("id") Integer id) {
        try {
            Center center = service.getCenter(id);
            return new ResponseEntity<>(convertToDto(center), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/center/{id}")
    public ResponseEntity<?> updateCenter(@PathVariable("id") Integer id, @RequestBody CenterDto centerDto) {
        try {
            Center center = service.updateCenter(id, centerDto.getWeekSchedule(), centerDto.getWeekendSchedule(), centerDto.getAdress());
            return new ResponseEntity<>(convertToDto(center), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/center/{id}")
    public ResponseEntity<?> deleteClassType(@PathVariable("id") Integer id) {
        try {
            service.deleteCenter(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    private CenterDto convertToDto(Center center){
        if (center == null) {
            throw new IllegalArgumentException("There is no such center");
        }
        CenterDto centerDto = new CenterDto(center.getId(), center.getWeekSchedule(), center.getweekendSchedule(), center.getAdress());
        return centerDto;
    }

}
    

