package com.empresa.alquiler.controller;

import com.empresa.alquiler.service.AlquilerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PeliculasController {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/peliculas")
    public String mostrarPeliculas(Model model) {
        model.addAttribute("peliculas", alquilerService.obtenerPeliculas());
        return "peliculas_disponibles";
    }
}
