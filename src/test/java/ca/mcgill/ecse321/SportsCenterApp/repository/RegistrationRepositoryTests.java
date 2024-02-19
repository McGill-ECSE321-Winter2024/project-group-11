package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Registration;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")


public class RegistrationRepositoryTests {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    @AfterEach
    void clearDatabase() {
        registrationRepository.deleteAll();
    }
    @Test
    void TestFindRegistrationByCustomerFirstName(){
        Registration registration1 = new Registration ();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        String firstName1 = "Bob";
        String lastName1 = "Cousy";
        String firstName2 = "Jermaine";
        String lastName2 = "Cole";

        customer1.setFirstName(firstName1);
        customer1.setLastName(lastName1);
        customer2.setFirstName(firstName2);
        customer2.setLastName(lastName2);

        Customer bob = registrationRepository.save(registration1);
        

    }
}

