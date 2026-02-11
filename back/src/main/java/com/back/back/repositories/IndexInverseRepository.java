package com.back.back.repositories;

import com.back.back.entities.IndexInverse;
import com.back.back.entities.IndexInverseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IndexInverseRepository extends JpaRepository<IndexInverse, IndexInverseId> {
    @Query("""
                SELECT ii.book.id, SUM(ii.termFrequency)
                FROM IndexInverse ii
                WHERE ii.stem.id IN :stemIds
                GROUP BY ii.book.id
                ORDER BY SUM(ii.termFrequency) DESC
            """)
    List<Object[]> findByStemIds(@Param("stemIds") List<Integer> stemIds);
}
