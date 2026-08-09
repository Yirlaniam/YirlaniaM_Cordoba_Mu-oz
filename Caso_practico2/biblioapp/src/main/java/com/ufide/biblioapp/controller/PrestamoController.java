package com.ufide.biblioapp.controller;

import com.ufide.biblioapp.entity.Prestamo;
import com.ufide.biblioapp.service.LibroService;
import com.ufide.biblioapp.service.PrestamoService;
import com.ufide.biblioapp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private LibroService libroService;

    @Autowired
    private UsuarioService usuarioService;

    // Listar todos los préstamos
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @GetMapping
    public String listar(Model model) {

        model.addAttribute("prestamos", prestamoService.listar());

        return "prestamos";
    }

    // Mostrar formulario para registrar un préstamo
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("libros", libroService.listar());
        model.addAttribute("usuarios", usuarioService.listar());

        return "prestamo-form";
    }

    // Guardar préstamo
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Prestamo prestamo) {

        prestamoService.guardar(prestamo);

        return "redirect:/prestamos";
    }

    // Registrar devolución
    @PreAuthorize("hasRole('BIBLIOTECARIO')")
    @GetMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id) {

        prestamoService.registrarDevolucion(id);

        return "redirect:/prestamos";
    }

}