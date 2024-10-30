package com.kafka.consumer_ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.consumer_ms.exception.BusinessException;
import com.kafka.consumer_ms.exception.NotFoundException;
import com.kafka.consumer_ms.model.dto.MovimientoDTO;
import com.kafka.consumer_ms.model.dto.MovimientoSQS;
import com.kafka.consumer_ms.model.dto.TipoMovimiento;
import com.kafka.consumer_ms.model.entities.Cuenta;
import com.kafka.consumer_ms.model.entities.Movimiento;
import com.kafka.consumer_ms.model.mapper.MovimientoMapper;
import com.kafka.consumer_ms.repository.CuentaRepository;
import com.kafka.consumer_ms.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoServiceImp implements MovimientoService {

    public static final String NOT_FOUND_MOVIMIENTO = "Not found movimiento ";
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private MovimientoMapper movimientoMapper;

    @Transactional
    private void registrarMovimiento(MovimientoSQS movimientoSQS) {
        Cuenta cuentaOpt = cuentaRepository.findByNumeroCuenta(movimientoSQS.nomeroCuenta()).orElseThrow(()-> new RuntimeException("not count found"));
        double nuevoSaldo = cuentaOpt.getSaldoInicial();

        if (TipoMovimiento.RETIRO.equals(movimientoSQS.tipoMovimiento())) {
            if (nuevoSaldo < movimientoSQS.valor()) {
                buildMovimiento(movimientoSQS, cuentaOpt, nuevoSaldo, "FAIL", "Transaccion fallida, No hay saldo suficiente para realizar el retiro.");
                throw new BusinessException("No hay saldo suficiente para realizar el retiro.");
            }
            nuevoSaldo -= movimientoSQS.valor();
        } else if (TipoMovimiento.DEPOSITO.equals(movimientoSQS.tipoMovimiento())) {
            nuevoSaldo += movimientoSQS.valor();
        } else {
            throw new IllegalArgumentException("Tipo de movimiento no válido.");
        }

        buildMovimiento(movimientoSQS, cuentaOpt, nuevoSaldo,"SUCCESS","Transaccion extitosa!");
    }

    private void buildMovimiento(MovimientoSQS movimientoSQS, Cuenta cuentaOpt, double nuevoSaldo, String estado, String descripcion) {
        cuentaOpt.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuentaOpt);
        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDate.now());
        movimiento.setTipoMovimiento(movimientoSQS.tipoMovimiento().name());
        movimiento.setValor(movimientoSQS.valor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setEstado(estado);
        movimiento.setDescripcion(descripcion);
        movimiento.setCuentaId(cuentaOpt.getId());

        movimientoRepository.save(movimiento);
    }


    @Override
    public ResponseEntity<Object> getAll() {
        List<Movimiento> movimiento = movimientoRepository.findAll();
        return new ResponseEntity<>(movimiento.stream().map(movimientoMapper::toDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> update(Long id, MovimientoDTO request) {
        Movimiento movimiento = getMovimiento(id);
        movimiento.setFecha(request.fecha());
        movimiento.setTipoMovimiento(request.tipoMovimiento().name());
        movimiento.setValor(request.valor());
        movimiento.setSaldo(request.saldo());
        return new ResponseEntity<>(movimientoMapper.toDTO(movimientoRepository.save(movimiento)),HttpStatus.OK);
    }

    private Movimiento getMovimiento(Long id) {
        return movimientoRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MOVIMIENTO + id));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        Movimiento movimiento = getMovimiento(id);
        movimientoRepository.delete(movimiento);
        return ResponseEntity.noContent().build();
    }


    @KafkaListener(topics = {"movimientos-topic"}, groupId = "my-group-id")
    public void listener(String message) throws JsonProcessingException {
        try {
            if(message!=null){
                System.out.println("mensaje" + message);
                MovimientoSQS movimiento = objectMapper.readValue(message, MovimientoSQS.class);
                System.out.println("objeto------>"+movimiento.toString());
                registrarMovimiento(movimiento);
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
            // Opcional: Manejo de reintentos o registros en caso de error
        }
    }

    @Override
    public List<Movimiento> getMovimientosByCuenta(Long idCuenta) {
        return movimientoRepository.findByCuentaId(idCuenta);
    }







}
