package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;


import java.util.*;

@Service
public class ClassTypeService {

    @Autowired
    ClassTypeRepository classTypeRepository;


    @Transactional
    public ClassType creatClassType(String name, String description, boolean approved, DifficultyLevel difficultyLevel){

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }


        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description cannot be empty!");
        }

        if (difficultyLevel == null) {
            throw new IllegalArgumentException("Difficulty Level cannot be null!");
        }

        ClassType classType = new ClassType(name, description, approved, difficultyLevel);

        classTypeRepository.save(classType);

        return classType;
    }

    @Transactional
    public ClassType creatClassType(String name, String description, boolean approved, DifficultyLevel difficultyLevel, String imageUrl){

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }


        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("description cannot be empty!");
        }

        if (difficultyLevel == null) {
            throw new IllegalArgumentException("Difficulty Level cannot be null!");
        }

        ClassType classType = new ClassType(name, description, approved, difficultyLevel, imageUrl);

        classTypeRepository.save(classType);

        return classType;
    }

    /**
     * Returns a class type based on id
     * @param id class Type id
     * @return a classType.
     */
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


    /**
     * Returns a class type based on name
     * @param className class Type name
     * @return a classType.
     */
    @Transactional
    public ClassType getClassType(String className) {
        ClassType classType = classTypeRepository.findClassTypeByName(className);

        if (classType != null){
            return classType;
        }
        else {
            throw new IllegalArgumentException("Class " + className + " not found");
        }
    }
    

    /**
     * Returns all class types (approved or not)
     * @return a list of classType.
     */
    @Transactional
    public List<ClassType> getAllClassTypes(){
        return toList(classTypeRepository.findAll());
    }


        /**
     * Returns all approved class types
     * @return a list of approved class types.
     */
    @Transactional 
    public List<ClassType> getAllApprovedClassTypes() {
        List<ClassType> allClassTypes = toList(classTypeRepository.findAll());
        Iterator<ClassType> iterator = allClassTypes.iterator();
        while (iterator.hasNext()) {
            ClassType c = iterator.next();
            if (!c.getApproved()) {
                iterator.remove(); // Remove the element from the list using iterator
            }
        }
        return allClassTypes;
    }



    /**
     * Updates a classType based on the parameter provided
     * if null = we dont want to update the field
     * @param id classType id.
     * @param newName new name for the class Type.
     * @param newDesc new description for the class Type.
     * @param newLevel new difficulty level for the class Type.
     * @return classType or error message if classType is not found.
     */
    @Transactional
    public ClassType updateClassType(Integer id, String newName, String newDesc, DifficultyLevel newLevel) {
    Optional<ClassType> classTypeOptional = classTypeRepository.findById(id);

    if (classTypeOptional.isPresent()) {
        ClassType classType = classTypeOptional.get();
        
        // Update properties if provided
        if (newName != null) {
            if(newName.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty!");
            }
            classType.setName(newName);
        }
        if (newDesc != null) {
            if (newDesc.isEmpty()) {
                throw new IllegalArgumentException("description cannot be empty!");
            }
            classType.setDescription(newDesc);
        }
        if (newLevel != null) {
            classType.setDifficultyLevel(newLevel);
        }
        
        // Save the updated classType
        classTypeRepository.save(classType);
        
        return classType;
    } else {
        throw new IllegalArgumentException("ClassType not found for id: " + id);
    }
}



    /**
     * Approves a class
     * sets the boolean field to true
     * @param id classType id.
     * @return classType or error message if classType is not found.
     */
    @Transactional
    public ClassType approveClassType(Integer id){

        Optional<ClassType> classType = classTypeRepository.findById(id);

        if (classType.isPresent()){
           ClassType classT = classType.get();

           classT.setApproved(true);
           classTypeRepository.save(classT);

           return classT;
        } else {

            throw new IllegalArgumentException("ClassType not found for id: "+ id);
        }

    }


    /**
     * disaprove a class
     * sets the boolean field to false
     * @param id classType id.
     * @return classType or error message if classType is not found.
     */
    @Transactional
    public ClassType disapproveClassType(Integer id){

        Optional<ClassType> classType = classTypeRepository.findById(id);

        if (classType.isPresent()){
           ClassType classT = classType.get();

           classT.setApproved(false);
           classTypeRepository.save(classT);
           return classT;
        } else {

            throw new IllegalArgumentException("ClassType not found for id: "+ id);
        }

    }



    /**
     * delete a class by id
     * @param id classType id.
     * @return nothing
     */
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
