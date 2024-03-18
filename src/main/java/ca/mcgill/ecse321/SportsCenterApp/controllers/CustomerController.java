package ca.mcgill.ecse321.SportsCenterApp.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.CustomerService;


@RestController
@RequestMapping("/user")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = { "/customer/{aId}", "/customer/{aId}/"})
    public CustomerDto createCustomer(@RequestParam("aFirstName") String aFirstName, @RequestParam("aLastName")  String aLastName, @RequestParam("aEmail") String aEmail,@RequestParam("aPassword") String aPassword,
                                        @PathVariable("aId") Integer aId, @RequestParam("aAccoutBalance") float aAccoutBalance){
                                            try{
                                            Customer customer = customerService.createCustomer(aFirstName, aLastName, aEmail, aPassword, aId, aAccoutBalance);
                                            return convertToDto(customer);
                                        }catch (Exception e){
                                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating customer", e);
                                        }
                                    }

    @GetMapping(value = { "/customer/{aId}", "/customer/{aId}/"})
    public CustomerDto getCustomer(@RequestParam("aId") Integer aId){
        try{
            Customer customer = customerService.getCustomer(aId);
            return convertToDto(customer);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating customer", e);
        }
    }

    @PutMapping(value = { "/customer/update-balance/{aId}" , "/customer/update-balance/{aId}/"})
    public CustomerDto updateCustomer(@PathVariable("aId") Integer aId, @RequestParam("aAccoutBalance") float aAccoutBalance){
        try{
            Customer customer = customerService.updateCustomer(aId, aAccoutBalance);
            return convertToDto(customer);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating customer", e);
        }
    }

    @DeleteMapping(value = {"/customer/delete/{aId}" , "/customer/delete/{aId}/"})
    public void deleteCustomer(@PathVariable("aId") Integer aId){
        customerService.deleteCustomer(aId);
    }

    private CustomerDto convertToDto(Customer customer){
        if (customer == null) {
            throw new IllegalArgumentException("There is no such customer");
        }
        CustomerDto customerDto = new CustomerDto(customer.getFirstName(), customer.getLastName(),customer.getEmail(), customer.getPassword(), customer.getId(), customer.getAccoutBalance());
        return customerDto;
    }
}


