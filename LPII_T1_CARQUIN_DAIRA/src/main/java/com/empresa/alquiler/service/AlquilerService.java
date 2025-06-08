package com.empresa.alquiler.service;

import com.empresa.alquiler.model.*;
import com.empresa.alquiler.model.enums.EstadoAlquiler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlquilerService {

    private EntityManager em;

    public AlquilerService(EntityManager em) {
        this.em = em;
    }

    public void registrarAlquiler(int idCliente, List<Pelicula> peliculas, List<Integer> cantidades) {
        Cliente cliente = em.find(Cliente.class, idCliente);

        if (cliente == null || peliculas.size() != cantidades.size()) {
            System.out.println("Error: Datos inválidos.");
            return;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Alquiler alquiler = new Alquiler();
            alquiler.setCliente(cliente);
            alquiler.setFecha(LocalDate.now());
            alquiler.setEstado(EstadoAlquiler.ACTIVO);

            double total = 0;
            List<DetalleAlquiler> detalles = new ArrayList<>();

            for (int i = 0; i < peliculas.size(); i++) {
                Pelicula pelicula = em.find(Pelicula.class, peliculas.get(i).getIdPelicula());
                int cantidad = cantidades.get(i);

                if (pelicula == null || pelicula.getStock() < cantidad) {
                    throw new RuntimeException("Stock insuficiente para: " + (pelicula != null ? pelicula.getTitulo() : "Película desconocida"));
                }

                pelicula.setStock(pelicula.getStock() - cantidad);

                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setAlquiler(alquiler);
                detalle.setPelicula(pelicula);
                detalle.setCantidad(cantidad);

                detalles.add(detalle);
                total += cantidad * 15.0;
            }

            alquiler.setDetalles(detalles);
            alquiler.setTotal(total);

            em.persist(alquiler);
            for (DetalleAlquiler det : detalles) {
                em.persist(det);
            }

            tx.commit();
            System.out.println("Alquiler registrado correctamente. Total: $" + total);

        } catch (Exception e) {
            tx.rollback();
            System.err.println("Error al registrar alquiler: " + e.getMessage());
        }
    }
}
