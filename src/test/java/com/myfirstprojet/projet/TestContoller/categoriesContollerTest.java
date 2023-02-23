package com.myfirstprojet.projet.TestContoller;
import com.myfirstprojet.projet.controller.CategoriesContoller;
import com.myfirstprojet.projet.entity.Categories;
import com.myfirstprojet.projet.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@SpringBootTest
 class categoriesContollerTest {

    @Autowired
    private CategoriesService service;
    @Autowired
    private CategoriesContoller controller;

    @Test
     void addCategoriesTest() {
        Categories categorie = Categories.builder()
                .nom("Electronics")
                .qt(10)
                .datecreation(new Timestamp(System.currentTimeMillis()))
                .datemodification(new Timestamp(System.currentTimeMillis()))
                .build();

        Categories savedCategories = controller.addCategories(categorie);

        assertThat(savedCategories).isNotNull();
        assertThat(savedCategories.getNom()).isEqualTo("Electronics");
        assertThat(savedCategories.getQt()).isEqualTo(10);
    }
   @Test
   void deleteCategoriesTest() {
      // Arrange
      Categories categorie = Categories.builder()
              .nom("Electronics")
              .qt(10)
              .datecreation(new Timestamp(System.currentTimeMillis()))
              .datemodification(new Timestamp(System.currentTimeMillis()))
              .build();
      Categories savedCategories = service.save(categorie);

      // Act
      service.deleteCategories(savedCategories.getId());
      Optional<Categories> deletedCategories = service.getcategoriesbyid(savedCategories.getId());

      // Assert
      assertThat(deletedCategories).isEmpty();
   }
}







