package ca.mcgill.ecse321.SportsCenterApp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;

@SpringBootTest
@ActiveProfiles("test")
public class InstructorRepositoryTests {
    @Autowired
    private InstructorRepository instructorRepository;

    @AfterEach
    void clearDatabase() {
        instructorRepository.deleteAll();
    }

    @Test
    public void testFindInstructorByEmail(){
        //Create instructors

        String firstName2 = "Ronald";
        String lastName2 = "Mcdonald";
        String email2 = "mcdonalds@gmail.com";
        String biography2 = "mcdonald ceo";
        Integer yearOfExp = 30;

        Instructor instructor2 = new Instructor();
        instructor2.setFirstName(firstName2);
        instructor2.setLastName(lastName2);
        instructor2.setEmail(email2);
        instructor2.setBiography(biography2);
        instructor2.setYearsOfExperience(yearOfExp);

        //save the instructors
        instructorRepository.save(instructor2);

        
        //Read
        // instructor2 = instructorRepository.findInstructorByEmail(email2);
        Instructor testinstructor = instructorRepository.findInstructorByEmail(email2);

        //Assertion
        assertNotNull(testinstructor);
        assertEquals(email2, testinstructor.getEmail());
        assertEquals(firstName2, testinstructor.getFirstName());
        assertEquals(lastName2, testinstructor.getLastName());
        assertEquals(biography2, testinstructor.getBiography());
        assertEquals(yearOfExp, testinstructor.getYearsOfExperience());

    }

    @Test
    public void testUpdateInstructorEmail(){

        //Create instructor
        String firstName2 = "Ronald";
        String lastName2 = "Mcdonald";
        String email2 = "mcdonalds@gmail.com";
        String biography2 = "mcdonald ceo";
        Integer yearOfExp = 30;

        Instructor instructor2 = new Instructor();
        instructor2.setFirstName(firstName2);
        instructor2.setLastName(lastName2);
        instructor2.setEmail(email2);
        instructor2.setBiography(biography2);
        instructor2.setYearsOfExperience(yearOfExp);
        String newEmail = "wendys@gmail.ca";

        //Save
        instructorRepository.save(instructor2);


        int status = instructorRepository.updateInstructorEmailByEmail(email2,newEmail);


        assertEquals(1, status);

        Instructor testinstructor =  instructorRepository.findInstructorByEmail(newEmail);


        assertNotNull(testinstructor);
        assertNotEquals(email2, testinstructor.getEmail());
        assertEquals(newEmail, testinstructor.getEmail());
        assertEquals(firstName2, testinstructor.getFirstName());
        assertEquals(lastName2, testinstructor.getLastName());
        assertEquals(biography2, testinstructor.getBiography());
        assertEquals(yearOfExp, testinstructor.getYearsOfExperience());

    }


    @Test
    public void testDeleteInstructorbyEmail(){

        //Create instructor 
        String firstName2 = "Ronald";
        String lastName2 = "Mcdonald";
        String email2 = "mcdonalds@gmail.com";
        String biography2 = "mcdonald ceo";
        Integer yearOfExp = 30;

        Instructor instructor2 = new Instructor();
        instructor2.setFirstName(firstName2);
        instructor2.setLastName(lastName2);
        instructor2.setEmail(email2);
        instructor2.setBiography(biography2);
        instructor2.setYearsOfExperience(yearOfExp);

        //Save

        instructorRepository.save(instructor2);


        //call method

        instructorRepository.deleteByEmail(email2);

        Instructor testInstructor = instructorRepository.findInstructorByEmail(email2);

        assertNull(testInstructor);






    }
    
}
