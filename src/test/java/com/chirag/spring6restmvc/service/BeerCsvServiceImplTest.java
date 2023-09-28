package com.chirag.spring6restmvc.service;

import com.chirag.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
public class BeerCsvServiceImplTest {

  //  @Autowired
    BeerCSVService beerCSVService = new BeerCSVServiceImpl();
    @Test
    void testBeerCSVServiceImpl() throws FileNotFoundException {
        File csvFile = ResourceUtils.getFile("classpath:csvdata/beers.csv");

        List<BeerCSVRecord> recs = beerCSVService.convertCSV(csvFile);
        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}
