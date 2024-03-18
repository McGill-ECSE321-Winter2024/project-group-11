package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.SportsCenterApp.model.*;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import jakarta.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.*;


public class RegistrationService {
    
    @Autowired
    private RegistrationRepository registrationRepository;

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


    


    
    @Transactional
    public void deleteRegistration(Integer id){
        registrationRepository.deleteById(id);
    }



}
