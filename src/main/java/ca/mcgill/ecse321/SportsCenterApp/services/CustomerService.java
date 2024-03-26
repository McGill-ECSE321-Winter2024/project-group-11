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
    public Customer createCustomer(String firstName, String lastName, String email, String password, String token, float accountBalance) {
        Customer customer = new Customer(firstName, lastName, email, password, token, accountBalance);
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
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
    public List<Customer> getAllCustomers(){
        return toList(customerRepository.findAll());
    }

    @Transactional
    public Customer updateCustomer(Integer id, float accountBalance){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            Customer updatedCustomer = customer.get();
            updatedCustomer.setAccountBalance(accountBalance);
            customerRepository.save(updatedCustomer);
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


    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable){
            resultList.add(t);
        }
        return resultList;
    }
}

