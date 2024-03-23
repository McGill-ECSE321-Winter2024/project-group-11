package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.model.Session;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
    List<Session> getSessionsByDateIsAndRoomNumber(Date date, Integer roomNumber);

    List<Session> getSessionsByDateIsAndInstructor(Date date, Instructor instructor);
    List<Session> getSessionsByInstructor_Id(Integer instructorId);

    @Modifying
    @Query("UPDATE Session s SET s.instructor = NULL WHERE s.id = :sessionId")
    int updateSessionByInstructorId(Integer sessionId);

    @Modifying
    @Query("UPDATE Session s SET s.date = :date, s.startTime = :startTime, s.endTime = :endTime, s.price = :price, s.remainingCapacity = :remainingCapacity, " +
            "s.roomNumber = :roomNumber, s.classType = :classType WHERE s.id = :id")
    int updateSessionById(Integer id, Date date, Time startTime, Time endTime, float price, Integer remainingCapacity, Integer roomNumber, ClassType classType);
}
