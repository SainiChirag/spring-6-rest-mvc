package com.chirag.spring6restmvc.mappers;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto( Beer beer);

}
