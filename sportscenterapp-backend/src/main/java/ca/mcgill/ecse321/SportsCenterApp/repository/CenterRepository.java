package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Center;
import jakarta.persistence.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
@Table(name = "center_infos")
public interface CenterRepository extends CrudRepository<Center, Integer>{
    
}
