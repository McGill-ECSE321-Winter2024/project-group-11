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
     * @param InstructorDto (InstructorDto)
     * @return a insturctor dto
     */
    @PostMapping("/instructor")
    public ResponseEntity<?> createInstructor(@RequestBody InstructorDto instructorDto) {
        try {
            Instructor instructor = service.createInstructor(instructorDto.getFirstName(), instructorDto.getLastName(), instructorDto.getEmail(), instructorDto.getPassword(), instructorDto.getYearsOfExperience(), instructorDto.getBiography());
            return new ResponseEntity<>(convertToDto(instructor), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = {"/instructor" , "/instructor/" })
    public List<InstructorDto> getAllInstructors() {
        System.out.println("Hello world (instructors)");

        return service.getAllInstructors().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
    }

    @GetMapping(value = {"/hello" , "/hello/" })
    public String hello(){
        return "Hello World";
    }



    private InstructorDto convertToDto(Instructor instructor){
        if (instructor == null) {
            throw new IllegalArgumentException("There is no such instructor");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getFirstName(), instructor.getLastName(),instructor.getEmail(), instructor.getPassword(), instructor.getId(), instructor.getYearsOfExperience(), instructor.getBiography());
        return instructorDto;
    }


}

