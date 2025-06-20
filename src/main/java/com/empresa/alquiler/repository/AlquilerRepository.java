package com.empresa.alquiler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.empresa.alquiler.model.Alquiler;

@Repository public interface AlquilerRepository extends JpaRepository<Alquiler,Integer> {}
