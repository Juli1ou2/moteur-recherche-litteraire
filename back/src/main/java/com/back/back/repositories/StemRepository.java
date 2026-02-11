package com.back.back.repositories;

import com.back.back.entities.Stem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StemRepository extends JpaRepository<Stem, Long> {

    Optional<Stem> findByStem(String stem);

    @Query("SELECT s FROM Stem s")
    List<Stem> findAllStems();

    @Query("""
                SELECT s
                FROM Stem s
                ORDER BY s.frequency DESC
            """)
    List<Stem> findTopStems(org.springframework.data.domain.Pageable pageable);

    @Query("""
                SELECT s
                FROM Stem s
                WHERE s.stem LIKE CONCAT(:prefix, '%')
            """)
    List<Stem> findByPrefix(@Param("prefix") String prefix);
}
