package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
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
public class OwnerServiceTests {

    @Mock
    private OwnerRepository ownerRepository;
    @InjectMocks
    private OwnerService ownerService;



    private static final String OWNER1_FIRST_NAME = "lee";
    private static final String OWNER1_LAST_NAME = "dee";
    private static final String OWNER1_EMAIL = "leeddee@gmail.com";
    private static final String OWNER1_PASSWORD = "lededede!";
    private static final int OWNER1_ID = 1;
    private static final String OWNER1_TOKEN = "blabla";

    private static final String OWNER2_FIRST_NAME = "yo";
    private static final String OWNER2_LAST_NAME = "yoyo";
    private static final String OWNER2_EMAIL = "yoyoyo@mcdo.ca";
    private static final String OWNER2_PASSWORD = "324!";
    private static final int OWNER2_ID = 2;
    private static final String OWNER2_TOKEN = "hahaha";



    @BeforeEach
    void setMockOutput() {

        Owner o1 = new Owner();
        o1.setFirstName(OWNER1_FIRST_NAME);
        o1.setLastName(OWNER1_LAST_NAME);
        o1.setEmail(OWNER1_EMAIL);
        o1.setPassword(OWNER1_PASSWORD);
        o1.setToken(OWNER1_TOKEN);
        o1.setId(OWNER1_ID);


        Owner o2 = new Owner();
        o2.setFirstName(OWNER2_FIRST_NAME);
        o2.setLastName(OWNER2_LAST_NAME);
        o2.setEmail(OWNER2_EMAIL);
        o2.setPassword(OWNER2_PASSWORD);
        o2.setToken(OWNER2_TOKEN);
        o1.setId(OWNER2_ID);


        List<Owner> allOwners = new ArrayList<>(List.of(o1, o2));

        lenient().when(ownerRepository.findById(anyInt())).thenAnswer(invocation -> {
            if (invocation.getArgument(0).equals(1)) {
                return Optional.of(o1);
            } else if (invocation.getArgument(0).equals(2)) {
                return Optional.of(o2);
            } else {
                return Optional.empty();
            }
        });


        lenient().when(ownerRepository.findOwnerByEmail(any(String.class))).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            if (email.equals(OWNER1_EMAIL)) {
                return o1;
            } else if (email.equals(OWNER2_EMAIL)) {
                return o2;
            } else {
                return null;
            }
        });


        lenient().when(ownerRepository.findAll()).thenReturn(allOwners);

        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer((InvocationOnMock invocation) -> {
            Owner owner = invocation.getArgument(0);
            allOwners.add(owner);
            return owner;
        });
    }


    @Test
    public void testCreateOwner(){
        {
            Owner owner = ownerService.createOwner(OWNER1_FIRST_NAME, OWNER1_LAST_NAME, OWNER1_EMAIL, OWNER1_PASSWORD, OWNER1_TOKEN);

            assertNotNull(owner);
            assertEquals(OWNER1_FIRST_NAME, owner.getFirstName());
            assertEquals(OWNER1_LAST_NAME, owner.getLastName());
            assertEquals(OWNER1_EMAIL, owner.getEmail());
            assertEquals(OWNER1_PASSWORD, owner.getPassword());
            assertEquals(OWNER1_TOKEN, owner.getToken());
        }
    }

    @Test
    void testGetOwner() {
        {
            Owner owner = ownerService.getOwner(OWNER1_ID);
            assertNotNull(owner);
            assertEquals(OWNER1_FIRST_NAME, owner.getFirstName());
            assertEquals(OWNER1_LAST_NAME, owner.getLastName());
            assertEquals(OWNER1_EMAIL, owner.getEmail());
            assertEquals(OWNER1_PASSWORD, owner.getPassword());
            assertEquals(OWNER1_TOKEN, owner.getToken());
        }
    }



    @Test
    public void testGetAllOwners(){
        List<Owner> owners = null;
        try{
            owners = ownerService.getAllOwners();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertEquals(2, owners.size());
    }


    @Test
    public void testUpdateOwnerPassword(){
        Owner owner = ownerService.getOwner(OWNER1_ID);
        assertNotNull(owner);
        assertEquals(OWNER1_FIRST_NAME, owner.getFirstName());
        assertEquals(OWNER1_LAST_NAME, owner.getLastName());
        assertEquals(OWNER1_EMAIL, owner.getEmail());
        assertEquals(OWNER1_PASSWORD, owner.getPassword());
        assertEquals(OWNER1_TOKEN, owner.getToken());

        String newPassword = "newPassword123!";
        owner.setPassword(newPassword);
        assertEquals(newPassword, owner.getPassword());

    }
}
