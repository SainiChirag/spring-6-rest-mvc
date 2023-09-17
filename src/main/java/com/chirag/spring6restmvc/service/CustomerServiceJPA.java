package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.mappers.CustomerMapper;
import com.chirag.spring6restmvc.model.CustomerDTO;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {
        return Optional.ofNullable(customerMapper.customertoCustomerDto(customerRepository.findById(customerId)
                .orElse(null)));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customertoCustomerDto).toList();
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        Customer customer = Customer.builder().name(customerDTO.getName()).createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now()).build();
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customertoCustomerDto(savedCustomer);
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).get();
        customer.setName(customerDTO.getName());
        customer.setUpdatedDate(LocalDateTime.now());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).get();
        customer.setName(customerDTO.getName());
        customer.setUpdatedDate(LocalDateTime.now());
        customerRepository.save(customer);

    }
}
