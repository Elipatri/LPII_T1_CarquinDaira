package com.empresa.alquiler.controller;

import com.empresa.alquiler.service.AlquilerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PeliculasController {

    private final AlquilerService alquilerService;

    public PeliculasController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model) {
        model.addAttribute("peliculas", alquilerService.obtenerPeliculas());
        return "peliculas_disponibles";
    }
}
