package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.exception.NotFoundException;
import com.chirag.spring6restmvc.exception.UnknownException;
import com.chirag.spring6restmvc.mappers.BeerMapper;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


// TODO Update tests ---- right now the tests are not fully functional -- some of them are failing and have to code a
// according to specifications
// these tests are more for "Unit testing" the Repository etc functions. THe controller specific tests are present
// in the Controller Test class
@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

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
//        ResponseEntity<BeerDTO> response = beerController.getBeerById(UUID.randomUUID());
//        assertThrows(UnknownException.class, () -> {
//            beerController.getBeerById(UUID.randomUUID());
//        });
    }


    @Rollback
    @Transactional
    @Test
    void testAddBeer() {
        BeerDTO beer = BeerDTO.builder().beerName("test beer").build();
        ResponseEntity response = beerController.addBeer(beer);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(response.getHeaders().getLocation()).isNotNull();

        String[] location = response.getHeaders().getLocation().getPath()
                .split("/");
        UUID savedUUID = UUID.fromString(location[4]);
        Beer savedBeer = beerRepository.findById(savedUUID).get();
        assertThat(savedBeer).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void testUpdateBeer() {
        Beer beerToUpdate = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beerToUpdate);
        beerDTO.setBeerName("Updated Beer Name");
        beerDTO.setId(null);
        beerDTO.setId(null);

        ResponseEntity beerResponse =
                beerController.updateById(beerDTO,
                beerToUpdate.getId());
        assertThat(beerResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beerToUpdate.getId()).get();

        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer Name");

    }

    @Rollback
    @Transactional
    @Test
    void testUpdateBeerNotFound() {
        BeerDTO beerDTO = BeerDTO.builder().build();
        beerDTO.setBeerName("Updated Beer Name");

       assertThrows(NotFoundException.class,  ()-> beerController.updateById(beerDTO,
               UUID.randomUUID()));

    }

    @Rollback
    @Transactional
    @Test
    void testDeleteBeer() {
        Beer beer = beerRepository.findAll().get(0);
        UUID beerID = beer.getId();
        ResponseEntity responseEntity = beerController.deleteBeerById(beerID);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));

        assertThat(beerRepository.findById(beerID).orElse(null)).isNull();

    }

    @Test
    @Transactional
    @Rollback
    void testDeleteBeerNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.deleteBeerById(UUID.randomUUID());
        });
    }

}