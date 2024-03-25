package ca.mcgill.ecse321.SportsCenterApp.utilities;

import ca.mcgill.ecse321.SportsCenterApp.dto.*;
import ca.mcgill.ecse321.SportsCenterApp.model.*;

import java.util.ArrayList;
import java.util.List;

public class DtoConverter {
    public static SessionDto convertToDto(Session session) {
        if (session == null) {
            return null;
        }
        ClassTypeDto classTypeDto = convertToDto(session.getClassType());
        InstructorDto instructorDto = convertToDto(session.getInstructor());


        return new SessionDto(session.getDate(), session.getStartTime(), session.getEndTime(), session.getPrice(), session.getRemainingCapacity(), session.getRoomNumber(), instructorDto, classTypeDto, session.getId());
    }

    public static ClassTypeDto convertToDto(ClassType ClassType){
        if (ClassType == null) {
            return null;
        }
        return new ClassTypeDto(ClassType.getName(), ClassType.getDescription(), ClassType.getApproved(), ClassType.getDifficultyLevel(), ClassType.getId());

    }

    public static InstructorDto convertToDto(Instructor instructor){
        if (instructor == null) {
            return null;
        }
        return new InstructorDto(instructor.getFirstName(), instructor.getLastName(),instructor.getEmail(), instructor.getPassword(), instructor.getId(), instructor.getToken(), instructor.getYearsOfExperience(), instructor.getBiography());

    }

    public static RegistrationDto convertToDto(Registration registration) {
        if (registration == null) {
            return null;
        }
        SessionDto sessionDto = convertToDto(registration.getSession());
        CustomerDto customerDto = convertToDto(registration.getCustomer());
        return new RegistrationDto(registration.getDate(), registration.getTime(), customerDto, sessionDto, registration.getId());
    }

    public static CustomerDto convertToDto(Customer customer){
        if (customer == null) {
            return null;
        }
        return new CustomerDto(customer.getFirstName(), customer.getLastName(),customer.getEmail(), customer.getPassword(), customer.getId(), customer.getToken(), customer.getAccountBalance());
    }
    public static List<RegistrationDto> convertRegistrationsToDtos(Iterable<Registration> registrations) {
        List<RegistrationDto> registrationDtos = new ArrayList<>();
        for (Registration registration : registrations) {
            registrationDtos.add(convertToDto(registration));
        }
        return registrationDtos;
    }
}
