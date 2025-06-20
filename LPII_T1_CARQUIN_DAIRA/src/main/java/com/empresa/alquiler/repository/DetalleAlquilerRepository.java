package com.empresa.alquiler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresa.alquiler.model.DetalleAlquiler;
import com.empresa.alquiler.model.DetalleAlquilerId;

@Repository public interface DetalleAlquilerRepository extends JpaRepository<DetalleAlquiler,DetalleAlquilerId> {}
