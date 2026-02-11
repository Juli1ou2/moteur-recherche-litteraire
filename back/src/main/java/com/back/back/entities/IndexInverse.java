package com.back.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "index_inverse",
        uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "stem_id"}))
public class IndexInverse {
    @EmbeddedId
    private IndexInverseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stemId")
    @JoinColumn(name = "stem_id")
    private Stem stem;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(nullable = false)
    private Integer termFrequency;

    public IndexInverse(Stem stem, Book book, Integer termFrequency) {
        this.id = new IndexInverseId(stem.getId(), book.getId());
        this.stem = stem;
        this.book = book;
        this.termFrequency = termFrequency;
    }
}
