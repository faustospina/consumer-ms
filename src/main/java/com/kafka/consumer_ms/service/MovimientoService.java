package com.kafka.consumer_ms.service;

import com.kafka.consumer_ms.model.dto.MovimientoDTO;
import com.kafka.consumer_ms.model.entities.Movimiento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovimientoService {

    ResponseEntity<Object> getAll();

    ResponseEntity<Object> update(Long id, MovimientoDTO request);

    ResponseEntity<Object> delete(Long id);

    List<Movimiento> getMovimientosByCuenta(Long id);

}
