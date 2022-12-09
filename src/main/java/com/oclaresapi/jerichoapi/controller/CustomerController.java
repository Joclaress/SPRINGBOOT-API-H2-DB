package com.oclaresapi.jerichoapi.controller;

import com.oclaresapi.jerichoapi.exception.ResourceNotFoundException;
import com.oclaresapi.jerichoapi.model.Customer;
import com.oclaresapi.jerichoapi.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;


@RestController
@RequestMapping("")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);

    }
    @PostMapping("/customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @DeleteMapping("/customer/{id}")
    public Map< String, Boolean > deleteCustomer(@PathVariable(value = "id") Long costumerId)
            throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(costumerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer not found for this id :: " + costumerId));

        customerRepository.delete(customer);
        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer>updateCustomer(@PathVariable(value = "id") Long customerId,
                                                      @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));

        customer.setEmail(customerDetails.getEmail());
        customer.setLastName(customerDetails.getLastName());
        customer.setFirstName(customerDetails.getFirstName());
        final Customer updatedEmployee = customerRepository.save(customer);
        return ResponseEntity.ok(updatedEmployee);
    }
}