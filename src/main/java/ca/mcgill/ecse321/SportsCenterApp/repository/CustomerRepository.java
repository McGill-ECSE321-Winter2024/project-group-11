package ca.mcgill.ecse321.SportsCenterApp.repository;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    //query method that returns a list of customers who all share the same first name. without case sensitivity.
    List<Customer> findCustomersByFirstNameIgnoreCase(String firstName);
}
