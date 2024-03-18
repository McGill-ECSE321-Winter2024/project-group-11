package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.User;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Transactional
@Repository
@Table(name = "user_table")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByFirstNameIgnoreCase(String firstName);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE lower(u.email) = lower(:email) ")
    //this finds user by their email, and then allows them to update their password.
    int updateUserByEmailIgnoreCase(@Param("email")String email, @Param("password") String password);
}
