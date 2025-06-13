package com.example.lab8_gtics_20251_20211602.Repository;

import com.example.lab8_gtics_20251_20211602.Entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
}