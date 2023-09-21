package com.chirag.spring6restmvc.repositories;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.model.CustomerDTO;
import com.chirag.spring6restmvc.repository.BeerRepository;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void saveBeerTest() {
        Customer customer = Customer.builder().name("TestCustomer").build();
        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.flush();
        assertThat(savedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
    }

}
