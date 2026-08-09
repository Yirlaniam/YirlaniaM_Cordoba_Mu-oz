package com.ufide.biblioapp.controller;

import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoRestController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/atrasados")
    public List<Prestamo> prestamosAtrasados() {
        return prestamoRepository.prestamosAtrasados();
    }

}