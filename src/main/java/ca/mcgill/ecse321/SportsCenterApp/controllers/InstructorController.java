package ca.mcgill.ecse321.SportsCenterApp.controllers;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.SportsCenterApp.dto.InstructorDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.services.InstructorService;

import java.util.*;



@CrossOrigin(origins = "*")
@RestController
public class InstructorController {

    @Autowired
    private InstructorService service;


    /**
     * POST request to create a new Instructor
     * @param instructorDto (InstructorDto)
     * @return a insturctor dto
     */
    @PostMapping("/instructor")
    public ResponseEntity<?> createInstructor(@RequestBody InstructorDto instructorDto) {
        try {
            Instructor instructor = service.createInstructor(instructorDto.getFirstName(), instructorDto.getLastName(), instructorDto.getEmail(), instructorDto.getPassword(), instructorDto.getToken(), instructorDto.getYearsOfExperience(), instructorDto.getBiography());
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * GET request to retrieve all instructors
     * @return a list of instructors
     */
    @GetMapping("/instructors")
    public List<InstructorDto> getAllInstructors() {
        return service.getAllInstructors().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
    }

    /**
     * GET request to retrieve a instructor by ID
     * @param id (Integer) ID of the instructor
     * @return class type dto
     */
    @GetMapping("/instructors/{id}")
    public ResponseEntity<?> getInstructorById(@PathVariable Integer id) {
        try {
            Instructor instructor = service.getInstructor(id);
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * GET request to retrieve a instructor by email
     * @param email (String) email of the instructor
     * @return Instructor type dto
     */
    @GetMapping("/instructors/email/{email}")
    public ResponseEntity<?> getInstructorByEmail(@PathVariable String email) {
        try {
            Instructor instructor = service.getInstructorByEmail(email);
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * PUT request to update a instructor password by ID
     * @param id (Integer) ID of the instructor
     * @param newPassword (String) new desired password
     * @return Instructor dto
     */
    @PutMapping("/instructors/{id}/password")
    public ResponseEntity<?> updateInstructorPassword (@PathVariable Integer id, @RequestParam String newPassword) {

        try {
            Instructor instructor = service.updateInstructorPassword(id, newPassword);
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * PUT request to update a instructor password by email
     * @param email (String) of the instructor
     * @param newPassword (String) new desired password
     * @return Instructor dto
     */
    @PutMapping("/instructors/email/{email}/password")
    public ResponseEntity<?> updateInstructorPassword (@PathVariable String email, @RequestParam String newPassword) {

        try {
            Instructor instructor = service.updateInstructorPassword(email, newPassword);
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * DELETE request to delete a instructor
     * @param id of the of the instructor to delete
     * @return Status no content (204)
     */

    @DeleteMapping("/instructors/{id}")
    public ResponseEntity<?> deleteInstructor(@PathVariable Integer id) {
        try {
            service.deleteInstructor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    private InstructorDto convertToDto(Instructor instructor){
        if (instructor == null) {
            throw new IllegalArgumentException("There is no such instructor");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getFirstName(), instructor.getLastName(),instructor.getEmail(), instructor.getPassword(), instructor.getId(), instructor.getToken(), instructor.getYearsOfExperience(), instructor.getBiography());
        return instructorDto;
    }


}

