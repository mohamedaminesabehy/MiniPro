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
 class categoriesServiceTest {
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
    @Test
    void updateCategorieTest() {
        // Arrange
        Categories categorie = Categories.builder()
                .nom("Electronics")
                .qt(10)
                .datecreation(new Timestamp(System.currentTimeMillis()))
                .datemodification(new Timestamp(System.currentTimeMillis()))
                .build();
        Categories savedCategories = service.save(categorie);

        // Act
        savedCategories.setNom("Mobile");
        Categories updatedCategories = service.save(savedCategories);

        // Assert
        assertThat(updatedCategories).isNotNull();
        assertThat(updatedCategories.getId()).isEqualTo(savedCategories.getId());
        assertThat(updatedCategories.getNom()).isEqualTo("Mobile");
    }
}
