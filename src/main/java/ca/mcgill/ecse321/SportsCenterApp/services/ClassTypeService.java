package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.model.User;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;

import java.util.*;

@Service
public class ClassTypeService {

    @Autowired
    ClassTypeRepository classTypeRepository;


    @Transactional
    public ClassType creatClassType(String name, String description, boolean approved, DifficultyLevel difficultyLevel){
        ClassType classType = new ClassType(name, description, approved, difficultyLevel);

        classTypeRepository.save(classType);

        return classType;
    }

    @Transactional
    public ClassType getClassType(Integer id) {
        Optional<ClassType> classType = classTypeRepository.findById(id);

        if (classType.isPresent()){
            return classType.get();
        }
        else {
            throw new IllegalArgumentException("Class not found for id: " + id);
        }
    }
    

    @Transactional
    public List<ClassType> getAllClassTypes(){
        return toList(classTypeRepository.findAll());
    }

    //TODO create a method to update class type
    //TODO create a method to retrieve class by name?

    @Transactional
    public ClassType updateClassTypeName(Integer id, String newName) {

        Optional<ClassType> classType = classTypeRepository.findById(id);
        if (classType.isPresent()){
            ClassType updatedClassType = classType.get();
            updatedClassType.setName(newName);
            return updatedClassType;
        }
        else{
            throw new IllegalArgumentException("ClassType not found for id: "+ id);
        }
    }



    @Transactional
    public void deleteClassType(Integer id){
        classTypeRepository.deleteById(id);
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable){
            resultList.add(t);
        }
        return resultList;
    }
}
