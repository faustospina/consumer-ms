package com.kafka.consumer_ms.service;

import com.kafka.consumer_ms.exception.NotFoundException;
import com.kafka.consumer_ms.model.entities.Cliente;
import com.kafka.consumer_ms.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    public static final String NOT_FOUND_CLIENT = "Not found client";
    private final ClienteRepository repository;

    @Override
    public Cliente findClienteById(Long id) {
        return getClienteId(id);
    }

    private Cliente getClienteId(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_CLIENT));
    }

    @Override
    public ResponseEntity<Object> delete(Cliente cliente) {
        repository.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
