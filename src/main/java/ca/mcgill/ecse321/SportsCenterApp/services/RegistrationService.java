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
    private RegistrationService registrationRepository;

    @Transactional
    public Registration createRegistration(Date aDate, Time aTime, Customer aCustomer, Session aSession){

    }


}
