package com.MyFirstProjet.Projet.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long Id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "Qt")
    private int Qt;
    @Column(name = "datecreation")
    private Timestamp datecreation;
    @Column(name = "datemodification")
    private Timestamp datemodification;
    @OneToMany(mappedBy = "categorieid", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Produit> products;


}
