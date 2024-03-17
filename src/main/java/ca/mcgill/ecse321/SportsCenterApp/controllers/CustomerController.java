package ca.mcgill.ecse321.SportsCenterApp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import ca.mcgill.ecse321.SportsCenterApp.model.Customer;

import ca.mcgill.ecse321.SportsCenterApp.dto.CustomerDto;

import ca.mcgill.ecse321.SportsCenterApp.services.CustomerService;


@RestController
@RequestMapping("/user")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(value = { "/customer/{aId}", "/customer/{aId}/"})
    public CustomerDto createCustomer(@RequestParam("aFirstName") String aFirstName, @RequestParam("aLastName")  String aLastName, @RequestParam("aEmail") String aEmail,@RequestParam("aPassword") String aPassword, 
                                        @RequestParam("aId") Integer aId, @RequestParam("aAccoutBalance") float aAccoutBalance){
                                            try{
                                            Customer customer = customerService.createCustomer(aFirstName, aLastName, aEmail, aPassword, aId, aAccoutBalance);
                                            return CustomerDto.convertToDto(customer);
                                        }catch (Exception e){
                                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating customer", e);
                                        }
                                    }

    @GetMapping(value = { "/customer/{aId}", "/customer/{aId}/"})
    public List<CustomerDto> getCustomer(@RequestParam("aId") Integer aId){
        try{
            Customer customer = customerService.getCustomer(aId);
            return CustomerDto.convertToDto(customer);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating customer", e);
        }
    }

    @PutMapping(value = { "/customer/update-balance/{aId}" , "/customer/update-balance/{aId}/"})
    public
}


