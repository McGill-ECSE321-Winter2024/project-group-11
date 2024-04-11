package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType.DifficultyLevel;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.services.ClassTypeService;
import ca.mcgill.ecse321.SportsCenterApp.services.ClassTypeService;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;


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
public class ClassTypeServiceTests {


    @Mock
    private ClassTypeRepository classTypeRepository;
    @InjectMocks
    private ClassTypeService classTypeService;




    private static final String C1_NAME = "ESPORTS";
    private static final String C1_DESC = "League";
    private static final boolean C1_APPROVED = true;
    private static final DifficultyLevel C1_LEVEL = DifficultyLevel.Advanced;
    private static final int C1_ID = 1;

    private static final String C2_NAME = "Valorant";
    private static final String C2_DESC = "easy";
    private static final boolean C2_APPROVED = false;
    private static final DifficultyLevel C2_LEVEL = DifficultyLevel.Beginner;
    private static final int C2_ID = 2;




    @BeforeEach
    void setMockOutput() {

        ClassType C1 = new ClassType(C1_NAME, C1_DESC,C1_APPROVED, C1_LEVEL, C1_ID);
        C1.setId(C1_ID);

        ClassType C2 = new ClassType(C2_NAME, C2_DESC, C2_APPROVED, C2_LEVEL, C2_ID);
        C2.setId(C2_ID);
      

        List<ClassType> allClassTypes = new ArrayList<>(List.of(C1, C2));


        lenient().when(classTypeRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(C1);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(C2);
            } else {
                return Optional.empty();
            }
        });


        lenient().when(classTypeRepository.findClassTypeByName(any(String.class))).thenAnswer(invocation -> {
            String name = invocation.getArgument(0);
            if (name.equals(C1_NAME)) {
                return C1;
            } else if (name.equals(C2_NAME)) {
                return C2;
            } else {
                return null;
            }
        });



        lenient().when(classTypeRepository.findAll()).thenReturn(allClassTypes);

        lenient().when(classTypeRepository.save(any(ClassType.class))).thenAnswer((InvocationOnMock invocation) -> {
            ClassType classType = invocation.getArgument(0);
            allClassTypes.add(classType);
            return classType;
        });



        lenient().doAnswer(invocation -> {
            int id = invocation.getArgument(0);

            if (id == C1_ID) {
                allClassTypes.removeIf(classType -> classType.getId() == C1_ID);
            } else if (id == C2_ID){
                allClassTypes.removeIf(classType -> classType.getId() == C2_ID);
            }
            return null;
        }).when(classTypeRepository).deleteById(anyInt());
    
    }


    @Test
    public void testCreateClassType(){

        String invalidName1 = "";
        String invalidName2 = null;

        String invalidDesc1 = "";
        String invalidDesc2 = null;
        
        boolean approved = true;

        DifficultyLevel invalidlevel1= null;

        String validName = "Soccer";
        String validDesc = "ball goes brrr";
        DifficultyLevel validLevel = DifficultyLevel.Intermediate;



        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.creatClassType(invalidName1, validDesc, approved, validLevel);
        });
        assertEquals("Name cannot be empty!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.creatClassType(invalidName2, validDesc, approved, validLevel);
        });
        assertEquals("Name cannot be empty!", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.creatClassType(validName, invalidDesc1, approved, validLevel);
        });
        assertEquals("description cannot be empty!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.creatClassType(validName, invalidDesc2, approved, validLevel);
        });
        assertEquals("description cannot be empty!", exception.getMessage());



        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.creatClassType(validName, validDesc, approved, invalidlevel1);
        });
        assertEquals("Difficulty Level cannot be null!", exception.getMessage());


        //Successfull creation

        ClassType createdClassType = classTypeService.creatClassType(validName, validDesc, approved, validLevel);

        assertNotNull(createdClassType);
        assertEquals(validName, createdClassType.getName());
        assertEquals(validDesc, createdClassType.getDescription());
        assertEquals(approved, createdClassType.getApproved());
        assertEquals(validLevel, createdClassType.getDifficultyLevel());
        assertEquals(3, classTypeService.getAllClassTypes().size());


    }




    @Test
    public void testGetAllClassTypes(){

        List<ClassType> classTypes = null;
        List<ClassType> approvedClassTypes = null;

        try {
            classTypes = classTypeService.getAllClassTypes();
            approvedClassTypes = classTypeService.getAllApprovedClassTypes();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            //fail();
        }

        assertEquals(2, classTypes.size());
        assertEquals(1, approvedClassTypes.size());


    }

    @Test
    public void testGetClassTypes() {


        String invalidName1 = "notExist";
        String invalidName2 = "";
        String invalidName3 = null;
        Integer invalidId1 = 3;
        Integer invalidId2 = null;



        //By name

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.getClassType(invalidName1);
        });
        assertEquals("Class " + invalidName1 + " not found", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.getClassType(invalidName2);
        });
        assertEquals("Class " + invalidName2 + " not found", exception.getMessage());


        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.getClassType(invalidName3);
        });
        assertEquals("Class " + invalidName3 + " not found", exception.getMessage());


        //By ID


        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.getClassType(invalidId1);
        });
        assertEquals("Class not found for id: " + invalidId1, exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.getClassType(invalidId2);
        });
        assertEquals("Class not found for id: " + invalidId2, exception.getMessage());



        //Valid names and id

         //valid emails and ids
        
        ClassType ClassTypeName = classTypeService.getClassType(C1_NAME);
        ClassType ClassTypeId = classTypeService.getClassType(1);

        //Assertions

        assertNotNull(ClassTypeName);
        assertEquals(C1_NAME, ClassTypeName.getName());
        assertEquals(C1_DESC, ClassTypeName.getDescription());
        assertEquals(C1_APPROVED, ClassTypeName.getApproved());
        assertEquals(C1_LEVEL, ClassTypeName.getDifficultyLevel());
        assertEquals(C1_ID, ClassTypeName.getId());
        

        assertNotNull(ClassTypeId);
        assertEquals(C1_NAME, ClassTypeId.getName());
        assertEquals(C1_DESC, ClassTypeId.getDescription());
        assertEquals(C1_APPROVED, ClassTypeId.getApproved());
        assertEquals(C1_LEVEL, ClassTypeId.getDifficultyLevel());
        assertEquals(C1_ID, ClassTypeId.getId());


    }

    @Test
    public void testUpdateClassType(){

        String validName = "valid";
        String validDesc = "validDesc";
        DifficultyLevel level = DifficultyLevel.Beginner;


        String invalidName1 = "";
        String invalidDesc = "";

        Integer invalidId1 = 3;
        Integer invalidId2 = null;


        //invalids id
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.updateClassType(invalidId1, validName, validDesc, level);
        });
        assertEquals("ClassType not found for id: " + invalidId1, exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.updateClassType(invalidId2, validName, validDesc, level);
        });
        assertEquals("ClassType not found for id: " + invalidId2, exception.getMessage());



        //Invalid parameters

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.updateClassType(C1_ID, invalidName1, validDesc, level);
        });
        assertEquals("Name cannot be empty!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.updateClassType(C1_ID, validName, invalidDesc, level);
        });
        assertEquals("description cannot be empty!", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            classTypeService.updateClassType(C1_ID, invalidName1, invalidDesc, level);
        });
        assertEquals("Name cannot be empty!", exception.getMessage());


        //Valid things


        //Updating only the name

        ClassType classTypeName = classTypeService.updateClassType(C1_ID, validName, null, null);
        assertNotNull(classTypeName);
        assertEquals(validName, classTypeName.getName());
        assertEquals(C1_DESC, classTypeName.getDescription());
        assertEquals(C1_APPROVED, classTypeName.getApproved());
        assertEquals(C1_LEVEL, classTypeName.getDifficultyLevel());


        //Updating only the desc, we still have the previous name

        ClassType classTypeDesc = classTypeService.updateClassType(C1_ID, null, validDesc, null);
        assertNotNull(classTypeDesc);
        assertEquals(validName, classTypeDesc.getName());
        assertNotEquals(C1_NAME, classTypeDesc.getName());
        assertEquals(validDesc, classTypeDesc.getDescription());
        assertEquals(C1_APPROVED, classTypeDesc.getApproved());
        assertEquals(C1_LEVEL, classTypeDesc.getDifficultyLevel());
        
        //Updating only the difficulty level


        ClassType classTypeLevel = classTypeService.updateClassType(C1_ID, null, null, level);
        assertNotNull(classTypeLevel);
        assertEquals(validName, classTypeLevel.getName());
        assertNotEquals(C1_NAME, classTypeLevel.getName());
        assertEquals(validDesc, classTypeLevel.getDescription());
        assertNotEquals(C1_DESC, classTypeLevel.getDescription());
        assertEquals(C1_APPROVED, classTypeLevel.getApproved());
        assertEquals(level, classTypeLevel.getDifficultyLevel());

        //Revert back to everything except the difficulty (change name and desc at the same time)

        ClassType classTypeRevert = classTypeService.updateClassType(C1_ID, C1_NAME , C1_DESC, null);
        assertNotNull(classTypeRevert);
        assertNotEquals(validName, classTypeRevert.getName());
        assertEquals(C1_NAME, classTypeRevert.getName());
        assertNotEquals(validDesc, classTypeRevert.getDescription());
        assertEquals(C1_DESC, classTypeRevert.getDescription());
        assertEquals(C1_APPROVED, classTypeRevert.getApproved());
        assertEquals(level, classTypeRevert.getDifficultyLevel());
        assertNotEquals(C1_LEVEL, classTypeRevert.getDifficultyLevel());


    }


    @Test 
    public void testApproveDisaprove(){

        //Not gonna bother writing test for checking id, its the same thing as the update method, they are just separated into different methods.

        ClassType classTypeApproved = classTypeService.approveClassType(2);
        assertNotNull(classTypeApproved);
        assertEquals(true, classTypeApproved.getApproved());
        classTypeService.disapproveClassType(2);
        assertEquals(false, classTypeApproved.getApproved());


    }


    @Test
    public void testDeleteClassType(){

        assertEquals(2, classTypeService.getAllClassTypes().size());
        assertEquals(1, classTypeService.getAllApprovedClassTypes().size());

        Integer invalidId1 = 3;
        Integer invalidId2 = null;


        //invalids id (nothing gets deleted)
        classTypeService.deleteClassType(invalidId1);
        assertEquals(2, classTypeService.getAllClassTypes().size());
        assertEquals(1, classTypeService.getAllApprovedClassTypes().size());
        


        classTypeService.deleteClassType(invalidId2);
        assertEquals(2, classTypeService.getAllClassTypes().size());
        assertEquals(1, classTypeService.getAllApprovedClassTypes().size());


        //delete the approved class type

        classTypeService.deleteClassType(C1_ID);
        assertEquals(1, classTypeService.getAllClassTypes().size());
        assertEquals(0, classTypeService.getAllApprovedClassTypes().size());

        


    }





}
