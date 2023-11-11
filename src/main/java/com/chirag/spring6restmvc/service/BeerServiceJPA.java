package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.mappers.BeerMapper;
import com.chirag.spring6restmvc.model.BeerDTO;
import com.chirag.spring6restmvc.model.BeerStyle;
import com.chirag.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 25;


    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, boolean showInventoryOnHand,
                                   Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize);

        Page<Beer> beerPage;
        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerPage = listBeersByName(beerName, pageRequest);
        }
        else if(beerStyle!= null && StringUtils.isEmpty(beerName)) {
            beerPage = listBeersByStyle(beerStyle, pageRequest);
        }
        else if (StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
        }
        else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if ( !showInventoryOnHand ) {
            for (Beer beer: beerPage) {
                beer.setQuantityOnHand(null);
            }

        }
        return beerPage.map(beerMapper::beerToBeerDto);
               /* beerList.stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList());*/
    }

    private Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle, Pageable pageable) {
        return beerRepository.findBeerByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" +beerName + "%", beerStyle, pageable);
    }

    private Page<Beer> listBeersByName(String beerName, Pageable pageable) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%", pageable);
    }

    @Override
    public BeerDTO addBeer(BeerDTO beer) {
        beer.setCreatedDate(LocalDateTime.now());
        beer.setUpdatedDate(LocalDateTime.now());
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public BeerDTO updateBeerById(UUID id, BeerDTO newBeer) {
      beerRepository.findById(id).ifPresent((beer -> {
          beer.setBeerName(newBeer.getBeerName());
          beer.setBeerStyle(newBeer.getBeerStyle());
          beer.setPrice(newBeer.getPrice());
          beer.setUpc(newBeer.getUpc());
          beer.setQuantityOnHand(newBeer.getQuantityOnHand());
          beer.setVersion(newBeer.getVersion());
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

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        }
        else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else if (pageSize > 1000 ) {
            queryPageSize = 1000;
        }
        else queryPageSize = pageSize;

        Sort sort = Sort.by(Sort.Order.asc("beerName"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}
