package ca.mcgill.ecse321.SportsCenterApp.services;

import ca.mcgill.ecse321.SportsCenterApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.model.User;

import java.util.*;


@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    

    @Transactional
    public Instructor createInstructor(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aYearsOfExperience, String aBiography) {

        Instructor instructor = instructorRepository.findInstructorByEmail(aEmail);
        if (instructor != null) {
            throw new IllegalArgumentException("Instructor with email exists!");
        }
        instructor = new Instructor(aFirstName, aLastName, aEmail, aPassword, aYearsOfExperience, aBiography);

        instructorRepository.save(instructor);
        
        return instructor;
    }

    @Transactional
    public Instructor getInstructor(Integer id){

        Optional<Instructor> instructor = instructorRepository.findById(id);

        if (instructor.isPresent()){
            return instructor.get();
        }
        else {
            throw new IllegalArgumentException("Instructor not found for id: " + id);
        }
    }


    @Transactional
    public List<Instructor> getAllInstructors(){
        return toList(instructorRepository.findAll());
    }


    @Transactional
    public Instructor updateInstructorBio(Integer id, String biography){

        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()){
            Instructor updatedInstructor = instructor.get();
            updatedInstructor.setBiography(biography);
            return updatedInstructor;
        }
        else{
            throw new IllegalArgumentException("Instructor not found for id: "+ id);
        }
    }

    @Transactional
    public Instructor updateInstructorYearsOfXp(Integer id, Integer years){

        Optional<Instructor> instructor = instructorRepository.findById(id);
        if (instructor.isPresent()){
            Instructor updatedInstructor = instructor.get();
            updatedInstructor.setYearsOfExperience(years);
            return updatedInstructor;
        }
        else{
            throw new IllegalArgumentException("Instructor not found for id: "+ id);
        }
    }

    
    

    @Transactional
    public void deleteInstructor(Integer id){
        instructorRepository.deleteById(id);
    }


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable){
            resultList.add(t);
        }
        return resultList;
    }
}
