package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.exception.NotFoundException;
import com.chirag.spring6restmvc.mappers.BeerMapper;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.repository.BeerRepository;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.chirag.spring6restmvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }
    @Test
    void testListBeers(){
        Page<BeerDTO> dtos = beerController.getBeerList(null, null, false, 1, 25);
        assertThat(dtos.getContent().size()).isEqualTo(25);
    }

    @Test
    void testListBeerByName() {
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList(){
        beerRepository.deleteAll();
        Page<BeerDTO> dtos = beerController.getBeerList(null, null, false, 1, 25);
        assertThat(dtos.getContent().size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(0);
     ResponseEntity<BeerDTO> response = beerController.getBeerById(beer.getId());
     BeerDTO dto = response.getBody();
     assertThat(dto).isNotNull();


    }

    @Test
    void testGetBeerIdNotFound() {
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



    @Test
    void listBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                     //   .with(httpBasic("user", "password"))
                        .with(jwt().jwt(jwt->{
                          jwt.claims(claims-> {
                          claims.put("scope","message.read");
                          claims.put("scope","message.write");
                                  }).subject("messaging-client")
                                  .notBefore(Instant.now().minusSeconds(5L));
                        }))
                        .queryParam("beerName", "Gillespie Brown Ale"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));

    }

    @Test
    void listBeersByStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                    //    .with(httpBasic("user", "password"))
                        .with(jwtRequestPostProcessor)
                        .queryParam("beerStyle", "ALE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(25)));

    }

    @Test
    void listBeersByNameAndStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        //.with(httpBasic("user", "password"))
                        .with(jwtRequestPostProcessor)
                        .queryParam("beerStyle", "ALE")
                .queryParam("beerName", "Spirit Animal"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));

    }
    @Test
    void listBeersByNameAndStyleNoBeer() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                       // .with(httpBasic("user", "password"))
                        .with(jwtRequestPostProcessor)
                        .queryParam("beerStyle", "ALE")
                        .queryParam("beerName", "QWERTY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(0)));

    }

    @Test
    void testListBeersAndNameShowInventoryPage2() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                      //  .with(httpBasic("user", "password"))
                        .with(jwtRequestPostProcessor)
                .queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name())
                .queryParam("showInventory", "true")
                .queryParam("pageNumber", "2")
                .queryParam("pageSize", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(50)))
                .andExpect(jsonPath("$.content.[0].quantityOnHand").value(IsNull.nullValue()));

    }

}