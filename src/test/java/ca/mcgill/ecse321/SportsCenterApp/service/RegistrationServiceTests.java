package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import ca.mcgill.ecse321.SportsCenterApp.model.Registration;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import ca.mcgill.ecse321.SportsCenterApp.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTests {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private SessionService sessionService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private static final Integer CUSTOMER1_ID = 10;
    private static final Integer SESSION1_ID = 10;
    private static final Integer REGISTRATION1_ID = 1;



    @BeforeEach
    void setMockOutput(){
        Registration registration = new Registration();
        Date date = new Date(2024);
        Time time = new Time(10);

        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Seph");
        customer.setId(CUSTOMER1_ID);

        Session session = new Session();
        session.setRoomNumber(12);
        session.setId(SESSION1_ID);


        registration.setDate(date);
        registration.setTime(time);
        registration.setCustomer(customer);
        registration.setId(REGISTRATION1_ID);
        registration.setSession(session);


        Registration registration2 = new Registration();
        Date date2 = new Date(2025);
        registration2.setDate(date2);
        registration2.setId(2);
        List<Registration> allRegistrations = List.of(registration, registration2);

        lenient().when(registrationRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(registration);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(registration2);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(registrationRepository.findAll()).thenReturn(allRegistrations);


        lenient().when(customerRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(new Customer("Joe", "Seph", "s@gmail", "abcd", 123, "aToken", 3f));
            }
            return Optional.empty();
        });



        lenient().when(sessionRepository.getSessionsByDateIsAndRoomNumber(any(), anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(1).equals(1)) {
                Session three = new Session();
                three.setEndTime(new Time(120));
                Session four = new Session();
                four.setEndTime(new Time(160));
                return List.of(three, four);
            }
            Session notConflicting = new Session();
            notConflicting.setEndTime(new Time(130));
            return List.of(notConflicting);
        });

    }

    @Test
    void testGetRegistration() {
        Registration registration = null;
        try {
            registration = registrationService.getRegistration(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(registration);
        assertEquals(1, registration.getId());
    }

    @Test
    void testGetAllRegistrations(){
        List<Registration> registrations = null;
        try {
            registrations = registrationService.getAllRegistrations();
        }catch (Exception e){
            System.out.println(e.getMessage());
            fail();
        }
        assertEquals(2, registrations.size());
    }

    @Test
    void testCreateRegistration(){
        Date dateCreate = new Date(2020);
        Time timeCreate = new Time(10);
        Session sessionCreate = new Session();
        Customer customerCreate = new Customer();

        Integer sessionId = sessionCreate.getId();
        Integer customerId = customerCreate.getId();

        Registration registration = registrationService.createRegistration(dateCreate, timeCreate, sessionId, customerId);

        assertNotNull(registration);
        assertEquals(customerId, registration.getCustomer());
        assertEquals(sessionId, registration.getCustomer());


    }
    @Test
    void testUpdateRegistrationBySession(){
        Registration registration = registrationService.getRegistration(REGISTRATION1_ID);

        assertNotNull(registration);

        assertEquals(SESSION1_ID, registration.getSession().getId());

        Session sessionTest = new Session();
        Integer newSessionId = sessionTest.getId();
        registration.setSession(sessionTest);

        assertEquals(newSessionId, registration.getSession().getId());


    }

    @Test
    void testUpdateRegistrationByCustomer(){
        Registration registration = registrationService.getRegistration(REGISTRATION1_ID);

        assertNotNull(registration);

        assertEquals(CUSTOMER1_ID, registration.getCustomer().getId());

        Customer customerTest = new Customer();
        Integer newCustomerId = customerTest.getId();
        registration.setCustomer(customerTest);

        assertEquals(newCustomerId, registration.getCustomer().getId());

    }

}

