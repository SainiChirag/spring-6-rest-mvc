package com.chirag.spring6restmvc.bootstrap;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.repository.BeerRepository;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        populateBeerData();
        populateCustomerData();

    }

    private void populateBeerData(){

        Beer beer1 = Beer.builder()
                .beerName("Kingfisher")
                .beerStyle(BeerStyle.GOSE)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

       Beer beer2 = Beer.builder()
                .beerName("Corona")
                .beerStyle(BeerStyle.IPA)
                .upc("89834")
                .price(new BigDecimal("20.99"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

       Beer beer3 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.WHEAT)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);

    }
    private void populateCustomerData() {

        Customer customer1 = Customer.builder()
                .name("Customer 1")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .name("Customer 2")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .name("Customer 3")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
    }
}
