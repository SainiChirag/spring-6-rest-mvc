package com.chirag.spring6restmvc.repository;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.repository.BeerRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeerTest() {
        Beer beer = Beer.builder().beerName("ABC").beerStyle(BeerStyle.GOSE).price(new BigDecimal("100")).quantityOnHand(100).upc("abcdef").build();
        Beer savedBeer = beerRepository.save(beer);

        beerRepository.flush();
        assertThat(savedBeer.getBeerName()).isEqualTo(beer.getBeerName());
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void testBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer beer = Beer.builder()
                    .beerName("ABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABCABC")
                    .beerStyle(BeerStyle.GOSE).price(new BigDecimal("100")).quantityOnHand(100).upc("abcdef").build();
            Beer savedBeer = beerRepository.save(beer);

            beerRepository.flush();
        });
    }
}
