package com.kafka.consumer_ms.service;

import com.kafka.consumer_ms.model.entities.Cliente;
import org.springframework.http.ResponseEntity;

public interface ClienteService {
    Cliente findClienteById(Long id);

    ResponseEntity<Object> delete(Cliente cliente);

}
