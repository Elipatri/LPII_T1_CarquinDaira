package com.empresa.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @Column(name = "id_pelicula")
    private int idPelicula;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "genero", nullable = false, length = 50)
    private String genero;

    @Column(name = "stock", nullable = false)
    private int stock;
}
