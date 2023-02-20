package com.MyFirstProjet.Projet.repository;

import com.MyFirstProjet.Projet.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    List<Categories> findByNomContainingIgnoreCase(String query);

}