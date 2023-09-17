package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.mappers.CustomerMapper;
import com.chirag.spring6restmvc.model.CustomerDTO;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerControllerIntegrationTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerController customerController;

    @Test
    void testGetAllCustomer() {
        List<CustomerDTO> dtos = customerController.getAllCustomers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetACustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId()).getBody();
        assertThat(customerDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback
    void testUpdateCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerMapper.customertoCustomerDto(customer);
        dto.setName("New customer name");
        customerController.updateCustomer(dto, customer.getId());

      //  assertThat()

    }

    @Test
    @Transactional
    @Rollback
    void testDeleteCustomer() {

    }



    @Test
    void testPatchCustomer() {

    }
}
