package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;


import java.util.*;


@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    
    /**
	 * Method to create a instructor account
     * @param aFirstName of instructor
     * @param aLastName of instructor
	 * @param aEmail of instructor
	 * @param aPassword of instructor
	 * @param aYearsOfExperience of instructor
     * @param aBiograph of instructor
	 * @return an instructor corresponding to the instructor object just created
	 * @throws Exception if email/password is null or a instructor already exists with given email
	 */
    @Transactional
    public Instructor createInstructor(String aFirstName, String aLastName, String aEmail, String aPassword, Integer aYearsOfExperience, String aBiography) {

        //Input validation
        if (aEmail == null || aPassword == null) {
            throw new IllegalArgumentException("Email or password cannot be empty");
        }

        if (aFirstName == null || aLastName == null) {
            throw new IllegalArgumentException("Name fields cannot be empty, please provide the full name");
        }

        if (aYearsOfExperience < 0) {
            throw new IllegalArgumentException("Instructor cannot have negative years of experience");
        }
        Instructor instructor = instructorRepository.findInstructorByEmail(aEmail);
        if (instructor != null) {
            throw new IllegalArgumentException("Instructor with email exists!");
        }
        instructor = new Instructor(aFirstName, aLastName, aEmail, aPassword, aYearsOfExperience, aBiography);

        instructorRepository.save(instructor);
        
        return instructor;
    }

    /**
	 * Method to get a instructor by id
	 * @param id of instructor
	 * @return instructor with id "id"
	 * @throws Exception if no instructor exists with id
	 */
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


    /**
	 * Method to get a instructor by email
	 * @param email of instructor
	 * @return the instructor with the given email
	 * @throws Exception if email is null of if no instructor exists with given email
	 */
    @Transactional
    public Instructor getInstructorByEmail(String email){

        if(email == null) {
			throw new IllegalArgumentException("Email cannot be empty");
        }

        Instructor instructor = instructorRepository.findInstructorByEmail(email);

        if (instructor != null){
            return instructor;
        }
        else {
            throw new IllegalArgumentException("Instructor not found for email: " + email);
        }
    }

    /**
	 * Method to change an instructor password by id
	 * @param id of instructor
     * @param newPassword new desired password
	 * @return the instructor
	 * @throws Exception if password doesnt respect the conditions or of if no instructor exists with given id
	 */
    @Transactional
    public Instructor updateInstructorPassword(Integer id, String newPassword){
        
        Instructor instructor = getInstructor(id);

        instructor.setPassword(newPassword);
        instructorRepository.save(instructor);

        return instructor;

    }

     /**
	 * Method to change an instructor password by email
	 * @param email of instructor
     * @param newPassword new desired password
	 * @return the instructor
	 * @throws Exception if password doesnt respect the conditions, email is empty or of if no instructor exists with given email
	 */
    @Transactional
    public Instructor updateInstructorPassword(String email, String newPassword){
        
        Instructor instructor = getInstructorByEmail(email);

        instructor.setPassword(newPassword);
        instructorRepository.save(instructor);

        return instructor;

    }



    /**
	 * Method to list all instructors
	 * @return a list of instructor
	 */
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


    /**
	 * Method to delete an instructor
	 * @param id of instructor
	 * @throws Exception if instructor not found
	 */
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


    /**
	 * Method to validate an email
	 * @param email
     * @return true if the email is valid, false otherwise
	 */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
	 * Method to validate a password
	 * @param passwrd of instructor
     * @return true if the password is valid 
     * At least 1 digit
     * At least one lowercase
     * At least one uppercase
     * no whitespace
     * at least 8 character in length
	 */
    public boolean isValidPassword(String password) {
        String passwordpattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(passwordpattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();

    }
}
      
