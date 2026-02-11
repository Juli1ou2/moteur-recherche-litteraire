package com.back.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String auteur;

    @Column
    private int nbLignes;

    // Constructeurs
    public Book() {}

    public Book(String titre, String auteur, int nbLignes) {
        this.titre = titre;
        this.auteur = auteur;
        this.nbLignes = nbLignes;
    }

    public Book(String titre) {
        this.titre = titre;
        this.auteur = "";
        this.nbLignes = 0;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", titre='" + titre + "', auteur='" + auteur + ", nbLignes='" + nbLignes + "'}";
    }
}