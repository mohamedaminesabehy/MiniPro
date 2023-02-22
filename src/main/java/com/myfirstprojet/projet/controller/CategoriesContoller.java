package com.myfirstprojet.projet.controller;
import com.myfirstprojet.projet.entity.Categories;
import com.myfirstprojet.projet.entity.Produit;
import com.myfirstprojet.projet.service.CategoriesService;
import com.myfirstprojet.projet.service.ProduitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins ="http://localhost:4200/")
@RequestMapping("/api/v1")
public class CategoriesContoller {
    @Autowired
    private final CategoriesService categoriesService;
    @Autowired
    private final ProduitService produitService;
    private static final String CATEGORY_NOT_FOUND = "Category not found or matched";
    private static final String MESSAGE_KEYY = "message";

    @Autowired
    public CategoriesContoller(CategoriesService categoriesService, ProduitService produitService) {
        this.categoriesService = categoriesService;
        this.produitService = produitService;
    }
    @GetMapping("/search")
    public List<Categories> search(@RequestParam("q") String query) {

        return categoriesService.search(query);
    }
    @GetMapping("/categorie")
    public List <Categories> getCategories()
    {

        return categoriesService.getCategories();
    }


    @GetMapping("/categorie/{id}")

     public Categories getCategorieId(@PathVariable Long id) {
        return categoriesService.getcategoriesbyid(id).
                orElseThrow(

                        () -> new EntityNotFoundException("Requested task not found")
                );

    }
    @PostMapping("/Categories")
    public Categories addCategories (@RequestBody Categories categories)
    {
        return categoriesService.save(categories);
    }



    @PutMapping("Update/{id}")
    public ResponseEntity<Categories> updateCategorie(@RequestBody Categories categorie, @PathVariable Long id) {
        if (categoriesService.existsById(id)) {
            Categories categorie1 = categoriesService.getcategoriesbyid(id)
                    .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
            categorie1.setNom(categorie.getNom());
            categorie1.setQt(categorie.getQt());
            categorie1.setDatecreation(categorie.getDatecreation());
            categorie1.setDatemodification(categorie.getDatemodification());
            categoriesService.save(categorie1);

            return ResponseEntity.ok().body(categorie1);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put(MESSAGE_KEYY, CATEGORY_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    private static final String MESSAGE_KEY = "message";
    private static final String MESSAGE_CATEGORY_DELETED_SUCCESSFULLY = "Category with id %d deleted successfully";
    private static final String MESSAGE_CATEGORY_NOT_FOUND = "Category with id %d not found";

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HashMap<String, String>> deleteCategorie(@PathVariable Long id) {
        if (categoriesService.existsById(id)) {
            categoriesService.deleteCategories(id);
            String message = String.format(MESSAGE_CATEGORY_DELETED_SUCCESSFULLY, id);
            HashMap<String, String> response = new HashMap<>();
            response.put(MESSAGE_KEY, message);
            return ResponseEntity.ok().body(response);
        } else {
            String message = String.format(MESSAGE_CATEGORY_NOT_FOUND, id);
            HashMap<String, String> response = new HashMap<>();
            response.put(MESSAGE_KEY, message);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/categorieproduit/{id}")
    public ResponseEntity<List<Produit>> getProduitsByCategorieId(@PathVariable Long id) {
        Optional<Categories> categorie = categoriesService.getcategoriesbyid(id);
        if (categorie.isPresent()) {
            List<Produit> produits = categorie.get().getProducts();
            return ResponseEntity.ok(produits);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}



