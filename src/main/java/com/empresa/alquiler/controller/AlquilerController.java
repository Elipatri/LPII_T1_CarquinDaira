package com.empresa.alquiler.controller;

import com.empresa.alquiler.model.Alquiler;
import com.empresa.alquiler.model.enums.EstadoAlquiler;
import com.empresa.alquiler.service.AlquilerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/alquiler")
public class AlquilerController {
    @Autowired
    private AlquilerService alquilerService;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("clientes", alquilerService.obtenerClientes());
        model.addAttribute("peliculas", alquilerService.obtenerPeliculas());
        model.addAttribute("alquileres", alquilerService.obtenerAlquileres());
        return "alquiler_form";
    }

    @PostMapping("/registrar")
    public String registrarAlquiler(
            @RequestParam("idCliente") int idCliente,
            @RequestParam("idPelicula") List<Integer> idPeliculas,
            @RequestParam("cantidad") List<Integer> cantidades,
            Model model) {

        model.addAttribute("clientes", alquilerService.obtenerClientes());
        model.addAttribute("peliculas", alquilerService.obtenerPeliculas());
        model.addAttribute("alquileres", alquilerService.obtenerAlquileres());

        try {
            Alquiler alquiler = alquilerService.registrarAlquiler(idCliente, idPeliculas, cantidades);
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("popupSuccess", true);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("popupError", true);
        }

        return "alquiler_form";
    }

    @PostMapping("/cambiarEstado")
    public String cambiarEstado(@RequestParam("idAlquiler") int id,
                                @RequestParam("estado") String nuevoEstado,
                                Model model) {
        try {
            alquilerService.cambiarEstadoAlquiler(id, EstadoAlquiler.valueOf(nuevoEstado));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "alquiler_error";
        }
        return "redirect:/alquiler/detalle/" + id;
    }


    @GetMapping("/detalle/{id}")
    public String verDetalleAlquiler(@PathVariable("id") int id, Model model) {
        Alquiler alquiler = alquilerService.obtenerAlquilerPorId(id);
        model.addAttribute("alquiler", alquiler);
        return "alquiler_detalle";
    }
}
