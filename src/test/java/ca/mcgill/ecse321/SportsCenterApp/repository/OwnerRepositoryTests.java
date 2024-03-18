package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
@ActiveProfiles("test")
public class OwnerRepositoryTests {

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    void clearDatabase() {
        ownerRepository.deleteAll();
    }

    @Test
    void testFindOwnerById() {

        //creating an owner to save into database.

        Owner gymOwner = new Owner();
        String firstName = "Antonio";
        String lastName = "Brown";
        gymOwner.setFirstName(firstName);
        gymOwner.setLastName(lastName);
        // write test case, testing if data correctly gets saved into database.
        Owner result = ownerRepository.save(gymOwner);
        assertNotNull(result);

        //verifying that the saved object matches the original gymOwner object.

        //checks if the attributes match.
        assertEquals(firstName, result.getFirstName());
        //checks if the stored/written object is the same one as the original

        assertEquals(gymOwner, result);

        Integer id = gymOwner.getId();
        //read test case, testing if the queried data matches its expected values.
        result = ownerRepository.findById(id).get();

        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());

    }
}
