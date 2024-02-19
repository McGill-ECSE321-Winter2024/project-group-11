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
        Instructor writeInstructor = instructorRepository.save(instructor2);

        //Write test case
        assertNotNull(writeInstructor);
        assertEquals(email2, writeInstructor.getEmail());
        assertEquals(firstName2, writeInstructor.getFirstName());
        assertEquals(lastName2, writeInstructor.getLastName());
        assertEquals(biography2, writeInstructor.getBiography());
        assertEquals(yearOfExp, writeInstructor.getYearsOfExperience());


        
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



        //save the instructors
        Instructor writeInstructor = instructorRepository.save(instructor2);

        //Write test case
        assertNotNull(writeInstructor);
        assertEquals(email2, writeInstructor.getEmail());
        assertEquals(firstName2, writeInstructor.getFirstName());
        assertEquals(lastName2, writeInstructor.getLastName());
        assertEquals(biography2, writeInstructor.getBiography());
        assertEquals(yearOfExp, writeInstructor.getYearsOfExperience());


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


        //Create instructor 
        String firstName3 = "Yazid";
        String lastName3 = "test";
        String email3 = "yazid@gmail.com";
        String biography3 = "none";
        Integer yearOfExp3 = 20;

        Instructor instructor3 = new Instructor();
        instructor3.setFirstName(firstName3);
        instructor3.setLastName(lastName3);
        instructor3.setEmail(email3);
        instructor3.setBiography(biography3);
        instructor3.setYearsOfExperience(yearOfExp3);    


        String firstName4 = "Jhin";
        String lastName4 = "lol";
        String email4 = "Jhin@lol.com";
        String biography4 = "4";
        Integer yearOfExp4 = 4;

        Instructor instructor4 = new Instructor();
        instructor4.setFirstName(firstName4);
        instructor4.setLastName(lastName4);
        instructor4.setEmail(email4);
        instructor4.setBiography(biography4);
        instructor4.setYearsOfExperience(yearOfExp4);  

        //Save

        //save the instructors
        Instructor writeInstructor = instructorRepository.save(instructor2);
        Instructor writeInstructor3 = instructorRepository.save(instructor3);
        Instructor writeInstructor4 = instructorRepository.save(instructor4);

        //Write test case
        assertNotNull(writeInstructor);
        assertEquals(email2, writeInstructor.getEmail());
        assertEquals(firstName2, writeInstructor.getFirstName());
        assertEquals(lastName2, writeInstructor.getLastName());
        assertEquals(biography2, writeInstructor.getBiography());
        assertEquals(yearOfExp, writeInstructor.getYearsOfExperience());


        assertNotNull(writeInstructor3);
        assertEquals(email3, writeInstructor3.getEmail());
        assertEquals(firstName3, writeInstructor3.getFirstName());
        assertEquals(lastName3, writeInstructor3.getLastName());
        assertEquals(biography3, writeInstructor3.getBiography());
        assertEquals(yearOfExp3, writeInstructor3.getYearsOfExperience());

        assertNotNull(writeInstructor4);
        assertEquals(email4, writeInstructor4.getEmail());
        assertEquals(firstName4, writeInstructor4.getFirstName());
        assertEquals(lastName4, writeInstructor4.getLastName());
        assertEquals(biography4, writeInstructor4.getBiography());
        assertEquals(yearOfExp4, writeInstructor4.getYearsOfExperience());


        //check number of rows

        assertEquals(3, instructorRepository.count());



        //call method

        instructorRepository.deleteByEmail(email2);

        Instructor testInstructor = instructorRepository.findInstructorByEmail(email2);

        assertNull(testInstructor);

        //count rows after deletion

        assertEquals(2, instructorRepository.count());


    }
    
}
