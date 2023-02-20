package com.MyFirstProjet.Projet.service;

import com.MyFirstProjet.Projet.entity.Produit;
import com.MyFirstProjet.Projet.repository.ProduitRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }
    @Autowired
    private CategoriesService categoriesService;

    public List<Produit> getProduit(){

        return produitRepository.findAll();
    }



    public Optional<Produit> getproduitbyid(Long Id)
    {

        return produitRepository.findById(Id);
    }




    public Produit save(Produit produit)
    {

        return produitRepository.saveAndFlush(produit);
    }
    public boolean existsById(Long Id){

        return produitRepository.existsById(Id);
    }

    public void deleteProduit (Long Id)
    {
        produitRepository.deleteById(Id);

    }



}
