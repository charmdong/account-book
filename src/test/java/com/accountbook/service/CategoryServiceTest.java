package com.accountbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Test
    void testGetCategoryList() throws Exception {
        var categoryList = categoryService.getCategoryList();
        log.info(" >> category List : {}", categoryList.toString());

    }

    @Test
    void testGetCategoryRank() {
        var rank = categoryService.getCategoryRank("test");
        log.info(" >> category rank : {}", rank.toString());
    }
}
