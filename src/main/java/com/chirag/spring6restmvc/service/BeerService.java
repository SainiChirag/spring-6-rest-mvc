package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventoryOnHand);

    BeerDTO addBeer(BeerDTO beer);

    BeerDTO updateBeer(UUID id, BeerDTO newBeer);

    void deleteBeer(UUID id);

    BeerDTO patchBeer(UUID beerId, BeerDTO newBeer);
}
