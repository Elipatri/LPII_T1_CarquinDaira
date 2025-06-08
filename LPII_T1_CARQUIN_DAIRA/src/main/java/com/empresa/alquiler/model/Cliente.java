package com.empresa.alquiler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {

    @Id
    @Column(name = "id_cliente", nullable = false)
    private int idCliente;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    private String email;
}
