package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    Optional<CustomerDTO> getCustomerById(UUID customerId);
    List<CustomerDTO> getAllCustomers();
    CustomerDTO saveNewCustomer(CustomerDTO customer);
    void updateCustomerById(UUID customerId, CustomerDTO customer);
    void deleteCustomerById(UUID customerId);
    void patchCustomerById(UUID customerId, CustomerDTO customer);


}
