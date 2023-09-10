package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<Beer> getBeerById(UUID id);

    List<Beer> listBeers();

    Beer addBeer(Beer beer);

    Beer updateBeer(UUID id, Beer newBeer);

    void deleteBeer(UUID id);

    Beer patchBeer(UUID beerId, Beer newBeer);
}
