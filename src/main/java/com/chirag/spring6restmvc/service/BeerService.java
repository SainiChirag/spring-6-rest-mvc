package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
