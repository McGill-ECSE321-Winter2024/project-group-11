package ca.mcgill.ecse321.SportsCenterApp.controllers;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.SportsCenterApp.dto.InstructorDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.services.InstructorService;

import java.util.*;



@CrossOrigin(origins = "*")
@RestController
public class InstructorController {

    @Autowired
    private InstructorService service;


    @GetMapping(value = {"/instructors" , "/instructors/" })
    public List<InstructorDto> getAllInstructors() {
        System.out.println("Hello world (instructors)");

        return service.getAllInstructors().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
    }



    private InstructorDto convertToDto(Instructor instructor){
        if (instructor == null) {
            throw new IllegalArgumentException("There is no such instructor");
        }
        InstructorDto instructorDto = new InstructorDto(instructor.getFirstName(), instructor.getLastName(),instructor.getEmail(), instructor.getPassword(), instructor.getId(), instructor.getYearsOfExperience(), instructor.getBiography());
        return instructorDto;
    }


}

