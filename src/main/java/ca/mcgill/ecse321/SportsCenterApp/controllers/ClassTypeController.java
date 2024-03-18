package ca.mcgill.ecse321.SportsCenterApp.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.services.ClassTypeService;

import java.util.*;



@CrossOrigin(origins = "*")
@RestController
public class ClassTypeController {

    @Autowired
    private ClassTypeService service;

    @GetMapping(value = {"/classtypes" , "/classtypes/" })
    public List<ClassTypeDto> getAllClassTypes(){
        System.out.println("Hello world (classtypes)");

        return service.getAllClassTypes().stream().map(c -> convertToDto(c)).collect(Collectors.toList());
    }


    private ClassTypeDto convertToDto(ClassType ClassType){
        if (ClassType == null) {
            throw new IllegalArgumentException("There is no such ClassType");
        }
        ClassTypeDto ClassTypeDto = new ClassTypeDto(ClassType.getName(), ClassType.getDescription(), ClassType.getApproved(), ClassType.getDifficultyLevel(), ClassType.getId());
        return ClassTypeDto;
    }
    
}
