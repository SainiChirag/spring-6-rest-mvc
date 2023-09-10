package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.model.Beer;
import com.chirag.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET, value = "/{beerId}" )
    public ResponseEntity<Beer> getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get beer by Id - in controller");
        Beer returnBeer = beerService.getBeerById(id).orElseThrow(UnknownException::new);
        return new ResponseEntity<>(returnBeer,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> getBeerList() {
        return beerService.listBeers();
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Beer> addBeer(@RequestBody Beer beer) {
            Beer savedBeer =  beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId());
        return new ResponseEntity<Beer>(headers, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{beerId}")
    public ResponseEntity<Beer> updateById(@RequestBody Beer beer, @PathVariable("beerId") UUID id) {

        Beer searchedBeer = beerService.getBeerById(id).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//            throw new NotFoundException();
//        }
        // beer found
        searchedBeer = beerService.updateBeer(id, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + searchedBeer.getId());
        return new ResponseEntity<Beer>(headers, HttpStatus.OK);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{beerId}")
    public ResponseEntity<Beer> deleteBeerById(@PathVariable("beerId") UUID beerId){
        Beer searchedBeer = beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//          throw new NotFoundException();
//        }
        beerService.deleteBeer(beerId);

        return new ResponseEntity<Beer>( HttpStatus.OK);
    }


    @PatchMapping(value = "/{beerId}")
    public ResponseEntity<Beer> patchBeerById(@RequestBody Beer beer, @PathVariable("beerId") UUID id) {

        Beer searchedBeer = beerService.getBeerById(id).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//            throw new NotFoundException();
//        }
        // beer found
        searchedBeer = beerService.patchBeer(id, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + searchedBeer.getId());
        return new ResponseEntity<Beer>(headers, HttpStatus.OK);
    }
}

