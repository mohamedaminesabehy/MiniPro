package com.myfirstprojet.projet.controller;

import com.myfirstprojet.projet.excelfileexporter.PDFGenerator;
import com.myfirstprojet.projet.excelfileexporter.ProduitExcelExporter;
import com.myfirstprojet.projet.entity.Categories;
import com.myfirstprojet.projet.entity.Produit;
import com.myfirstprojet.projet.repository.ProduitRepository;
import com.myfirstprojet.projet.service.ProduitService;
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
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ProduitController {
   @Autowired
    private ProduitService produitService;
    @Autowired
    private ProduitRepository produitRepository;
    private static final String MESSAGE_KEYY = "message";

    private static final String CATEGORY_NOT_FOUND = "Category not found or matchedd";


    @GetMapping("/produit")
    public List<Produit> getProduit() {

        return produitService.getProduit();
    }


    @GetMapping("/produit/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitService.getproduitbyid(id)
                .orElseThrow(() -> new EntityNotFoundException("Requested product not found"));
    }

    @PostMapping("/Produit")
    public Produit addProduit(@RequestBody Produit produit) {

        return produitService.save(produit);
    }
    public static final String ERROR_MESSAGE = "Requested task not found";

    @PutMapping("/Produit/{id}")
    public ResponseEntity<Produit> update(@RequestBody Produit produit, @PathVariable Long id) {
        if (produitService.existsById(id)) {
            Produit produit1 = produitService.getproduitbyid(id).orElseThrow(
                    () -> new EntityNotFoundException(ERROR_MESSAGE)
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

            return ResponseEntity.ok().body(produit1);
        } else {
            HashMap<String,String> message = new HashMap<>();
            message.put(MESSAGE_KEYY, ERROR_MESSAGE);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("Produit/{id}")
    public ResponseEntity<HashMap<String, String>> deleteproduit(@PathVariable Long id) {
        if (produitService.existsById(id)) {
            produitService.deleteProduit(id);
            HashMap<String, String> message = new HashMap<>();
            message.put(MESSAGE_KEYY, "Categorie with id " + id + "deleted successfully.");
            //returned type hashmap I
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put(MESSAGE_KEYY, id + " task not found or matched");
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
    public ResponseEntity<InputStreamResource> downloadPDF() {
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
