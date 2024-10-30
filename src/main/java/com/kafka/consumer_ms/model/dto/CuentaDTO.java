package com.kafka.consumer_ms.model.dto;

public record CuentaDTO(Long id, String numeroCuenta, TipoCuenta tipoCuenta, double saldoInicial, boolean estado, ClienteDTO cliente) {
}
