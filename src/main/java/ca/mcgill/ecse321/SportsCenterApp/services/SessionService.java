package ca.mcgill.ecse321.SportsCenterApp.services;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import ca.mcgill.ecse321.SportsCenterApp.repository.ClassTypeRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.InstructorRepository;
import ca.mcgill.ecse321.SportsCenterApp.repository.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final InstructorRepository instructorRepository;
    private final ClassTypeRepository classTypeRepository;


    public SessionService(SessionRepository sessionRepository, InstructorRepository instructorRepository, ClassTypeRepository classTypeRepository) {
        this.sessionRepository = sessionRepository;
        this.instructorRepository = instructorRepository;
        this.classTypeRepository = classTypeRepository;
    }

    @Transactional
    public List<Session> getAllSessions() {
        return toList(sessionRepository.findAll());
    }

    @Transactional
    public Session getSession(int id) {
        Optional<Session> session = sessionRepository.findById(id);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Could not find session with id " + id);
        }

        return session.get();
    }

    @Transactional
    public Session createSession(Date date, Time startTime, Time endTime, float price, Integer remainingCap, Integer roomNumber, Integer instructorId, Integer classTypeId) {
        //if a room is occupied during the exact same time, return error.
        verifySessionConflicts(date, startTime, endTime, roomNumber, instructorId, -1);
        Instructor instructor = instructorRepository.findById(instructorId).get();
        Optional<ClassType> classType = classTypeRepository.findById(classTypeId);

        if (classType.isEmpty()) {
            throw new IllegalArgumentException("Invalid class type");
        }
        Session session = new Session(date, startTime, endTime, price, remainingCap, roomNumber, instructor, classType.get());

        return sessionRepository.save(session);
    }

    @Transactional
    public int updateSession(Date date, Time startTime, Time endTime, float price, Integer remainingCap, Integer roomNumber, Integer instructorId, Integer classTypeId, Integer id) {
        Optional<Session> sessionObj = sessionRepository.findById(id);
        if (sessionObj.isEmpty()) {
            throw new IllegalArgumentException("Session with id: " + id + " does not exist.");
        }
        Session session = sessionObj.get();
        verifySessionConflicts(date, startTime, endTime, roomNumber, instructorId, session.getId());

        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        Optional<ClassType> classType = classTypeRepository.findById(classTypeId);

        if (classType.isEmpty()) {
            throw new IllegalArgumentException("Invalid class type");
        }
        return sessionRepository.updateSessionById(id, date, startTime, endTime, price, remainingCap, roomNumber, instructor.get(), classType.get());
    }


    /**
     * Checks if there are any scheduling conflicts when creating/updating a session.
     * @param date Date of session.
     * @param startTime start time of session.
     * @param endTime end time of session.
     * @param roomNumber room number.
     * @param instructor Instructor.
     * @param id Id of session being updated, -1 if being created.
     * @return Error message, empty if no conflicts.
     */
    private void verifySessionConflicts(Date date, Time startTime, Time endTime, Integer roomNumber, Integer instructorId, Integer id) {
        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        if (instructor.isEmpty()) {
            throw new IllegalArgumentException("Could not find instructor");

        }
        List<Session> sessions = sessionRepository.getSessionsByDateIsAndRoomNumber(date, roomNumber);
        for (Session s: sessions) {
            if (id >= 0 && Objects.equals(s.getId(), id)) {
                continue;
            }
            if (s.getEndTime().after(startTime)) {
                throw new IllegalArgumentException("Room is not available during this time.");
            }
        }
        sessions = sessionRepository.getSessionsByDateIsAndInstructor(date, instructor.get());
        for (Session s: sessions) {
            if (id >= 0 && Objects.equals(s.getId(), id)) {
                continue;
            }
            if (s.getEndTime().after(startTime)) {
                throw new IllegalArgumentException("Instructor is not available during this time.");
            }
        }

    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t: iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
