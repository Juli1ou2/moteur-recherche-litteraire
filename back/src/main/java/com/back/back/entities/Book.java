package com.back.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {

    // Getters et Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String author;

    @Column(nullable = true)
    private int nbPages;

    // Constructeurs
    public Book() {}

    public Book(String nom, String author, int nbPages) {
        this.nom = nom;
        this.author = author;
        this.nbPages = nbPages;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", nom='" + nom + "', author='" + author + ", nbPages='" + nbPages + "'}";
    }
}