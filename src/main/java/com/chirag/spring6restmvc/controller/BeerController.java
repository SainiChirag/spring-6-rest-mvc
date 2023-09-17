package com.chirag.spring6restmvc.controller;

import com.chirag.spring6restmvc.exception.NotFoundException;
import com.chirag.spring6restmvc.exception.UnknownException;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;
    @RequestMapping(method = RequestMethod.GET, value = "/{beerId}" )
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Get beer by Id - in controller");
        BeerDTO returnBeer = beerService.getBeerById(id).orElseThrow(UnknownException::new);
        return new ResponseEntity<>(returnBeer,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BeerDTO> getBeerList() {
        return beerService.listBeers();
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<BeerDTO> addBeer(@Validated @RequestBody BeerDTO beer) {
            BeerDTO savedBeer =  beerService.addBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{beerId}")
    public ResponseEntity<BeerDTO> updateById(@RequestBody BeerDTO beer, @PathVariable("beerId") UUID id) {

       beerService.getBeerById(id).orElseThrow(NotFoundException::new);
        //BeerDTO searchedBeer = beerService.getBeerById(id).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//            throw new NotFoundException();
//        }
        // beer found
        BeerDTO searchedBeer = beerService.updateBeer(id, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + searchedBeer.getId());
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{beerId}")
    public ResponseEntity<BeerDTO> deleteBeerById(@PathVariable("beerId") UUID beerId){
        beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
      //  BeerDTO searchedBeer = beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//          throw new NotFoundException();
//        }
        beerService.deleteBeer(beerId);

        return new ResponseEntity<>( HttpStatus.OK);
    }


    @PatchMapping(value = "/{beerId}")
    public ResponseEntity<BeerDTO> patchBeerById(@RequestBody BeerDTO beer, @PathVariable("beerId") UUID id) {

        beerService.getBeerById(id).orElseThrow(NotFoundException::new);
       // BeerDTO searchedBeer = beerService.getBeerById(id).orElseThrow(NotFoundException::new);
//        if (searchedBeer == null ) { // beer not found
//            throw new NotFoundException();
//        }
        // beer found
        BeerDTO searchedBeer = beerService.patchBeer(id, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + searchedBeer.getId());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}

