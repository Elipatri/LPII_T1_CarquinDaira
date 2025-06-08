package com.empresa.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import com.empresa.alquiler.model.enums.EstadoAlquiler;

@Entity
@Data
@Table(name = "alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler", nullable = false)
    private int idAlquiler;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "total", nullable = false)
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 10)
    private EstadoAlquiler estado;

    @OneToMany(mappedBy = "alquiler")
    private List<DetalleAlquiler> detalles;
}
