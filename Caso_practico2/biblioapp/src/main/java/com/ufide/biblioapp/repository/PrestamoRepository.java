package com.ufide.biblioapp.repository;

import com.ufide.biblioapp.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    @Query("""
            SELECT p
            FROM Prestamo p
            WHERE p.fechaLimite < CURRENT_DATE
              AND p.fechaDevolucion IS NULL
            """)
    List<Prestamo> prestamosAtrasados();

}