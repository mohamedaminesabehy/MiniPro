package com.myfirstprojet.projet.TestS;

import com.myfirstprojet.projet.entity.Categories;
import com.myfirstprojet.projet.entity.Produit;
import com.myfirstprojet.projet.service.CategoriesService;
import com.myfirstprojet.projet.service.ProduitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProduitServiceTest {
    @Autowired
    private ProduitService service;
    @Autowired
    private CategoriesService categoriesService;


    @Test
    public void getProduitTest() {
        List<Produit> produit = service.getProduit();
        assertThat(produit).isNotNull();
    }
    @Test
    public void getProduitByIdTest() {
        Optional<Produit> produit = service.getproduitbyid(1L);
        assertThat(produit).isNotNull();
    }
    @Test
    public void deleteProduitTest() {
        Long id = 1L;
        service.deleteProduit(id);
        Optional<Produit> produit = service.getproduitbyid(id);
        assertThat(produit).isEmpty();
    }
    @Test
    public void testSaveProduit() {
        // Arrange
        Categories categorie = new Categories();
        categorie.setNom("informatique ");
        Categories savedCategorie = categoriesService.save(categorie);
        Produit produit = new Produit();
        produit.setNom("nom de produit");
        produit.setQt(10);
        produit.setDisponible(true);
        produit.setDatecreation(new Timestamp(System.currentTimeMillis()));
        produit.setDatemodification(new Timestamp(System.currentTimeMillis()));
        produit.setCategorieid(savedCategorie);

        // Act
        Produit savedProduit = service.save(produit);

        // Assert
        assertThat(savedProduit).isNotNull();
        assertThat(savedProduit.getId()).isNotNull();
        assertThat(savedProduit.getNom()).isEqualTo(produit.getNom());
        assertThat(savedProduit.getQt()).isEqualTo(produit.getQt());
        assertThat(savedProduit.getDisponible()).isEqualTo(produit.getDisponible());
        assertThat(savedProduit.getDatecreation()).isEqualTo(produit.getDatecreation());
        assertThat(savedProduit.getDatemodification()).isEqualTo(produit.getDatemodification());
    }
}
