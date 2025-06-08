package com.empresa.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_alquiler")
@IdClass(DetalleAlquilerId.class)
public class DetalleAlquiler {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;
}