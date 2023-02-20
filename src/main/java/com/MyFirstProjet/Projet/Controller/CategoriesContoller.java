package com.MyFirstProjet.Projet.Controller;
import com.MyFirstProjet.Projet.entity.Categories;
import com.MyFirstProjet.Projet.entity.Produit;
import com.MyFirstProjet.Projet.service.CategoriesService;
import com.MyFirstProjet.Projet.service.ProduitService;
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


    @GetMapping("/categorie/{Id}")

     public Categories getCategorieId(@PathVariable Long Id) {
        return categoriesService.getcategoriesbyid(Id).
                orElseThrow(

                        () -> new EntityNotFoundException("Requested task not found")
                );

    }
    @PostMapping("/categories")
    public Categories addCategories (@RequestBody Categories categories)
    {
        return categoriesService.save(categories);
    }

    @PutMapping("Update/{Id}")
    public ResponseEntity<?> UpdateCategorie (@RequestBody Categories categorie, @PathVariable Long Id)
    {
        if (categoriesService.existsById(Id))
        {
            Categories categorie1 = categoriesService.getcategoriesbyid(Id).
                    orElseThrow
                (
                        ()->new EntityNotFoundException ("Requested task not found")
                );
            categorie1.setNom(categorie.getNom());
            categorie1.setQt(categorie.getQt());
            categorie1.setDatecreation(categorie.getDatecreation());
            categorie1.setDatemodification(categorie.getDatemodification());
            categoriesService.save(categorie1);

             //returned type Task
            return ResponseEntity.ok(). body (categorie1);
        }
        else
        {
            HashMap<String,String> message = new HashMap<>();
            message.put("message", Id + " task not found or matched");
           //returned type hashmap
            return ResponseEntity.status (HttpStatus.NOT_FOUND).body (message);
        }
        }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable Long id)
    {
        if (categoriesService.existsById(id))
        {
            categoriesService.deleteCategories(id);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Categorie with id " + id + "deleted successfully.");
            //returned type hashmap I
            return ResponseEntity.status (HttpStatus. NOT_FOUND).body(message);
        }
        else
        {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", id + " task not found or matched");
            //returned type hashmap
            return ResponseEntity.status (HttpStatus.NOT_FOUND).body(message);
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



