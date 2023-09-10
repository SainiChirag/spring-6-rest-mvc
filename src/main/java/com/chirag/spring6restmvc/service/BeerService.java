package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    List<BeerDTO> listBeers();

    BeerDTO addBeer(BeerDTO beer);

    BeerDTO updateBeer(UUID id, BeerDTO newBeer);

    void deleteBeer(UUID id);

    BeerDTO patchBeer(UUID beerId, BeerDTO newBeer);
}
