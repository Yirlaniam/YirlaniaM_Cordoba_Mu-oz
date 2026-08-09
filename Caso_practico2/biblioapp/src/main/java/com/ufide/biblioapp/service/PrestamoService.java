package com.ufide.biblioapp.service;

import com.ufide.biblioapp.entity.Libro;
import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroService libroService;

    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    public Optional<Prestamo> buscarPorId(Long id) {
        return prestamoRepository.findById(id);
    }

    public Prestamo guardar(Prestamo prestamo) {

        Libro libro = prestamo.getLibro();

        if (libro.getCopiasDisponibles() <= 0) {
            throw new RuntimeException("No hay copias disponibles para este libro.");
        }

        // Si no se indicó la fecha, se usa la fecha actual
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
        }

        // La fecha límite siempre es 14 días después
        prestamo.setFechaLimite(
                prestamo.getFechaPrestamo().plusDays(14)
        );

        // Descontar una copia del libro
        libroService.descontarCopia(libro);

        return prestamoRepository.save(prestamo);
    }

    public Prestamo registrarDevolucion(Long id) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (prestamo.getFechaDevolucion() == null) {

            prestamo.setFechaDevolucion(LocalDate.now());

            // Devolver una copia al inventario
            libroService.devolverCopia(prestamo.getLibro());
        }

        return prestamoRepository.save(prestamo);
    }

    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }

}