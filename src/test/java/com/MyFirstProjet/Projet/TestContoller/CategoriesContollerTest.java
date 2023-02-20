package com.MyFirstProjet.Projet.TestContoller;
import com.MyFirstProjet.Projet.Controller.CategoriesContoller;
import com.MyFirstProjet.Projet.entity.Categories;
import com.MyFirstProjet.Projet.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
public class CategoriesContollerTest {

    @Autowired
    private CategoriesService service;
    @Autowired
    private CategoriesContoller controller;

    @Test
    public void addCategoriesTest() {
        Categories categorie = Categories.builder()
                .nom("Electronics")
                .Qt(10)
                .datecreation(new Timestamp(System.currentTimeMillis()))
                .datemodification(new Timestamp(System.currentTimeMillis()))
                .build();

        Categories savedCategories = controller.addCategories(categorie);

        assertThat(savedCategories).isNotNull();
        assertThat(savedCategories.getNom()).isEqualTo("Electronics");
        assertThat(savedCategories.getQt()).isEqualTo(10);
    }
    @Test
    public void deleteCategorieTest() {
        Long id = 39L;
        service.deleteCategories(id);
        Optional<Categories> categorie = service.getcategoriesbyid(id);
        assertThat(categorie).isEmpty();
    }
}







