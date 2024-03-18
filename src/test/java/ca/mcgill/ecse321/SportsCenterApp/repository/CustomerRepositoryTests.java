package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTests {
    @Autowired
    private CustomerRepository customerRepository;
    @AfterEach
    void clearDatabase() {
        customerRepository.deleteAll();
    }
    @Test
    void testFindCustomerById() {
        //creating customer
        Customer gymCustomer = new Customer();
        String firstName = "Draymond";
        String lastName = "Green";
        gymCustomer.setFirstName(firstName);
        gymCustomer.setLastName(lastName);
        // write test case, testing if data correctly gets saved into database.
        Customer result = customerRepository.save(gymCustomer);
        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());
        assertEquals(gymCustomer, result);

        Integer id = gymCustomer.getId();

        //read test case, testing if the queried data matches its expected values.
        result = customerRepository.findById(id).get();

        assertNotNull(result);
        assertEquals(firstName, result.getFirstName());
    }

    @Test
    void testFindCustomerByFirstName() {
        //creating customers, and putting them into the table.
        Customer one = new Customer();
        Customer two = new Customer();
        Customer three = new Customer();
        one.setFirstName("Deshaun");
        one.setLastName("Watson");
        two.setFirstName("Deshaun");
        two.setLastName("Jackson");

        three.setFirstName("Alex");
        three.setPassword("password");
        //these two rows will be used for later "read" test cases
        customerRepository.save(two);
        customerRepository.save(three);

        Customer res = customerRepository.save(one);
        //write test case
        assertEquals(one, res);
        assertEquals("Deshaun", res.getFirstName());
        assertEquals("Watson", res.getLastName());

        //read test case, this method returns a list of customers that have the same first name, without case sensitivity.
        List<Customer> customers = customerRepository.findCustomersByFirstNameIgnoreCase("Deshaun");
        //here since we know we have 2 customers with the name "Deshaun", we expect the resulting list to have 2 entries.
        assertEquals(2, customers.size());

        customers = customerRepository.findCustomersByFirstNameIgnoreCase("Alex");
        assertEquals(1, customers.size());
        assertEquals("Alex", customers.get(0).getFirstName());
        assertEquals("password", customers.get(0).getPassword());

    }
}
