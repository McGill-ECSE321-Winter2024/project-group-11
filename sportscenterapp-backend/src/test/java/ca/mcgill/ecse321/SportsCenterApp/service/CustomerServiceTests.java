package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.services.CustomerService;
import ca.mcgill.ecse321.SportsCenterApp.services.OwnerService;
import ca.mcgill.ecse321.SportsCenterApp.repository.OwnerRepository;

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
public class CustomerServiceTests {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;


    private static final String CUSTOMER1_FIRST_NAME = "Kenny";
    private static final String CUSTOMER1_LAST_NAME = "Nguyen";
    private static final String CUSTOMER1_EMAIL = "ken@gmail.com";
    private static final String CUSTOMER1_PASSWORD = "kennyNoMoney1!";
    private static final int CUSTOMER1_ID = 1;
    private static final float CUSTOMER1_ACCOUNT_BALANCE = 0.45f;
    private static final String CUSTOMER1_TOKEN = "blabla";

    private static final String CUSTOMER2_FIRST_NAME = "yo";
    private static final String CUSTOMER2_LAST_NAME = "yoyo";
    private static final String CUSTOMER2_EMAIL = "yoyoyo@mcdo.ca";
    private static final String CUSTOMER2_PASSWORD = "324asdasdadw!";
    private static final int CUSTOMER2_ID = 2;
    private static final float CUSTOMER2_ACCOUNT_BALANCE = 100f;
    private static final String CUSTOMER2_TOKEN = "hahaha";


    @BeforeEach
    void setMockOutput() {

        Customer c1 = new Customer();
        c1.setFirstName(CUSTOMER1_FIRST_NAME);
        c1.setLastName(CUSTOMER1_LAST_NAME);
        c1.setEmail(CUSTOMER1_EMAIL);
        c1.setPassword(CUSTOMER1_PASSWORD);
        c1.setToken(CUSTOMER1_TOKEN);
        c1.setId(CUSTOMER1_ID);
        c1.setAccountBalance(CUSTOMER1_ACCOUNT_BALANCE);


        Customer c2 = new Customer();
        c2.setFirstName(CUSTOMER2_FIRST_NAME);
        c2.setLastName(CUSTOMER2_LAST_NAME);
        c2.setEmail(CUSTOMER2_EMAIL);
        c2.setPassword(CUSTOMER2_PASSWORD);
        c2.setToken(CUSTOMER2_TOKEN);
        c2.setId(CUSTOMER2_ID);
        c2.setAccountBalance(CUSTOMER2_ACCOUNT_BALANCE);


        List<Customer> allCustomers = new ArrayList<>(List.of(c1, c2));

        lenient().when(customerRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(c1);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(c2);
            } else {
                return Optional.empty();
            }
        });


        lenient().when(customerRepository.findCustomerByEmail(any(String.class))).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            if (email.equals(CUSTOMER1_EMAIL)) {
                return c1;
            } else if (email.equals(CUSTOMER2_EMAIL)) {
                return c2;
            } else {
                return null;
            }
        });


        lenient().when(customerRepository.findAll()).thenReturn(allCustomers);

        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {
            Customer customer = invocation.getArgument(0);
            allCustomers.add(customer);
            return customer;
        });
    }
    @Test
    public void testCreateCustomer(){
        {
            String C_TEST_FIRSTNAME = "kenny";
            String C_TEST_LASTNAME = "nguyen";
            String C_TEST_EMAIL = "kenny@gmail.com";
            String C_TEST_PASSWORD = "1aBccccccc!";
            String C_TEST_TOKEN = "okay";
            float C_TEST_ACCOUNTBALANCE = 25f;
            Customer createdCustomer = customerService.createCustomer(C_TEST_FIRSTNAME, C_TEST_LASTNAME, C_TEST_EMAIL, C_TEST_PASSWORD, C_TEST_TOKEN,C_TEST_ACCOUNTBALANCE);

            assertNotNull(createdCustomer);
            assertEquals(C_TEST_FIRSTNAME, createdCustomer.getFirstName());
            assertEquals(C_TEST_LASTNAME, createdCustomer.getLastName());
            assertEquals(C_TEST_EMAIL, createdCustomer.getEmail());
            assertEquals(C_TEST_PASSWORD, createdCustomer.getPassword());
            assertEquals(C_TEST_TOKEN, createdCustomer.getToken());
            assertEquals(C_TEST_ACCOUNTBALANCE, createdCustomer.getAccountBalance());
        }
    }

    @Test
    public void testGetCustomer(){
        Customer customer = customerService.getCustomer(CUSTOMER1_ID);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_FIRST_NAME, customer.getFirstName());
        assertEquals(CUSTOMER1_LAST_NAME, customer.getLastName());
        assertEquals(CUSTOMER1_EMAIL, customer.getEmail());
        assertEquals(CUSTOMER1_PASSWORD, customer.getPassword());
        assertEquals(CUSTOMER1_TOKEN, customer.getToken());
        assertEquals(CUSTOMER1_ACCOUNT_BALANCE, customer.getAccountBalance());

        Customer customer2 = customerService.getCustomer(CUSTOMER2_ID);
        assertNotNull(customer2);
        assertEquals(CUSTOMER2_FIRST_NAME, customer2.getFirstName());
        assertEquals(CUSTOMER2_LAST_NAME, customer2.getLastName());
        assertEquals(CUSTOMER2_EMAIL, customer2.getEmail());
        assertEquals(CUSTOMER2_PASSWORD, customer2.getPassword());
        assertEquals(CUSTOMER2_TOKEN, customer2.getToken());
        assertEquals(CUSTOMER2_ACCOUNT_BALANCE, customer2.getAccountBalance());
    }

    @Test
    public void testGetAllCustomers(){
        List<Customer> customers = null;

        try {
            customers = customerService.getAllCustomers();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }

        assertEquals(2,customers.size());

    }

    @Test
    public void testUpdateCustomerEmail(){
        Customer customer = customerService.getCustomer(CUSTOMER1_ID);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_FIRST_NAME, customer.getFirstName());
        assertEquals(CUSTOMER1_LAST_NAME, customer.getLastName());
        assertEquals(CUSTOMER1_EMAIL, customer.getEmail());
        assertEquals(CUSTOMER1_PASSWORD, customer.getPassword());
        assertEquals(CUSTOMER1_TOKEN, customer.getToken());
        assertEquals(CUSTOMER1_ACCOUNT_BALANCE, customer.getAccountBalance());

        String newEmail = "newEmail1@gmail.com";
        customer.setEmail(newEmail);
        assertEquals(newEmail, customer.getEmail());

    }
    @Test
    public void testUpdateCustomerAccountBalance(){
        Customer customer = customerService.getCustomer(CUSTOMER1_ID);
        assertNotNull(customer);
        assertEquals(CUSTOMER1_FIRST_NAME, customer.getFirstName());
        assertEquals(CUSTOMER1_LAST_NAME, customer.getLastName());
        assertEquals(CUSTOMER1_EMAIL, customer.getEmail());
        assertEquals(CUSTOMER1_PASSWORD, customer.getPassword());
        assertEquals(CUSTOMER1_TOKEN, customer.getToken());
        assertEquals(CUSTOMER1_ACCOUNT_BALANCE, customer.getAccountBalance());

        float accountBalance = 1.45f;
        customer.setAccountBalance(accountBalance);
        assertEquals(accountBalance, customer.getAccountBalance());
    }

}
