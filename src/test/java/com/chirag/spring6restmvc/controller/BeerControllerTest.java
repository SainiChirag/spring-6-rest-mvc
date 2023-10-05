package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.mappers.BeerMapper;
import com.chirag.spring6restmvc.mappers.BeerMapperImpl;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.service.BeerService;
import com.chirag.spring6restmvc.service.BeerServiceImpl;
import com.chirag.spring6restmvc.service.BeerServiceJPA;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;


//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    //@Autowired
    //BeerController beerController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //in case of testing via webmvc (test splices) user defined Beans are not initialized so we have to declare them
    //manually for them to be managed by WebMvc
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl;// = new BeerServiceImpl();
    BeerServiceJPA beerServiceJPA;
    BeerMapper beerMapper = new BeerMapperImpl();

    @BeforeEach
    void setup() {
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerById() throws Exception {
        BeerDTO testBeer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        given(beerService.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));
        // System.out.println(beerController.getBeerById(UUID.randomUUID()));
        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }


    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers(null, null, false, 1, 25)).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25));

        mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().size())));
    }

    @Test
    void testCreateNewBeer() throws Exception {
        //  given(beerService.addBeer())
        Beer beer = beerMapper.beerDtoToBeer(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0));
        beer.setVersion(null);
        beer.setId(null);
        beer.setCreatedDate(null);
        beer.setUpdatedDate(null);

        given(beerService.addBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));

        String beerJson = objectMapper.writeValueAsString(beer);

        mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateBeer() throws Exception {
        BeerDTO beer = beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(0);
        given(beerService.updateBeer(any(), any())).willReturn(beer);

        mockMvc.perform(put("/api/v1/beer/", beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)));
               // .andExpect()
    }

    @Test
    void testCreateBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();

        given(beerService.addBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.listBeers(null, null, false, 1, 25).getContent().get(1));

        MvcResult result =  mockMvc.perform(post("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}