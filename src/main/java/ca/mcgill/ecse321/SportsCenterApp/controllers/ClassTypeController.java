package ca.mcgill.ecse321.SportsCenterApp.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.services.ClassTypeService;

import java.util.*;



@CrossOrigin(origins = "*")
@RestController
public class ClassTypeController {

    @Autowired
    private ClassTypeService service;

    @PostMapping("/classtype")
    public ResponseEntity<?> createClassType(@RequestBody ClassTypeDto classTypeDto) {
        try {
            ClassType classType = service.creatClassType(classTypeDto.getName(), classTypeDto.getDescription(), classTypeDto.isApproved(), classTypeDto.getDifficultyLevel());
            return new ResponseEntity<>(convertToDto(classType), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GET request to retrieve all ClassTypes
     * @return class type dto
     */
    @GetMapping("/classtypes")
    public List<ClassTypeDto> getAllClassTypes(){

        return service.getAllClassTypes().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
    }


    /**
     * GET request to retrieve all approved ClassTypes
     * @return class type dto
     */
    @GetMapping("/classtypes/approved")
    public List<ClassTypeDto> getAllApprovedClassTypes(){

        return service.getAllApprovedClassTypes().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
    }



     /**
     * GET request to retrieve a ClassType by Name
     * @param name (String) Name of the ClassType
     * @return class type dto
     */
    @GetMapping("/classtypes/name/{name}")
    public ResponseEntity<?> getClassTypeByName(@PathVariable String name){
        try {
            ClassType classType = service.getClassType(name);
            return new ResponseEntity<>(convertToDto(classType), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * GET request to retrieve a ClassType by ID
     * @param id (Integer) ID of the ClassType
     * @return class type dto
     */
    @GetMapping("/classtypes/{id}")
    public ResponseEntity<?> getClassTypeById(@PathVariable Integer id) {
        try {
            ClassType classType = service.getClassType(id);
            return new ResponseEntity<>(convertToDto(classType), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


     /**
     * PUT request to update a ClassType
     * @param id (Integer) ID of the ClassType to update
     * @param classTypeDto (ClassTypeDto) Updated ClassType data
     * @return updated class type dto
     */
    @PutMapping("/classtypes/{id}")
    public ResponseEntity<?> updateClassType(@PathVariable Integer id, @RequestBody ClassTypeDto classTypeDto) {
        try {
            ClassType classType = service.updateClassType(id, classTypeDto.getName(), classTypeDto.getDescription(), classTypeDto.getDifficultyLevel());
            return new ResponseEntity<>(convertToDto(classType), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


     /**
     * PUT request to approve a ClassType
     * @param id (Integer) ID of the ClassType to approve
     * @return approved class type dto
     */
    @PutMapping("/classtypes/{id}/approve")
    public ResponseEntity<?> approveClassType(@PathVariable Integer id) {
        try {
            ClassType approvedClassType = service.approveClassType(id);
            return new ResponseEntity<>(convertToDto(approvedClassType), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * PUT request to disapprove a ClassType
     * @param id (Integer) ID of the ClassType to disapprove
     * @return disapproved class type dto
     */
    @PutMapping("/classtypes/{id}/disapprove")
    public ResponseEntity<?> disapproveClassType(@PathVariable Integer id) {
        try {
            ClassType disapprovedClassType = service.disapproveClassType(id);
            return new ResponseEntity<>(convertToDto(disapprovedClassType), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE request to delete a classType
     * @param id of the of the classType to delete
     * @return Status no content (204)
     */
    @DeleteMapping("/classtype/{id}")
    public ResponseEntity<?> deleteClassType(@PathVariable Integer id) {
        try {
            service.deleteClassType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }





    private ClassTypeDto convertToDto(ClassType ClassType){
        if (ClassType == null) {
            throw new IllegalArgumentException("There is no such ClassType");
        }
        ClassTypeDto ClassTypeDto = new ClassTypeDto(ClassType.getName(), ClassType.getDescription(), ClassType.getApproved(), ClassType.getDifficultyLevel(), ClassType.getId());
        return ClassTypeDto;
    }
    
}
