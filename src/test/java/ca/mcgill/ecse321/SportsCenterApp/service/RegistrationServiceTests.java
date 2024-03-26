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
import static org.mockito.Mockito.when;

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
    private static final Integer SESSION2_ID = 11;
    private static final Integer REGISTRATION2_ID = 2;
    private static final Integer CUSTOMER2_ID = 21;



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
        session.setDate(new Date(100));
        session.setStartTime(new Time(10));
        session.setEndTime(new Time(1000));
        session.setPrice(10);
        session.setRemainingCapacity(5);


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

        lenient().when(sessionRepository.findById(any())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                Session goodSession = new Session();
                goodSession.setId(1);
                goodSession.setStartTime(new Time(10));
                goodSession.setDate(new Date(100));
                goodSession.setEndTime(new Time(120));
                goodSession.setRemainingCapacity(5);
                goodSession.setRoomNumber(5);
                goodSession.setPrice(2);
                return Optional.of(goodSession);
            } else if (invocation.getArgument(0).equals(2)) {
                Session fullSession = new Session();
                fullSession.setRemainingCapacity(0);
                return Optional.of(fullSession);
            } else if (invocation.getArgument(0).equals(3)) {
                Session priceSession = new Session();
                priceSession.setPrice(6);
                priceSession.setRemainingCapacity(6);
                return Optional.of(priceSession);
            }
            return Optional.empty();
        });
        lenient().when(registrationRepository.save(any())).thenAnswer((invocation) -> {
            if (invocation.getArgument(0) instanceof Registration) {
                Customer one = new Customer();
                one.setFirstName("jon");
                Session three = new Session();
                three.setPrice(4);
                Registration newReg = new Registration(new Date(1000), new Time(100), one, three);
                return newReg;
            }
            return null;
        });
        lenient().when(sessionRepository.updateSessionById(anyInt(), any(), any(), any(), anyInt(), anyInt(), anyInt(), any())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return 2;
            }
            return 1;
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
        sessionCreate.setId(4);
        Customer customerCreate = new Customer();
        customerCreate.setId(4);

        Integer sessionId = sessionCreate.getId();
        Integer customerId = customerCreate.getId();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createRegistration(dateCreate, timeCreate,  customerCreate.getId(), sessionCreate.getId());
        });
        assertEquals("Could not find session", exception.getMessage());

        sessionCreate.setId(2);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createRegistration(dateCreate, timeCreate, customerCreate.getId(), sessionCreate.getId());
        });
        assertEquals("Could not find customer", exception.getMessage());

        customerCreate.setId(1);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createRegistration(dateCreate, timeCreate, customerCreate.getId(), sessionCreate.getId());
        });
        assertEquals("This session is already full!", exception.getMessage());

        sessionCreate.setId(3);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createRegistration(dateCreate, timeCreate, customerCreate.getId(), sessionCreate.getId());
        });
        assertEquals("Insufficient funds to register for this session.", exception.getMessage());

        sessionCreate.setId(1);

        exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.createRegistration(dateCreate, timeCreate, customerCreate.getId(), sessionCreate.getId());
        });
        assertEquals("Could not get session information.", exception.getMessage());


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
    void testUpdateRegistrationBySession() {
        Registration registration = registrationService.getRegistration(REGISTRATION1_ID);

        assertNotNull(registration);

        assertEquals(SESSION1_ID, registration.getSession().getId());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.updateRegistrationBySession(REGISTRATION2_ID, SESSION2_ID);
        });
        assertEquals("Registration could not be updated with session id: " + SESSION2_ID, exception.getMessage());

        registrationService.updateRegistrationBySession(1, 1);


    }

    @Test
    void testUpdateRegistrationByCustomer() {
        Registration registration = registrationService.getRegistration(REGISTRATION1_ID);

        assertNotNull(registration);

        assertEquals(CUSTOMER1_ID, registration.getSession().getId());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationService.updateRegistrationBySession(REGISTRATION2_ID, CUSTOMER2_ID);
        });
        assertEquals("Registration could not be updated with session id: " + CUSTOMER2_ID, exception.getMessage());

        registrationService.updateRegistrationBySession(1, 1);


    }

}

