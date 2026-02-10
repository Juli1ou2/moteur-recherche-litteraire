package com.back.back.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class IndexInverseId implements Serializable {

    @Column(name = "stem_id")
    private Integer stemId;

    @Column(name = "book_id")
    private Long bookId;
}

