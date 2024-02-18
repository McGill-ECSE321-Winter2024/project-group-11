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
    private String name;
    private String description;
    private boolean approved;
    private DifficultyLevel difficultyLevel;
    @Test
    void testFindClassTypeByDescription() {
        ClassType hockey = new ClassType();
        String name = "Hockey Class";
        String description = "Fun vibes";
        boolean approved = true;

        hockey.setName(name);
        hockey.setDescription(description);
        hockey.setApproved(approved);

        classTypeRepository.save(hockey);



        ClassType result = classTypeRepository.findClassTypeByDescription(description);
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(description, result.getDescription());
        assertEquals(approved, result.getApproved());

    }




    
}