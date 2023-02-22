package com.myfirstprojet.projet.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Produit {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "qt")
    private int qt;
    @Column(name = "disponible")
    private Boolean disponible;
    @Column(name = "datecreation")
    private Timestamp datecreation;
    @Column(name = "datemodification")
    private Timestamp datemodification;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="categorieid")
    private Categories categorieid;}
