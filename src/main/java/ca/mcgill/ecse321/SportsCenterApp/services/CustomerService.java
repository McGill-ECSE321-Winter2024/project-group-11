package ca.mcgill.ecse321.SportsCenterApp.services;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.SportsCenterApp.model.*;
import ca.mcgill.ecse321.SportsCenterApp.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, String email, String password, Integer id, float accountBalance) {

        Customer customer = new Customer(firstName, lastName, email, password, id, accountBalance);
        return customerRepository.save(customer);

    }

    @Transactional
    public Customer getCustomer(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            return customer.get();
        }
        else {
            throw new IllegalArgumentException("Customer not found for id: " + id);
        }
    }

    @Transactional
    public Customer updateCustomer(Integer id, float accountBalance){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            Customer updatedCustomer = customer.get();
            updatedCustomer.setAccoutBalance(accountBalance);
            return updatedCustomer;
        }
        else{
            throw new IllegalArgumentException("Customer not found for id: "+ id);
        }
    }

    @Transactional
    public void deleteCustomer(Integer id){
        customerRepository.deleteById(id);
    }

}
