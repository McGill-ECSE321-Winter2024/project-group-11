package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.services.InstructorService;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class InstructorServiceTests {

    @Mock
    private InstructorRepository instructorRepository;
    @InjectMocks
    private InstructorService instructorService;



    private static final String I1_FIRST_NAME = "Yazid";
    private static final String I1_LAST_NAME = "Asselah";
    private static final String I1_EMAIL = "asselahyazid@gmail.com";
    private static final String I1_PASSWORD = "1aBccccccc!";
    private static final int I1_YEARS_OF_EXPERIENCE = 3;
    private static final String I1_BIOGRAPHY = "Kai'sa main";
    private static final int I1_ID = 1;

    private static final String I2_FIRST_NAME = "Ronald";
    private static final String I2_LAST_NAME = "Mcdonalds";
    private static final String I2_EMAIL = "mcdonalds@mcdo.ca";
    private static final String I2_PASSWORD = "1aBccccccc!";
    private static final int I2_YEARS_OF_EXPERIENCE = 10;
    private static final String I2_BIOGRAPHY = "Mcdonald CEO";
    private static final int I2_ID = 2;



    @BeforeEach
    void setMockOutput() {

        Instructor i1 = new Instructor();
        i1.setFirstName(I1_FIRST_NAME);
        i1.setLastName(I1_LAST_NAME);
        i1.setEmail(I1_EMAIL);
        i1.setPassword(I1_PASSWORD);
        i1.setYearsOfExperience(I1_YEARS_OF_EXPERIENCE);
        i1.setBiography(I1_BIOGRAPHY);
        i1.setId(I1_ID);

        Instructor i2 = new Instructor();
        i2.setFirstName(I2_FIRST_NAME);
        i2.setLastName(I2_LAST_NAME);
        i2.setEmail(I2_EMAIL);
        i2.setPassword(I2_PASSWORD);
        i2.setYearsOfExperience(I2_YEARS_OF_EXPERIENCE);
        i2.setBiography(I2_BIOGRAPHY);
        i2.setId(I2_ID);

        List<Instructor> allInstructors = new ArrayList<>(List.of(i1, i2));

        lenient().when(instructorRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(i1);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(i2);
            } else {
                return Optional.empty();
            }
        });



        lenient().when(instructorRepository.findInstructorByEmail(any(String.class))).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            if (email.equals(I1_EMAIL)) {
                return i1;
            } else if (email.equals(I2_EMAIL)) {
                return i2;
            } else {
                return null;
            }
        });
        


        lenient().when(instructorRepository.findAll()).thenReturn(allInstructors);

        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer((InvocationOnMock invocation) -> {
            Instructor instructor = invocation.getArgument(0);
            allInstructors.add(instructor);
            return instructor;
        });


        // Mocking void method deleteByEmail()
        lenient().doAnswer(invocation -> {
            String email = invocation.getArgument(0);

            if (email.equals(I1_EMAIL)) {
                allInstructors.removeIf(instructor -> instructor.getEmail().equals(I1_EMAIL));
            } else if (email.equals(I2_EMAIL)){
                allInstructors.removeIf(instructor -> instructor.getEmail().equals(I2_EMAIL));
            } 
            return null; 
        }).when(instructorRepository).deleteByEmail(any(String.class));

        // Mocking void method deleteById()
        lenient().doAnswer(invocation -> {
            int id = invocation.getArgument(0);

            if (id == I1_ID) {
                allInstructors.removeIf(instructor -> instructor.getId() == I1_ID);
            } else if (id == I2_ID){
                allInstructors.removeIf(instructor -> instructor.getId() == I2_ID);
            }
            return null;
        }).when(instructorRepository).deleteById(anyInt());


    }


    @Test
    //Successfully create a person
    public void testCreateInstructor(){

        assertEquals(2, instructorService.getAllInstructors().size());


        String I_TEST_FIRST_NAME = "Sung";
        String I_TEST_LAST_NAME = "Jin-woo";
        String I_TEST_EMAIL = "jinwoo@gmail.com";
        String I_TEST_PASSWORD = "1aBccccccc!";
        int I_TEST_YEARS_OF_EXPERIENCE = 10;
        String I_TEST_BIOGRAPHY = "ARISE";


        // Invalid inputs
        String invalidEmail1 = "invalid.email.com";
        String invalidEmail2 = "john.doe@example";
        String invalidEmail3 = "@email.com";
        String invalidEmail4 = "email@";
        String invalidPassword1 = "weak password"; //with space
        String invalidPassword2 = "Password123"; // No special char
        String invalidPassword3 = "p@ssword123"; // No uppercase
        String invalidPassword4 = "P@SSWORD123"; // No lowercase
        String invalidPassword5 = "P@ssword"; // No digit





        //invalid Emails

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, invalidEmail1, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid Email adress", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, invalidEmail2, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid Email adress", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, invalidEmail3, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid Email adress", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, invalidEmail4, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid Email adress", exception.getMessage());



        //invalid passwords


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, invalidPassword1, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, invalidPassword2, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, invalidPassword3, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, invalidPassword4, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());



        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, invalidPassword5, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());


        //Empty name fields

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor("", I_TEST_LAST_NAME, I_TEST_EMAIL, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Name fields cannot be empty", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, "", I_TEST_EMAIL, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Name fields cannot be empty", exception.getMessage());


        //passing negative years of XP
        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, I_TEST_PASSWORD, -2, I_TEST_BIOGRAPHY);
        });
        assertEquals("Instructor cannot have negative years of experience", exception.getMessage());


        //Instructor with existing email
        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I1_EMAIL, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);
        });
        assertEquals("Instructor with email exists!", exception.getMessage());


        //Successfull creation
        Instructor createdInstructor = instructorService.createInstructor(I_TEST_FIRST_NAME, I_TEST_LAST_NAME, I_TEST_EMAIL, I_TEST_PASSWORD, I_TEST_YEARS_OF_EXPERIENCE, I_TEST_BIOGRAPHY);

        assertNotNull(createdInstructor);
        assertEquals(I_TEST_FIRST_NAME, createdInstructor.getFirstName());
        assertEquals(I_TEST_LAST_NAME, createdInstructor.getLastName());
        assertEquals(I_TEST_EMAIL, createdInstructor.getEmail());
        assertEquals(I_TEST_PASSWORD, createdInstructor.getPassword());
        assertEquals(I_TEST_YEARS_OF_EXPERIENCE, createdInstructor.getYearsOfExperience());
        assertEquals(I_TEST_BIOGRAPHY, createdInstructor.getBiography());
        assertEquals(3, instructorService.getAllInstructors().size());

    }   


    @Test
    public void testGetAllInstructors(){

        List<Instructor> instructors = null;

        try {
            instructors = instructorService.getAllInstructors();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }

        assertEquals(2, instructors.size());

    }



    @Test
    public void testGetInstructors(){

        String invalidMail1 = "Idontexist@gmail.com";
        String invalidMail2 = "";
        Integer invalidId1 = 3;
        Integer invalidId2 = null;

        //Invalid emails
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.getInstructorByEmail(invalidMail1);
        });
        assertEquals("Instructor not found for email: " + invalidMail1, exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.getInstructorByEmail(invalidMail2);
        });
        assertEquals("Email cannot be empty", exception.getMessage());


        //invalid ids
        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.getInstructor(invalidId1);
        });
        assertEquals("Instructor not found for id: " + invalidId1, exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.getInstructor(invalidId2);
        });
        assertEquals("Empty id", exception.getMessage());


        //valid emails and ids
        
        Instructor instructorMail = instructorService.getInstructorByEmail(I1_EMAIL);
        Instructor instructorId = instructorService.getInstructor(1);


        //Assertions

        assertEquals(I1_FIRST_NAME, instructorMail.getFirstName());
        assertEquals(I1_LAST_NAME, instructorMail.getLastName());
        assertEquals(I1_EMAIL, instructorMail.getEmail());
        assertEquals(I1_PASSWORD, instructorMail.getPassword());
        assertEquals(I1_YEARS_OF_EXPERIENCE, instructorMail.getYearsOfExperience());
        assertEquals(I1_BIOGRAPHY, instructorMail.getBiography());


        assertEquals(I1_FIRST_NAME, instructorId.getFirstName());
        assertEquals(I1_LAST_NAME, instructorId.getLastName());
        assertEquals(I1_EMAIL, instructorId.getEmail());
        assertEquals(I1_PASSWORD, instructorId.getPassword());
        assertEquals(I1_YEARS_OF_EXPERIENCE, instructorId.getYearsOfExperience());
        assertEquals(I1_BIOGRAPHY, instructorId.getBiography());


    }


    @Test
    public void TestUpdateInstructorPassword(){

        String invalidMail1 = "Idontexist@gmail.com";

        String invalidPassword1 = "weak password"; //with space
        String invalidPassword2 = "Password123"; // No special char
        String invalidPassword3 = "p@ssword123"; // No uppercase
        String invalidPassword4 = "P@SSWORD123"; // No lowercase
        String invalidPassword5 = "P@ssword"; // No digit

        String validPassword1 = "P@ssword123";
        String validPassword2 = "P@ssword321";




        
        //invalid passwords with ids and email mix and match (the get function has already been tested)


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(I1_EMAIL, invalidPassword1);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(I1_EMAIL, invalidPassword2);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(I1_EMAIL, invalidPassword3);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(I1_ID, invalidPassword4);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(I1_ID, invalidPassword5);
        });
        assertEquals("Invalid password, passwrod must have at least 1 digit, one lowercase, one uppercase, no whitespace at least 8 character in length", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorPassword(invalidMail1, validPassword1);
        });
        assertEquals("Instructor not found for email: " + invalidMail1, exception.getMessage());

        Instructor updatedInstructor = instructorService.updateInstructorPassword(I1_EMAIL, validPassword1);
        assertEquals(validPassword1, updatedInstructor.getPassword());

        Instructor updatedInstructor2 = instructorService.updateInstructorPassword(I1_ID, validPassword2);
        assertEquals(validPassword2, updatedInstructor2.getPassword());

        assertEquals(I1_EMAIL, updatedInstructor2.getEmail());
        assertEquals(I1_ID, updatedInstructor2.getId());


        
    }

    @Test
    public void TestUpdateInstructorEmail(){

         // Invalid inputs
        String invalidEmail1 = "invalid.email.com";
        String invalidEmail2 = "john.doe@example";
        String invalidEmail3 = "@email.com";
        String invalidEmail4 = "email@";
        String invalidEmail5 = "";


        String validEmail = "hello@gmail.com";
        String validEmail2 = "Bye@outlook.com";


        //Non existant instructor
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(validEmail, validEmail);
        });
        assertEquals("Instructor not found for email: " + validEmail, exception.getMessage());


        //Wrong emails

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(I1_EMAIL, invalidEmail1);
        });
        assertEquals("Invalid new email", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(I1_EMAIL, invalidEmail2);
        });
        assertEquals("Invalid new email", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(I1_EMAIL, invalidEmail3);
        });
        assertEquals("Invalid new email", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(I1_EMAIL, invalidEmail4);
        });
        assertEquals("Invalid new email", exception.getMessage());


        //Empty email
        exception = assertThrows(IllegalArgumentException.class, () -> {
            instructorService.updateInstructorEmail(I1_EMAIL, invalidEmail5);
        });
        assertEquals("Empty email", exception.getMessage());


        //Update by email


        Instructor updatedInstructor = instructorService.updateInstructorEmail(I1_EMAIL, validEmail);
        assertEquals(validEmail, updatedInstructor.getEmail());

        //New instructor should have the same id
        assertEquals(I1_ID, updatedInstructor.getId());

        //Update by Id

        Instructor updatedInstructor2 = instructorService.updateInstructorEmail(I1_ID, validEmail2);
        assertEquals(validEmail2, updatedInstructor2.getEmail());
        assertEquals(I1_ID, updatedInstructor2.getId());



    }



    @Test
    public void TestDeleteInstructor(){

        
        assertEquals(2, instructorService.getAllInstructors().size());

        //random email

        instructorService.deleteInstructor("random@gmail.com");
        assertEquals(2, instructorService.getAllInstructors().size());

        //random id

        instructorService.deleteInstructor(100);
        assertEquals(2, instructorService.getAllInstructors().size());


        //Delete with email
        instructorService.deleteInstructor(I1_EMAIL);
        assertEquals(1, instructorService.getAllInstructors().size());



        //Delete with id

        instructorService.deleteInstructor(I2_ID);
        assertEquals(0, instructorService.getAllInstructors().size());


    }

    

    
}
