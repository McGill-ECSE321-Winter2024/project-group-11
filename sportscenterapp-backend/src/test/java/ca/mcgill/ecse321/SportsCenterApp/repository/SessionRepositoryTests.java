package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class SessionRepositoryTests {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @AfterEach
    void clearDatabase() {
        sessionRepository.deleteAll();
        classTypeRepository.deleteAll();
        instructorRepository.deleteAll();
    }

    @Test
    void testFindSessionById() {
        Session one = new Session();
        one.setPrice(10);
        one.setRemainingCapacity(5);

        Instructor jonathan = new Instructor();
        jonathan.setFirstName("Jonathan");
        jonathan.setLastName("Kuminga");
        ClassType classType = new ClassType();
        classType.setName("yoga");
        one.setClassType(classType);
        one.setInstructor(jonathan);

        classTypeRepository.save(classType);
        instructorRepository.save(jonathan);

        Session res = sessionRepository.save(one);
        //write testing
        assertEquals(10, res.getPrice());
        assertEquals("Jonathan", res.getInstructor().getFirstName());
        //read testing
        int id = res.getId();
        res = sessionRepository.findById(id).get();
        //testing if the read operation on the Session object and its references worked.
        assertNotNull(res);
        assertNotNull(res.getClassType());
        //checking if the references match.
        assertEquals("Jonathan", res.getInstructor().getFirstName());
        assertEquals("yoga", res.getClassType().getName());

    }
    @Test
    @Transactional
    void testUpdateSession() {
        Session one = new Session();
        one.setPrice(10);
        one.setRemainingCapacity(5);

        Instructor jonathan = new Instructor();
        jonathan.setFirstName("Jonathan");
        jonathan.setLastName("Kuminga");
        ClassType classType = new ClassType();
        classType.setName("yoga");
        one.setClassType(classType);
        one.setInstructor(jonathan);

        classTypeRepository.save(classType);
        instructorRepository.save(jonathan);

        Session res = sessionRepository.save(one);

        //test updating
        int count = sessionRepository.updateSessionById(res.getId(), new Date(20001), new Time(4),
                new Time(2), 15, 50, 50, classType);

        assertEquals(1, count);

    }


}
