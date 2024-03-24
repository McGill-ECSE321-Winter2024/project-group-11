package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
import ca.mcgill.ecse321.SportsCenterApp.services.SessionService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SessionServiceTests {
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private ClassTypeRepository classTypeRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    void setMockOutput() {
        Session one = new Session();
        one.setPrice(10);
        one.setRemainingCapacity(5);
        one.setId(1);
        Instructor jonathan = new Instructor();
        jonathan.setFirstName("Jonathan");
        jonathan.setLastName("Kuminga");
        ClassType classType = new ClassType();
        classType.setName("yoga");
        one.setClassType(classType);
        one.setInstructor(jonathan);

        Session two = new Session();
        two.setPrice(20);
        two.setId(2);

        List<Session> allSessions = List.of(one, two);

        lenient().when(sessionRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(one);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(two);
            } else {
                return Optional.empty();
            }
        });

        lenient().when(sessionRepository.findAll()).thenReturn(allSessions);

        lenient().when(instructorRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(new Instructor("jonathan", "kuminga", "s@gmail", "abcd", 3, "BIO"));
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

        lenient().when(classTypeRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                ClassType yogaClass = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
                return Optional.of(yogaClass);
            }
            return Optional.empty();
        });
        lenient().when(sessionRepository.save(any())).thenReturn(new Session(new Date(1000), new Time(150), new Time(400), 3, 3, 2,
                new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced)));

    }
    @Test
    void testGetSession() {
        Session session = null;
        try {
            session = sessionService.getSession(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertNotNull(session);
        assertEquals(10, session.getPrice());
    }

    @Test
    void testGetAllSessions() {
        List<Session> sessions = null;
        try {
            sessions = sessionService.getAllSessions();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertEquals(2, sessions.size());

    }

    @Test
    void testAddInstructorToSession() {
        assertThrows(IllegalArgumentException.class, () -> {
            sessionService.addInstructorToSession(null, null); // Call service method with invalid input
        });

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.addInstructorToSession(null, null);
        });
        assertEquals("Invalid arguments!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.addInstructorToSession(2, 0);
        });
        assertEquals("Instructor with id 0 not found.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.addInstructorToSession(0, 1);
        });
        assertEquals("Session not found.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.addInstructorToSession(1, 1);
        });
        assertEquals("Session already has an instructor", exception.getMessage());

        Session res = sessionService.addInstructorToSession(2, 1);
        assertEquals("jonathan", res.getInstructor().getFirstName());

    }

    @Test
    void testCreateSession() {
        Date date = new Date(1000);
        Time startTime = new Time(50);
        Time endTime = new Time(30);


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, startTime, endTime, 0, 0, 0, 0);
        });
        assertEquals("Session start time must be before end time", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, startTime, new Time(80), -1, 0, -1, 0);
        });
        assertEquals("Invalid price for session.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, startTime, new Time(80), 3, 0, -1, 0);
        });
        assertEquals("Invalid room number, must be above 0.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, startTime, new Time(80), 3, 0, 2, 0);
        });
        assertEquals("Capacity can't be less than 1.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, new Time(150), new Time(400), 3, 3, 1, 0);
        });
        assertEquals("Room is not available during this time.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.createSession(date, new Time(150), new Time(400), 3, 3, 2, 0);
        });
        assertEquals("Invalid/ Not approved class type.", exception.getMessage());

        Session res = sessionService.createSession(date, new Time(150), new Time(400), 3, 3, 2, 1);
        assertEquals(3, res.getPrice());
        assertEquals("yoga", res.getClassType().getName());

    }

    @Test
    void testUpdateSession() {
        //NOTE: The Illega;ArgumentExceptions are the same from createsession.

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.updateSession(new Date(1000), new Time(150), new Time(400), 3, 3, 2, 1, 4);
        });
        assertEquals("Session with id: 4 does not exist.", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            sessionService.updateSession(new Date(1000), new Time(150), new Time(400), 3, 3, 2, 0, 2);
        });
        assertEquals("Invalid/ Not approved class type.", exception.getMessage());

    }




}
