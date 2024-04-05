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
        if (!isValidEmailAddress(email)) {
            throw new IllegalArgumentException("Invalid Email address");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password, password must have at least 1 digit, one lowercase, one uppercase, no whitespace and at least 8 character in length");
        }

        if (firstName == null || lastName == null || firstName.isEmpty() || lastName.isEmpty()) {
            throw new IllegalArgumentException("Name fields cannot be empty");
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer != null) {
            throw new IllegalArgumentException("Customer with email exists!");
        }
        customer = new Customer(firstName, lastName, email, password, token, accountBalance);

        customerRepository.save(customer);

        return customer;
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
    public Customer updateCustomerPassword(Integer id, String newPassword) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found for id: " + id);
        }
        Customer updatedCustomer = customer.get();
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password, password must have at least 1 digit, one lowercase, one uppercase, no whitespace and at least 8 character in length");
        }
        updatedCustomer.setPassword(newPassword);
        return customerRepository.save(updatedCustomer);
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

    /**
     * Method to validate an email
     * @param email
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Method to validate a password
     * @param password of instructor
     * @return true if the password is valid
     * At least 1 digit
     * At least one lowercase
     * At least one uppercase
     * no whitespace
     * at least 8 character in length
     */
    public static boolean isValidPassword(String password) {
        String passwordpattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(passwordpattern);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();

    }
}

