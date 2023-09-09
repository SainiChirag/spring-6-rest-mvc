package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.Beer;
import com.chirag.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    Map<UUID, Beer> beerMap;
    public BeerServiceImpl() {
        beerMap = new HashMap<>();
        UUID uuid = UUID.randomUUID();
        beerMap.put( uuid, Beer.builder()
                .id(uuid)
                .version(1)
                .beerName("Kingfisher")
                .beerStyle(BeerStyle.GOSE)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build());

        uuid = UUID.randomUUID();
        beerMap.put( uuid, Beer.builder()
                .id(uuid)
                .version(1)
                .beerName("Corona")
                .beerStyle(BeerStyle.IPA)
                .upc("89834")
                .price(new BigDecimal("20.99"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build());

        uuid = UUID.randomUUID();
        beerMap.put( uuid, Beer.builder()
                .id(uuid)
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.WHEAT)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build());

    }


    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get Beer ID in service is called");
        return beerMap.get(id);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer addBeer(Beer beer) {
        Beer newBeer = Beer.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        System.out.println("I am here");


        this.beerMap.put(newBeer.getId(), newBeer);
        return newBeer;
    }

    @Override
    public Beer updateBeer(UUID id, Beer newBeer) {
        Beer existing = beerMap.get(id);
        existing.setBeerName(newBeer.getBeerName());
        existing.setVersion(newBeer.getVersion());
        existing.setBeerStyle(newBeer.getBeerStyle());
        existing.setUpc(newBeer.getUpc());
        existing.setQuantityOnHand(newBeer.getQuantityOnHand());
        existing.setPrice(newBeer.getPrice());
        existing.setUpdatedDate(LocalDateTime.now());
        beerMap.put(id, existing);
        return existing;

    }

    @Override
    public void deleteBeer(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public Beer patchBeer(UUID beerId, Beer newBeer) {
        Beer foundBeer = beerMap.get(beerId);
        if (newBeer.getVersion() != null ) {
            foundBeer.setVersion(newBeer.getVersion());
        }
        if (newBeer.getBeerName() != null ) {
            foundBeer.setBeerName(newBeer.getBeerName());
        }
        if (newBeer.getBeerStyle() != null ) {
            foundBeer.setBeerStyle(newBeer.getBeerStyle());
        }
        if (newBeer.getUpc() != null ) {
            foundBeer.setUpc(newBeer.getUpc());
        }
        if (newBeer.getQuantityOnHand() != null ) {
            foundBeer.setQuantityOnHand(newBeer.getQuantityOnHand());
        }
        if (newBeer.getPrice() != null ) {
            foundBeer.setPrice(newBeer.getPrice());
        }
        foundBeer.setUpdatedDate(LocalDateTime.now());
        beerMap.put(beerId, foundBeer);
        return foundBeer;

    }


}
