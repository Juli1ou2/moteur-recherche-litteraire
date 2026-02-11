package com.back.back.repositories;

import com.back.back.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
                SELECT b
                FROM Book b
                WHERE b.titre LIKE CONCAT('%', :titre, '%')
            """)
    Optional<Book> findByTitre(@Param("titre") String titre);
}
