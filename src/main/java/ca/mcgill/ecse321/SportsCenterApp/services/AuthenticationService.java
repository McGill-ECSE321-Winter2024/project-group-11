package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.SportsCenterApp.dto.*;
import ca.mcgill.ecse321.SportsCenterApp.model.*;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import ca.mcgill.ecse321.SportsCenterApp.services.utilities.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;


@Service
public class AuthenticationService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TokenProvider tokenProvider;

    /**
     * Validates an email + password pair for a user type
     *
     * @param credentials DTO containing login credentials and user type
     * @return true is login was successful otherwise false
     */
    @Transactional
    public String logIn(LoginDto credentials) throws Exception {
        switch (credentials.getUserType()) {
            case Owner:
                return authenticateOwner(credentials.getEmail(), credentials.getPassword());
            case Instructor:
                return authenticateInstructor(credentials.getEmail(), credentials.getPassword());
            case Customer:
                return authenticateCustomer(credentials.getEmail(), credentials.getPassword());
            default:
                throw new Exception("Invalid user type.");
        }
    }

    /**
     * Method to logout the user.
     * @param credentials (LoginDto)
     * @throws Exception if wrong user type
     */
    public void logout(LoginDto credentials) throws Exception {
        switch (credentials.getUserType()) {
            case Instructor:
                logoutInstructor(credentials.getEmail());
                break;
            case Owner:
                logoutOwner(credentials.getEmail());
                break;
            case Customer:
                logoutCustomer(credentials.getEmail());
                break;
            default:
                throw new Exception("Invalid user type.");
        }
    }

    /**
     * Validate owner's token
     * @param token token of the owner
     * @return owner (Owner)
     */
    public Owner validateOwnerToken(String token) {
        return ownerRepository.findOwnerByToken(token);
    }

    /**
     * Validate instructor's token
     * @param token token of the instructor
     * @return instructor (Instructor)
     */
    public Instructor validateInstructorToken(String token) {
        return instructorRepository.findInstructorByToken(token);
    }

    /**
     * Validate customer's token
     * @param token token of the customer
     * @return customer (Customer)
     */
    public Customer validateCustomerToken(String token) {
        return customerRepository.findCustomerByToken(token);
    }

    /**
     * Verifies that the correct instructor is logged in with the correct email and password
     * @param email of the instructor
     * @param password of the instructor
     * @return token of the instructor (String)
     * @throws AuthenticationException if invalid password
     */
    private String authenticateInstructor(String email, String password) throws AuthenticationException {
        Instructor ins = instructorRepository.findInstructorByEmail(email);
        if (ins.getPassword().equals(password)) {
            ins.setToken(tokenProvider.createToken(ins.getEmail()));
            return ins.getToken();
        } else {
            throw new AuthenticationException("Invalid password.");
        }
    }

    /**
     * Verifies that the correct owner is logged in with the correct email and password
     * @param email of the owner
     * @param password of the owner
     * @return token of the owner (String)
     * @throws AuthenticationException if invalid password
     */
    private String authenticateOwner(String email, String password) throws AuthenticationException {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner.getPassword().equals(password)) {
            owner.setToken(tokenProvider.createToken(owner.getEmail()));
            return owner.getToken();
        } else {
            throw new AuthenticationException("Invalid password.");
        }
    }

    /**
     * Verifies that the correct customer is logged in with the correct email and password
     * @param email of the customer
     * @param password of the customer
     * @return token of the customer (String)
     * @throws AuthenticationException if invalid password
     */
    private String authenticateCustomer(String email, String password) throws AuthenticationException {
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer.getPassword().equals(password)) {
            customer.setToken(tokenProvider.createToken(customer.getEmail()));
            return customer.getToken();
        } else {
            throw new AuthenticationException("Invalid password.");
        }
    }

    /**
     * Method to logout the instructor
     * @param email of the instructor
     */
    private void logoutInstructor(String email) {
        Instructor ins = instructorRepository.findInstructorByEmail(email);
        ins.setToken(null);
        instructorRepository.save(ins);
    }

    /**
     * Method to logout the customer
     * @param email of the customer
     */
    private void logoutCustomer(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email);
        customer.setToken(null);
        customerRepository.save(customer);
    }

    /**
     * Method to logout the owner
     * @param email of the owner
     */
    private void logoutOwner(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        owner.setToken(null);
        ownerRepository.save(owner);
    }


}