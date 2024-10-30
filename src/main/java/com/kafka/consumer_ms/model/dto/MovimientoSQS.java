package com.kafka.consumer_ms.model.dto;

public record MovimientoSQS(String nomeroCuenta, TipoMovimiento tipoMovimiento, double valor) {
}
