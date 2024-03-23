package ca.mcgill.ecse321.SportsCenterApp.controller;

import ca.mcgill.ecse321.SportsCenterApp.dto.ClassTypeDto;
import ca.mcgill.ecse321.SportsCenterApp.dto.SessionDto;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SessionControllerTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @BeforeEach
    @AfterEach
    void clearDatabaes() {
        sessionRepository.deleteAll();
    }

    @Test
    void testCreateAndGetSession() {
        int id = testCreateSession();
        testGetSession(id);
        testGetAllSessions();
    }
    private int testCreateSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);

        ClassType savedYoga = classTypeRepository.save(yoga);
        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, savedYoga.getId());
        SessionDto session = new SessionDto(new Date(1000), new Time(50), new Time(90), 5, 5, 5, classTypeDto, null);
        ResponseEntity<SessionDto> response = client.postForEntity("/session/", session, SessionDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertTrue(response.hasBody());
        assertEquals(5, response.getBody().getPrice());
        assertTrue(response.getBody().getId() > 0);

        return response.getBody().getId();

    }
    private void testGetSession(int id) {
        ResponseEntity<SessionDto> response = client.getForEntity("/session/" + id, SessionDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.hasBody());
        assertEquals(5, response.getBody().getPrice());
    }

    private void testGetAllSessions() {
        ResponseEntity<List<SessionDto>> response = client.exchange("/session/", HttpMethod.GET, null, new ParameterizedTypeReference<List<SessionDto>>() {
        });
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(5, response.getBody().get(0).getRemainingCapacity());

    }

    @Test
    void testCreateInvalidSession1() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);

        ClassType savedYoga = classTypeRepository.save(yoga);
        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, savedYoga.getId());
        SessionDto session = new SessionDto(new Date(1000), new Time(500000), new Time(10), 5, 5, 5, classTypeDto, null);

        ResponseEntity<String> response = client.postForEntity("/session/", session, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Session start time must be before end time", response.getBody());
    }

    @Test
    void testCreateInvalidSession2() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);

        ClassType savedYoga = classTypeRepository.save(yoga);
        ClassTypeDto classTypeDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, savedYoga.getId());
        SessionDto session = new SessionDto(new Date(1000), new Time(10), new Time(10000), 5, -1, 5, classTypeDto, null);

        ResponseEntity<String> response = client.postForEntity("/session/", session, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Capacity can't be less than 1.", response.getBody());
    }
    @Test
    void testGetInvalidSession() {
        ResponseEntity<String> response = client.getForEntity("/session/" + 1000, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Could not find session with id 1000", response.getBody());
    }

    @Test
    void testAddInstructorToSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Session savedSession = sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 4, 4, savedYoga));
        Instructor dave = new Instructor("jonathan", "kuminga", "g@gmail.com", "password", 3, "goat man");
        Instructor saved = instructorRepository.save(dave);

        String url = "/session/" + savedSession.getId() + "/instructor/" + saved.getId();
        ResponseEntity<SessionDto> response = client.exchange(url, HttpMethod.PUT, null, SessionDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getInstructor());
        assertEquals("jonathan", response.getBody().getInstructor().getFirstName());
        assertEquals(saved.getId(), response.getBody().getInstructor().getId());
    }

    @Test
    void testAddInvalidInstructorToSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Session savedSession = sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 4, 4, savedYoga));

        String url = "/session/" + savedSession.getId() + "/instructor/" + 10;
        ResponseEntity<String> response = client.exchange(url, HttpMethod.PUT, null, String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Instructor with id 10 not found.", response.getBody());
    }

    @Test
    void testDropInstructorFromSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Session savedSession = sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 4, 4, savedYoga));
        Instructor dave = new Instructor("jonathan", "kuminga", "g@gmail.com", "password", 3, "goat man");
        Instructor saved = instructorRepository.save(dave);

        String url = "/session/" + savedSession.getId() + "/instructor";
        ResponseEntity<Integer> response = client.exchange(url, HttpMethod.PUT, null, Integer.class);
    }
    @Test
    void testUpdateSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType pilates = new ClassType("pilates", "2nd description", true, ClassType.DifficultyLevel.Beginner);
        ClassType savedPilates = classTypeRepository.save(pilates);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Session savedSession = sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 4, 4, savedYoga));

        ClassTypeDto pilatesDTO = new ClassTypeDto(savedPilates.getName(), savedPilates.getDescription(), savedPilates.getApproved(), savedPilates.getDifficultyLevel(), savedPilates.getId());
        SessionDto sessionDto = new SessionDto(new Date(1000), new Time(50), new Time(40000), 5, 5, 5, null, pilatesDTO, savedSession.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SessionDto> requestEntity = new HttpEntity<>(sessionDto, headers);
        String url = "/session/" + savedSession.getId();
        ResponseEntity<Integer> response = client.exchange(url, HttpMethod.PUT, requestEntity, Integer.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody());

        Optional<Session> res = sessionRepository.findById(savedSession.getId());
        assertTrue(res.isPresent());
        assertEquals("pilates", res.get().getClassType().getName());
    }

    @Test
    void testUnsuccessfullyUpdateSession() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Session savedSession = sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 4, 4, savedYoga));
        ClassTypeDto yogaDto = new ClassTypeDto("yoga", "description", true, ClassType.DifficultyLevel.Advanced, savedYoga.getId());
        SessionDto sessionDto = new SessionDto(new Date(1000), new Time(5000), new Time(20), 5, 5, 5, null, yogaDto, savedSession.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SessionDto> requestEntity = new HttpEntity<>(sessionDto, headers);
        String url = "/session/" + savedSession.getId();
        ResponseEntity<String> response = client.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Session start time must be before end time", response.getBody());

    }

    @Test
    void testGetAllSessionsByInstructorId() {
        ClassType yoga = new ClassType("yoga", "description", true, ClassType.DifficultyLevel.Advanced);
        ClassType savedYoga = classTypeRepository.save(yoga);
        Instructor dave = new Instructor("jonathan", "kuminga", "g@gmail.com", "password", 3, "goat man");
        Instructor b = new Instructor("bob","looney", "at@gmail.com", "pw", 3, "desc");
        Instructor savedDave = instructorRepository.save(dave);
        Instructor savedB = instructorRepository.save(b);
        sessionRepository.save(new Session(new Date(50), new Time(20), new Time(30000), 6, 20, 5, savedDave, savedYoga));
        sessionRepository.save(new Session(new Date(60), new Time(50), new Time(10000), 4, 4, 9, savedDave, savedYoga));
        sessionRepository.save(new Session(new Date(1000), new Time(50), new Time(10000), 4, 10, 4, savedB, savedYoga));
        ResponseEntity<List<SessionDto>> res = client.exchange("/session/instructor/" + savedDave.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<SessionDto>>() {
        });
        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNotNull(res.getBody());
        assertEquals(2, res.getBody().size());
    }

}
