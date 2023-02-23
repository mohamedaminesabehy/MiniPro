package com.myfirstprojet.projet.TestS;

import org.springframework.beans.factory.annotation.Autowired;

import com.myfirstprojet.projet.entity.Categories;
import com.myfirstprojet.projet.service.CategoriesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
 class CategoriesServiceTest {
    @Autowired
    private CategoriesService service;


    @Test
     void getCategoriesTest() {
        List<Categories> categories = service.getCategories();
        assertThat(categories).isNotNull();
    }
    @Test
     void getCategoriesByIdTest() {
        Optional<Categories> categorie = service.getcategoriesbyid(1L);
        assertThat(categorie).isNotNull();
    }
    @Test
     void deleteCategoriesTest() {
        Long id = 1L;
        service.deleteCategories(id);
        Optional<Categories> categorie = service.getcategoriesbyid(id);
        assertThat(categorie).isEmpty();
    }
    @Test
     void saveCategoriesTest() {
        Categories categorie = Categories.builder()
                .nom("Electronics")
                .qt(10)
                .datecreation(new Timestamp(System.currentTimeMillis()))
                .datemodification(new Timestamp(System.currentTimeMillis()))
                .build();

        Categories savedCategories = service.save(categorie);

        assertThat(savedCategories).isNotNull();
        assertThat(savedCategories.getNom()).isEqualTo("Electronics");
        assertThat(savedCategories.getQt()).isEqualTo(10);
    }

}
