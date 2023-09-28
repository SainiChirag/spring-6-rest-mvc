package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCSV(File file);
}
