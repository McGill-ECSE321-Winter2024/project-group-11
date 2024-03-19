package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Registration;


import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")


public class RegistrationRepositoryTests {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @AfterEach
    void clearDatabase() {
        registrationRepository.deleteAll();
        sessionRepository.deleteAll();
        customerRepository.deleteAll();
    }
    @SuppressWarnings("deprecation")
    @Test
    void TestFindRegistrationByCustomerFirstName(){
        Registration registration1 = new Registration ();
        Customer customer1 = new Customer();
        Session session = new Session();
        ClassType classType = new ClassType();
        classType.setName("yoga");

        String firstName1 = "Bob";
        String lastName1 = "Cousy";
        int remainingCap = 3;

        customer1.setFirstName(firstName1);
        customer1.setLastName(lastName1);

        session.setRemainingCapacity(remainingCap);
        session.setClassType(classType);
        registration1.setCustomer(customer1);
        registration1.setSession(session);
        registration1.setDate(new Date(2024, 1, 23));

        //saving the dependencies
        classTypeRepository.save(classType);
        sessionRepository.save(session);
        customerRepository.save(customer1);


        //testing write
        Registration result = registrationRepository.save(registration1);
        assertEquals(registration1, result);
        assertEquals(new Date(2024, 1, 23), result.getDate());
        assertEquals("Bob", result.getCustomer().getFirstName());

        //testing read operations
        int id = registration1.getId();
        result = registrationRepository.findById(id).get();
        assertEquals(new Date(2024, 1, 23), result.getDate());
        //testing if reference'd data matches expected.
        assertEquals("Bob", result.getCustomer().getFirstName());



        

    }
}

