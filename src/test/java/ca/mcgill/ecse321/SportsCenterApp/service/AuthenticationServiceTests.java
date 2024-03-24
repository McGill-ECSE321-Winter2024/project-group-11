package ca.mcgill.ecse321.SportsCenterApp.service;

import ca.mcgill.ecse321.SportsCenterApp.dto.LoginDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Owner;
import ca.mcgill.ecse321.SportsCenterApp.repository.CustomerRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.OwnerRepository;
import ca.mcgill.ecse321.SportsCenterApp.services.AuthenticationService;
import ca.mcgill.ecse321.SportsCenterApp.services.utilities.TokenProvider;
import ca.mcgill.ecse321.SportsCenterApp.services.utilities.UserType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {

    final String userEmail = "bobbyshurmda@gmail.com";
    final String userPassword = "aightbruh";
    final String badUserEmail = "kuminga@yahoo.ca";
    final String badUserPassword = "notgood";
    @Mock
    CustomerRepository customerRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    InstructorRepository instructorRepository;
    @Mock
    TokenProvider tokenProvider;
    @InjectMocks
    AuthenticationService authenticationService;

    @BeforeEach
    public void setMockOutputs() {

        lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(userEmail)) {
                Owner owner = new Owner();
                owner.setEmail(userEmail);
                owner.setPassword(userPassword);
                return owner;
            } else {
                throw new EntityNotFoundException();
            }
        });

        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(userEmail)) {
                Customer cus = new Customer();
                cus.setEmail(userEmail);
                cus.setPassword(userPassword);
                return cus;
            } else {
                throw new EntityNotFoundException();
            }
        });

        lenient().when(instructorRepository.findInstructorByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(userEmail)) {
                Instructor ins = new Instructor();
                ins.setEmail(userEmail);
                ins.setPassword(userPassword);
                return ins;
            } else {
                throw new EntityNotFoundException();
            }
        });
    }

    @Test
    public void testValidOwnerLogin() {
        try {
            LoginDto loginDto = new LoginDto();
            loginDto.setEmail(userEmail);
            loginDto.setPassword(userPassword);
            loginDto.setUserType(UserType.Owner);
            authenticationService.logIn(loginDto);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testInvalidOwnerLogin() {
        assertThrows(EntityNotFoundException.class,
                () -> {
                    LoginDto loginDto = new LoginDto();
                    loginDto.setEmail(badUserEmail);
                    loginDto.setPassword(userPassword);
                    loginDto.setUserType(UserType.Owner);
                    authenticationService.logIn(loginDto);
                }
        );
    }

    @Test
    public void testValidInstructorLogin() {
        try {
            LoginDto loginDto = new LoginDto();
            loginDto.setEmail(userEmail);
            loginDto.setPassword(userPassword);
            loginDto.setUserType(UserType.Instructor);
            authenticationService.logIn(loginDto);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testInvalidInstructorLogin() {
        assertThrows(AuthenticationException.class,
                () -> {
                    LoginDto loginDto = new LoginDto();
                    loginDto.setEmail(userEmail);
                    loginDto.setPassword(badUserPassword);
                    loginDto.setUserType(UserType.Instructor);
                    authenticationService.logIn(loginDto);
                }
        );
    }

    @Test
    public void testValidCustomerLogin() {
        try {
            LoginDto loginDto = new LoginDto();
            loginDto.setEmail(userEmail);
            loginDto.setPassword(userPassword);
            loginDto.setUserType(UserType.Customer);
            authenticationService.logIn(loginDto);
        } catch (Exception e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testInvalidCustomerLogin() {
        assertThrows(EntityNotFoundException.class,
                () -> {
                    LoginDto loginDto = new LoginDto();
                    loginDto.setEmail(badUserEmail);
                    loginDto.setPassword(badUserPassword);
                    loginDto.setUserType(UserType.Customer);
                    authenticationService.logIn(loginDto);
                }
        );
    }


}