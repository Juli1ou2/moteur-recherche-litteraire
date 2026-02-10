package com.back.back.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "index_inverse")
public class IndexInverse {
    @EmbeddedId
    private IndexInverseId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stemId")
    @JoinColumn(
            name = "stem_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_index_inverse_stem")
    )
    private Stem stem;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(
            name = "book_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_index_inverse_book")
    )
    private Book book;

    @Column(nullable = false)
    private Integer termFrequency;
}
