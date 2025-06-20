package com.empresa.alquiler.service;

import com.empresa.alquiler.model.*;
import com.empresa.alquiler.model.enums.EstadoAlquiler;
import com.empresa.alquiler.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;
    private final PeliculaRepository peliculaRepository;
    private final DetalleAlquilerRepository detalleAlquilerRepository;

    public AlquilerService(AlquilerRepository alquilerRepository,
                           ClienteRepository clienteRepository,
                           PeliculaRepository peliculaRepository,
                           DetalleAlquilerRepository detalleAlquilerRepository) {
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
        this.peliculaRepository = peliculaRepository;
        this.detalleAlquilerRepository = detalleAlquilerRepository;
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public List<Pelicula> obtenerPeliculas() {
        return peliculaRepository.findAll();
    }

    public List<Alquiler> obtenerAlquileres() {
        return alquilerRepository.findAll();
    }

    @Transactional
    public Alquiler registrarAlquiler(int idCliente, List<Integer> idPeliculas, List<Integer> cantidades) {
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        if (idPeliculas.size() != cantidades.size()) {
            throw new RuntimeException("Listas desbalanceadas");
        }

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(cliente);
        alquiler.setFecha(LocalDate.now());
        alquiler.setEstado(EstadoAlquiler.ACTIVO);

        double total = 0;
        List<DetalleAlquiler> detalles = new ArrayList<>();

        for (int i = 0; i < idPeliculas.size(); i++) {
            Pelicula pelicula = peliculaRepository.findById(idPeliculas.get(i))
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
            int cantidad = cantidades.get(i);
            if (pelicula.getStock() < cantidad) {
                throw new RuntimeException("Stock insuficiente para " + pelicula.getTitulo());
            }

            pelicula.setStock(pelicula.getStock() - cantidad);
            peliculaRepository.save(pelicula);

            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setAlquiler(alquiler);
            detalle.setPelicula(pelicula);
            detalle.setCantidad(cantidad);

            detalles.add(detalle);
            total += cantidad * 15.0;
        }

        alquiler.setTotal(total);
        alquiler.setDetalles(detalles);

        alquilerRepository.save(alquiler);
        for (DetalleAlquiler detalle : detalles) {
            detalleAlquilerRepository.save(detalle);
        }

        return alquiler;
    }

    @Transactional
    public void cambiarEstadoAlquiler(int idAlquiler, EstadoAlquiler nuevoEstado) {
        Alquiler alquiler = obtenerAlquilerPorId(idAlquiler);
        if (alquiler.getEstado() == EstadoAlquiler.DEVUELTO) {
            throw new RuntimeException("El alquiler ya fue devuelto.");
        }

        alquiler.setEstado(nuevoEstado);
        alquilerRepository.save(alquiler);

        if (nuevoEstado == EstadoAlquiler.DEVUELTO) {
            for (DetalleAlquiler detalle : alquiler.getDetalles()) {
                Pelicula pelicula = detalle.getPelicula();
                pelicula.setStock(pelicula.getStock() + detalle.getCantidad());
                peliculaRepository.save(pelicula);
            }
        }
    }

    public Alquiler obtenerAlquilerPorId(int id) {
        return alquilerRepository.findById(id).orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));
    }


}
