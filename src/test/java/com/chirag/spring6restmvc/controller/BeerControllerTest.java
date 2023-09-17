package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.service.BeerService;
import com.chirag.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

  //in case of testing via webmvc (test splices) user defined Beans are not initialized so we have to declare them
  //manually for them to be managed by WebMvc
  @MockBean
  BeerService beerService;

  BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception {
      BeerDTO testBeer = beerServiceImpl.listBeers().get(0);
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
      given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());

      mockMvc.perform(get("/api/v1/beer")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(beerServiceImpl.listBeers().size())));
    }
}