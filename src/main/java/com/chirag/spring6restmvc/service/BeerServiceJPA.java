package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.mappers.BeerMapper;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<BeerDTO> listBeers() {
        return  beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());
    }

    @Override
    public BeerDTO addBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO newBeer) {
      beerRepository.findById(id).ifPresent((beer -> {
          beer.setBeerName(newBeer.getBeerName());
          beer.setBeerStyle(newBeer.getBeerStyle());
          beer.setPrice(newBeer.getPrice());
          beer.setUpc(newBeer.getUpc());
          beer.setQuantityOnHand(newBeer.getQuantityOnHand());
          beer.setUpdatedDate(LocalDateTime.now());
          beerRepository.save(beer);
      }));
      return beerMapper.beerToBeerDto(beerRepository.findById(id).get());

    }

    @Override
    public void deleteBeer(UUID id) {
        beerRepository.deleteById(id);
    }

    @Override
    public BeerDTO patchBeer(UUID beerId, BeerDTO newBeer) {
        return null;
    }
}
