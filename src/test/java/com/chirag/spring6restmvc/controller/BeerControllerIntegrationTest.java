package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.exception.NotFoundException;
import com.chirag.spring6restmvc.exception.UnknownException;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testListBeers(){
        List<BeerDTO> dtos = beerController.getBeerList();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        beerRepository.deleteAll();
        List<BeerDTO> dtos = beerController.getBeerList();
        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(0);
     ResponseEntity<BeerDTO> response = beerController.getBeerById(beer.getId());
     BeerDTO dto = response.getBody();
     assertThat(dto).isNotNull();


    }

    @Test
    void testGetBeerIdNotFoung() {
        // Beer beer = beerRepository.findAll().get(0);
        ResponseEntity<BeerDTO> response = beerController.getBeerById(UUID.randomUUID());
//        assertThrows(UnknownException.class, () -> {
//            beerController.getBeerById(UUID.randomUUID());
//        });
    }


    @Test
    void testAddBeer() {
        List<BeerDTO> dtos = beerController.getBeerList();
        Beer beer = Beer.builder().build();
    }

}