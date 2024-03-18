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

    @Transactional
    public Registration createRegistration(Date aDate, Time aTime, Customer aCustomer, Session aSession){

        Registration registration = new Registration(aDate, aTime, aCustomer, aSession);

        registrationRepository.save(registration);

        return registration;
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
    public void deleteRegistration(Integer id){
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
            return updatedRegistration;
        }
        else{
            throw new IllegalArgumentException("Registration could not be updated with session id: " + sessionId); //rewrite message
        }
    }

    //update customer
    @Transactional 
    public Registration updateRegistrationByCustomer(Integer registrationId, Integer customerId){
        Optional<Registration> registration = registrationRepository.findById(registrationId);
        Optional<Customer> customer = customerRepository.findById(customerId);
        
        if (registration.isPresent() && customer.isPresent()){
            Registration updatedRegistration = registration.get();
            Customer newCustomer = customer.get();
            updatedRegistration.setCustomer(newCustomer);
            return updatedRegistration;
        }
        else{
            throw new IllegalArgumentException("Registration could not be updated with customer id: " + customerId); //rewrite message
        }
    }

    //commplete other updates if time permits


}
