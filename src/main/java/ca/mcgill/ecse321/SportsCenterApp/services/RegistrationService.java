package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.SportsCenterApp.model.*;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Service
public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;


    //TODO add a check to see if customer is already registered to the session.
    @Transactional
    public Registration createRegistration(Date aDate, Time aTime, Integer customerId, Integer sessionId){
        Optional<Session> session = sessionRepository.findById(sessionId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Could not find session");
        }
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Could not find customer");
        }
        Session aSession = session.get();
        Customer aCustomer = customer.get();
        if (aSession.getRemainingCapacity() < 1) {
            throw new IllegalArgumentException("This session is already full!");
        }
        float sessionPrice = aSession.getPrice();
        if (aCustomer.getAccountBalance() < sessionPrice) {
            throw new IllegalArgumentException("Insufficient funds to register for this session.");
        }

        Registration registration = new Registration(aDate, aTime, aCustomer, aSession);

        //This decrements capacity of session by 1. (update)
        int book = sessionRepository.updateSessionById(aSession.getId(), aSession.getDate(), aSession.getStartTime(), aSession.getEndTime(), aSession.getPrice(), aSession.getRemainingCapacity() - 1, aSession.getRoomNumber(), aSession.getClassType());
        if (book == 0) {
            throw new IllegalArgumentException("Could not get session information.");
        }
        //updates customers balance after registering for session.
        customerService.updateCustomer(aCustomer.getId(), aCustomer.getAccountBalance() - sessionPrice);
        return registrationRepository.save(registration);

    }

    @Transactional
    public Registration getRegistration(Integer id){
        Optional<Registration> registration = registrationRepository.findById(id);
        if (registration.isPresent()){
            return registration.get();
        }
        else {
            throw new IllegalArgumentException("Registration not found for id: " + id);
        }
    }

    //verify
    @Transactional
    public Iterable<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
    
    @Transactional
    public void deleteRegistration(Integer id) {
        Optional<Registration> registration = registrationRepository.findById(id);
        if (registration.isEmpty()) {
            throw new IllegalArgumentException("Could not find registration with id: " + id);
        }
        Registration reg = registration.get();
        Session session = reg.getSession();
        Customer customer = reg.getCustomer();

        // 1. refund customer money if successful
        // 2.  +1 remainingCap to session
        // 4a. if current date is past session date, dont allow.
        customerService.updateCustomer(customer.getId(), customer.getAccountBalance() + session.getPrice());
        int book = sessionRepository.updateSessionById(session.getId(), session.getDate(), session.getStartTime(), session.getEndTime(), session.getPrice(),
                session.getRemainingCapacity() + 1, session.getRoomNumber(), session.getClassType());
        if (book == 0) {
            throw new IllegalArgumentException("Could not update session");
        }
        registrationRepository.deleteById(id);
    }

    //update session
    @Transactional 
    public Registration updateRegistrationBySession(Integer registrationId, Integer sessionId){
        Optional<Registration> registration = registrationRepository.findById(registrationId);
        Optional<Session> session = sessionRepository.findById(sessionId);
        
        if (registration.isPresent() && session.isPresent()){
            Registration updatedRegistration = registration.get();
            Session newSession = session.get();
            updatedRegistration.setSession(newSession);
            return registrationRepository.save(updatedRegistration);
        }
        else{
            throw new IllegalArgumentException("Registration could not be updated with session id: " + sessionId); //rewrite message
        }
    }

    //TODO migbt not be required for the application
    @Transactional 
    public Registration updateRegistrationByCustomer(Integer registrationId, Integer customerId){
        Optional<Registration> registration = registrationRepository.findById(registrationId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        
        if (registration.isPresent() && customer.isPresent()) {
            Registration updatedRegistration = registration.get();
            Customer newCustomer = customer.get();
            updatedRegistration.setCustomer(newCustomer);
            return registrationRepository.save(updatedRegistration);
        }
        else {
            throw new IllegalArgumentException("Registration could not be updated with customer id: " + customerId); //rewrite message
        }
    }

    //commplete other updates if time permits


}
