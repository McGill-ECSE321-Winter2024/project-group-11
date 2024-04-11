package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.ClassType;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name = "classtype_table")
public interface ClassTypeRepository extends CrudRepository<ClassType, Integer> {
    ClassType findClassTypeByName(String name);
}

