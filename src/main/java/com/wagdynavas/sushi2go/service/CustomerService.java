package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Customer;
import com.wagdynavas.sushi2go.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Boolean validateCustomer(@Valid Customer customer, BindingResult result) {
        return result.hasErrors();
    }
}
