package com.kafka.consumer_ms.repository;

import com.kafka.consumer_ms.model.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {

}
