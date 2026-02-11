package com.back.back.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IndexInverseId implements Serializable {

    @Column(name = "stem_id")
    private Long stemId;

    @Column(name = "book_id")
    private Long bookId;
}

