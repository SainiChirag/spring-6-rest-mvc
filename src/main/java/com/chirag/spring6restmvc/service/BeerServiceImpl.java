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
}
