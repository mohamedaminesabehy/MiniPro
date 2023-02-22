package com.myfirstprojet.projet.service;

import com.myfirstprojet.projet.entity.Produit;
import com.myfirstprojet.projet.repository.ProduitRepository;
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



    public Optional<Produit> getproduitbyid(Long id)
    {

        return produitRepository.findById(id);
    }




    public Produit save(Produit produit)
    {

        return produitRepository.saveAndFlush(produit);
    }
    public boolean existsById(Long id){

        return produitRepository.existsById(id);
    }

    public void deleteProduit (Long id)
    {
        produitRepository.deleteById(id);

    }



}
