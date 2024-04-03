package ca.mcgill.ecse321.SportsCenterApp.controllers;


import ca.mcgill.ecse321.SportsCenterApp.dto.InstructorDto;
import ca.mcgill.ecse321.SportsCenterApp.model.Instructor;
import ca.mcgill.ecse321.SportsCenterApp.utilities.DtoConverter;
import org.apache.coyote.Response;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    @PostMapping(value = { "/customer/", "/customer"})
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto){
                                        try{
                                            Customer customer = customerService.createCustomer(customerDto.getFirstName(), customerDto.getLastName(), customerDto.getEmail(), customerDto.getPassword(), customerDto.getToken(), customerDto.getAccountBalance());
                                            return new ResponseEntity<>(convertToDto(customer), HttpStatus.CREATED);
                                        }catch (Exception e){
                                            System.out.println(e);
                                            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                                        }
                                    }


    @GetMapping(value = { "/customer/{aId}", "/customer/{aId}/"})
    public ResponseEntity<?> getCustomer(@PathVariable("aId") Integer aId){
        try{
            CustomerDto customerDto = DtoConverter.convertToDto(customerService.getCustomer(aId));
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customers")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream().map(i -> convertToDto(i)).collect(Collectors.toList());
    }

    @PutMapping(value = { "/customer/{aId}/update-balance" , "/customer/{aId}/update-balance/"})
    public ResponseEntity<?> updateCustomer(@PathVariable("aId") Integer aId, @RequestParam("updated-balance") float aAccoutBalance){
        try{
            Customer customer = customerService.updateCustomer(aId, aAccoutBalance);

            return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = {"/customer/{aId}" , "/customer/{aId}/"})
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer aId){
        try {
            customerService.deleteCustomer(aId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    private CustomerDto convertToDto(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("There is no such customer");
        }
        CustomerDto customerDto = new CustomerDto(customer.getFirstName(), customer.getLastName(),customer.getEmail(), customer.getPassword(), customer.getId(), customer.getToken(), customer.getAccountBalance());
        return customerDto;
    }
}


