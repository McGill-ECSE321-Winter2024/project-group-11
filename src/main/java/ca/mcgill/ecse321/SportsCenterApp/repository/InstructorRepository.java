package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    List<Instructor> findInstructorsByFirstName(String firstName);

    Instructor findInstructorByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Instructor i SET i.email = :newEmail WHERE i.email = :currentEmail ")
    int updateInstructorEmailByEmail(@Param("currentEmail") String currentEmail, @Param("newEmail") String newEmail);

    @Transactional
    @Modifying
    @Query("DELETE FROM Instructor i WHERE i.email = :email")
    void deleteByEmail(String email);

    Instructor findInstructorByToken(String token);
}
