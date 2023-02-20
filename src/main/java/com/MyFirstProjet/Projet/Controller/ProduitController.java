package com.MyFirstProjet.Projet.Controller;

import com.MyFirstProjet.Projet.ExcelFileExporter.PDFGenerator;
import com.MyFirstProjet.Projet.ExcelFileExporter.ProduitExcelExporter;
import com.MyFirstProjet.Projet.entity.Categories;
import com.MyFirstProjet.Projet.entity.Produit;
import com.MyFirstProjet.Projet.repository.CategoriesRepository;
import com.MyFirstProjet.Projet.repository.ProduitRepository;
import com.MyFirstProjet.Projet.service.ProduitService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ProduitController {
   @Autowired
    private ProduitService produitService;
    @Autowired
    private ProduitRepository produitRepository;
    private final CategoriesRepository categoriesRepository;

    @GetMapping("/produit")
    public List<Produit> getProduit() {

        return produitService.getProduit();
    }


    @GetMapping("/produit/{Id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitService.getproduitbyid(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested product not found"));
    }

    @PostMapping("/Produit")
    public Produit addProduit(@RequestBody Produit produit) {

        return produitService.save(produit);
    }
    @PutMapping("/Produit/{Id}")
    public ResponseEntity<?> update (@RequestBody Produit produit, @PathVariable Long Id)
    {
        if (produitService.existsById(Id))
        {
            Produit produit1 = produitService.getproduitbyid(Id).
                    orElseThrow
                            (
                                    ()->new EntityNotFoundException("Requested task not found")
                            );
            produit1.setNom(produit.getNom());
            produit1.setQt(produit.getQt());
            produit1.setDisponible(produit.getDisponible());
            produit1.setDatecreation(produit.getDatecreation());
            produit1.setDatemodification(produit.getDatemodification());
            Categories categorie = produit.getCategorieid();
            if (categorie != null) {
                produit.setCategorieid(categorie);
            }
            produitService.save(produit1);

            //returned type Task
            return ResponseEntity.ok(). body (produit1);
        }
        else
        {
            HashMap<String,String> message = new HashMap<>();
            message.put("message", Id + " task not found or matched");
            //returned type hashmap
            return ResponseEntity.status (HttpStatus.NOT_FOUND).body (message);
        }
    }

    @DeleteMapping("Produit/{id}")
    public ResponseEntity<?> deleteproduit(@PathVariable Long id) {
        if (produitService.existsById(id)) {
            produitService.deleteProduit(id);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Categorie with id " + id + "deleted successfully.");
            //returned type hashmap I
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", id + " task not found or matched");
            //returned type hashmap
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }

    @GetMapping("/produits/download")
    public ResponseEntity<InputStreamResource> downloadProduits() throws IOException {
        List<Produit> produits = produitRepository.findAll();

        ByteArrayInputStream in = ProduitExcelExporter.produitsToExcel(produits);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=produits.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @GetMapping(value = "/downloadPDF")
    public ResponseEntity<InputStreamResource> downloadPDF() throws IOException {
        List<Produit> produits = produitRepository.findAll();

       ByteArrayInputStream bis = PDFGenerator.produitPDFReport(produits);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=produits.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


}
