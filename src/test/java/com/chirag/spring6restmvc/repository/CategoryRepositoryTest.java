package com.chirag.spring6restmvc.repository;

import com.chirag.spring6restmvc.entity.Beer;
import com.chirag.spring6restmvc.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    Beer testBeer;

    @BeforeEach
    void setup() {
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    @Transactional
    void testAddCetegory() {
        Category category = categoryRepository.save(
                Category.builder().description("Ales").build());

        testBeer.addCategory(category);

        Beer beer = beerRepository.save(testBeer);
        System.out.println(beer);




    }
}
