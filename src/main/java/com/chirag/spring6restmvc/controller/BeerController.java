package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.model.Beer;
import com.chirag.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET, value = "/{beerId}" )
    public Beer getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get beer by Id - in controller");
        return  beerService.getBeerById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getBeerList() {
        return beerService.listBeers();
    }
}

