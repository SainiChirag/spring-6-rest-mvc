package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventoryOnHand,
                            Integer pageNumber, Integer pageSize);

    BeerDTO addBeer(BeerDTO beer);

    BeerDTO updateBeerById(UUID id, BeerDTO newBeer);

    void deleteBeer(UUID id);

    BeerDTO patchBeer(UUID beerId, BeerDTO newBeer);
}
