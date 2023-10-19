package com.chirag.spring6restmvc.repository;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.entity.BeerOrder;
import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.repository.BeerOrderRepository;
import com.chirag.spring6restmvc.repository.BeerRepository;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setup() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testBeerOrders(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("Test order")
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);


        System.out.println(savedBeerOrder.getCustomerRef());
    }

}