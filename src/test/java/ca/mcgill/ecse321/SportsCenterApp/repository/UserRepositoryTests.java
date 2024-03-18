package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @AfterEach
    void clearDatabase() {
        userRepository.deleteAll();
    }
    @Test
    void testFindUserById() {
        //creating customer to save into db.
        Customer one = new Customer();
        one.setFirstName("jonathan");
        one.setLastName("kuminga");
        one.setEmail("kuminga2024@yahoo.com");
        Customer result = userRepository.save(one);
        //write test cases, testing if saved data matches its expected values.
        //this checks if the saved object matches the original object

        assertEquals(one, result);
        //these assertions check if the attributes match.
        assertEquals("jonathan", result.getFirstName());
        assertEquals("kuminga", result.getLastName());

        //read test case, testing if the queried data matches its expected values.

        int id = result.getId();
        Optional<User> res = userRepository.findById(id);
        one = (Customer) res.get();
        //thick checks if the query method returns an object.
        assertNotNull(one);
        assertEquals(id, one.getId());
        assertEquals("jonathan", one.getFirstName());
        assertEquals("kuminga", one.getLastName());

    }
    @Test
    void testFindUserByFirstName() {
        //creating Instrucot object.
        Instructor brock = new Instructor();
        String firstName = "Brock";
        String lastName = "Purdy";
        String bio = "MVP";
        brock.setFirstName(firstName);
        brock.setLastName(lastName);
        brock.setBiography(bio);

        Instructor result = userRepository.save(brock);

        //write test cases, testing if saved data matches expected.

        assertEquals(brock, result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(bio, result.getBiography());


        //read test case, testing if the queried data matches its expected values.

        result = (Instructor) userRepository.findUserByFirstNameIgnoreCase(firstName);
        //checking if an object is returned
        assertNotNull(result);
        //verifying if the attributes have been read correctly.
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(bio, result.getBiography());

    }
    @Test
    void testUpdateUserPasswordByEmail() {
        //creating a customer object to save in database.
        Customer one = new Customer();
        one.setPassword("airplane");
        one.setFirstName("George");
        one.setLastName("Washington");
        one.setEmail("fraud@gov");

        userRepository.save(one);
        //Here, result = 1 if successful
        int status = userRepository.updateUserByEmailIgnoreCase("fraud@gov", "donkey");

        //write test case.
        assertEquals(1, status);

        //read test case, testing if the queried data matches its expected values.
        Customer res = (Customer) userRepository.findUserByFirstNameIgnoreCase("george");
        assertEquals("George", res.getFirstName());
        assertEquals("donkey", res.getPassword());

    }
}
