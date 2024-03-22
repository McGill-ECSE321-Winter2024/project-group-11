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
    public Session createSession(Date date, Time startTime, Time endTime, float price, Integer remainingCap, Integer roomNumber, Integer classTypeId) {
        inputValidation(startTime, endTime, price, roomNumber, remainingCap);
        //if a room is occupied during the exact same time, return error.
        verifySessionConflicts(date, startTime, endTime, roomNumber, -1);

        Optional<ClassType> classType = classTypeRepository.findById(classTypeId);

        if (classType.isEmpty()) {
            throw new IllegalArgumentException("Invalid/ Not approved class type.");
        }
        System.out.println("THIS is classType" + classType.get().getName());
        Session session = new Session(date, startTime, endTime, price, remainingCap, roomNumber, classType.get());

        return sessionRepository.save(session);
    }
    private void inputValidation(Time startTime, Time endTime, float price, Integer roomNumber, Integer remainingCap) {
        if (startTime.after(endTime)) {
            throw new IllegalArgumentException("Session start time must be before end time");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Invalid price for session.");
        }
        if (roomNumber < 0) {
            throw new IllegalArgumentException("Invalid room number, must be above 0.");
        }
        if (remainingCap < 1) {
            throw new IllegalArgumentException("Capacity can't be less than 1.");
        }
    }


    @Transactional
    public int updateSession(Date date, Time startTime, Time endTime, float price, Integer remainingCap, Integer roomNumber, Integer classTypeId, Integer id) {
        inputValidation(startTime, endTime, price, remainingCap, roomNumber);

        Optional<Session> sessionObj = sessionRepository.findById(id);
        if (sessionObj.isEmpty()) {
            throw new IllegalArgumentException("Session with id: " + id + " does not exist.");
        }
        Session session = sessionObj.get();
        verifySessionConflicts(date, startTime, endTime, roomNumber, session.getId());

        Optional<ClassType> classType = classTypeRepository.findById(classTypeId);

        if (classType.isEmpty()) {
            throw new IllegalArgumentException("Invalid/ Not approved class type.");
        }
        return sessionRepository.updateSessionById(id, date, startTime, endTime, price, remainingCap, roomNumber, classType.get());
    }

    @Transactional
    public Integer dropInstructorFromSession(Integer sessionId) {
        return sessionRepository.updateSessionByInstructorId(sessionId);
    }

    @Transactional
    public Session addInstructorToSession(Integer sessionId, Integer instructorId) {
        if (instructorId == null || sessionId == null) {
            throw new IllegalArgumentException("Invalid arguments!");
        }

        Optional<Instructor> instructor = instructorRepository.findById(instructorId);
        if (instructor.isEmpty()) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " not found.");
        }
        Optional<Session> session = sessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            throw new IllegalArgumentException("Session not found.");
        }
        Session updatedSession = session.get();
        if (updatedSession.hasInstructor()) {
            throw new IllegalArgumentException("Session already has an instructor");
        }
        updatedSession.setInstructor(instructor.get());
        sessionRepository.save(updatedSession);
        return updatedSession;
    }

    @Transactional
    public List<Session> getAllSessionsByInstructorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Could not parse instructor id");
        }
        return sessionRepository.getSessionsByInstructor_Id(id);

    }


    /**
     * Checks if there are any scheduling conflicts when creating/updating a session.
     * @param date Date of session.
     * @param startTime start time of session.
     * @param endTime end time of session.
     * @param roomNumber room number.
     * @param id Id of session being updated, -1 if being created.
     * @return Error message, empty if no conflicts.
     */
    private void verifySessionConflicts(Date date, Time startTime, Time endTime, Integer roomNumber, Integer id) {

        //checking if room is available during this time period.
        List<Session> sessions = sessionRepository.getSessionsByDateIsAndRoomNumber(date, roomNumber);
        for (Session s: sessions) {
            if (id >= 0 && Objects.equals(s.getId(), id)) {
                continue;
            }
            System.out.println(s.getEndTime());
            if (s.getEndTime().after(startTime)) {
                throw new IllegalArgumentException("Room is not available during this time.");
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
