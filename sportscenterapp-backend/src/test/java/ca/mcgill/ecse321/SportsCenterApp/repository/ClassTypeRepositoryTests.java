package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")

public class ClassTypeRepositoryTests {
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @AfterEach
    void clearDatabase() {
        classTypeRepository.deleteAll();
    }

    @Test
    void testFindClassTypeByName() {
        ClassType hockey = new ClassType();
        ClassType soccer = new ClassType();
        ClassType basketball = new ClassType();
        hockey.setName("Hockey Class");
        hockey.setDescription("fun hockey");
        hockey.setDifficultyLevel(DifficultyLevel.Advanced);
        soccer.setName("Soccer Class");
        basketball.setName("Basketball Class");

        classTypeRepository.save(hockey);
        classTypeRepository.save(soccer);

        //Save
        ClassType result = classTypeRepository.save(basketball);

        
        //write test case
        assertNotNull(result);
        assertEquals(basketball, result);
        assertEquals("Hockey Class", hockey.getName());
        assertEquals("Soccer Class", soccer.getName());

        //read test case
        ClassType readResult = classTypeRepository.findClassTypeByName("Hockey Class");
        assertNotNull(readResult);
        assertEquals("Hockey Class", readResult.getName());
        assertEquals("fun hockey", readResult.getDescription());
        assertEquals(DifficultyLevel.Advanced, readResult.getDifficultyLevel());

    
    }

}
