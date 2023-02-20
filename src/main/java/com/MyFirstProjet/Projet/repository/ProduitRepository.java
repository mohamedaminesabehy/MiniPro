package com.MyFirstProjet.Projet.repository;


import com.MyFirstProjet.Projet.entity.Categories;
import com.MyFirstProjet.Projet.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit,Long> {




}
