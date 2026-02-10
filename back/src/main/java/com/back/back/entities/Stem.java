package com.back.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stem")
public class Stem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String stem;

    @Column(nullable = false)
    private int frequency;

    public Stem() {
    }

    public Stem(Long id, String stem, int frequency) {
        this.id = id;
        this.stem = stem;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Stem{" +
                "id=" + id +
                ", stem='" + stem + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
