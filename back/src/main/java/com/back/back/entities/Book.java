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
    private int nbPages;

    // Constructeurs
    public Book() {}

    public Book(String titre, String auteur, int nbPages) {
        this.titre = titre;
        this.auteur = auteur;
        this.nbPages = nbPages;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", titre='" + titre + "', auteur='" + auteur + ", nbPages='" + nbPages + "'}";
    }
}