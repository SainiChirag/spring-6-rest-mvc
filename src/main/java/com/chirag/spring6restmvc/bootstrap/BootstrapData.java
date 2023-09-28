package com.chirag.spring6restmvc.bootstrap;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.entity.Customer;
import com.chirag.spring6restmvc.model.BeerCSVRecord;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.repository.BeerRepository;
import com.chirag.spring6restmvc.repository.CustomerRepository;
import com.chirag.spring6restmvc.service.BeerCSVService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCSVService beerCSVService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        populateBeerData();
        loadCSVData();
        populateCustomerData();
    }

    private void loadCSVData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCSVRecord> beerCSVRecords = beerCSVService.convertCSV(csvFile);

            beerCSVRecords.forEach(beerCSVRecord -> {
                Beer beer1 = Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(inferBeerstype(beerCSVRecord.getStyle()))
                        .upc(beerCSVRecord.getRow().toString())
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(beerCSVRecord.getCount())
                        //.createdDate(LocalDateTime.now())
                        //.updatedDate(LocalDateTime.now())
                        .build();
                beerRepository.save(beer1);
            });
        }
    }

    private BeerStyle inferBeerstype(String stringBeerStyle) {
        switch (stringBeerStyle) {
            case "American Pale Lager" :
                return BeerStyle.LAGER;
            case "American Pale Ale (APA)":
            case "American Black Ale":
            case "Belgian Dark Ale":
            case "American Blonde Ale":
                return BeerStyle.ALE;
            case "American IPA":
            case "American Double / Imperial IPA":
            case "Belgian IPA":
                return BeerStyle.IPA;
            case "American Porter" :
                return BeerStyle.PORTER;
            case "Oatmeal Stout":
            case "American Stout" :
                return BeerStyle.STOUT;
            case "Saison / Farmhouse Ale" :
                return BeerStyle.SAISON;
            case "Fruit / Vegetable Beer":
            case "Winter Warmer":
            case "Berliner Weissbier" :
                return BeerStyle.WHEAT;
            case "English Pale Ale" :
                return BeerStyle.PALE_ALE;
            default:
                return BeerStyle.PILSNER;
        }
    }

    private void populateBeerData(){

        if (beerRepository.count() == 0 ) {
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

    }
    private void populateCustomerData() {

        if (customerRepository.count() == 0) {
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
}
