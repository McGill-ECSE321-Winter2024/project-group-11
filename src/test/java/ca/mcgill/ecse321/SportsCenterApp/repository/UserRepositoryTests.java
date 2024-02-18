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
        Customer one = new Customer();
        one.setFirstName("jonathan");
        one.setLastName("kuminga");
        one.setEmail("kuminga2024@yahoo.com");
        Customer result = userRepository.save(one);
        //write test cases
        assertEquals(one, result);
        assertEquals("jonathan", result.getFirstName());
        assertEquals("kuminga", result.getLastName());

        //read test cases
        int id = result.getId();
        Optional<User> res = userRepository.findById(id);
        one = (Customer) res.get();

        assertNotNull(one);
        assertEquals(id, one.getId());
        assertEquals("jonathan", one.getFirstName());
        assertEquals("kuminga", one.getLastName());

    }
    @Test
    void testFindUserByFirstName() {
        Instructor brock = new Instructor();
        String firstName = "Brock";
        String lastName = "Purdy";
        String bio = "MVP";
        brock.setFirstName(firstName);
        brock.setLastName(lastName);
        brock.setBiography(bio);

        Instructor result = userRepository.save(brock);
        //write test cases
        assertEquals(brock, result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(bio, result.getBiography());

        //read test cases
        result = (Instructor) userRepository.findUserByFirstName(firstName);
        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(bio, result.getBiography());


    }
}
