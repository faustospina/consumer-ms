package com.kafka.consumer_ms.controller;

import com.kafka.consumer_ms.model.dto.CuentaDTO;
import com.kafka.consumer_ms.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("cuentas")
public class CuentaController {

    private final CuentaService service;

    @PostMapping("/cliente/{id}")
    public ResponseEntity<Object> createCuenta(@PathVariable Long id, @RequestBody CuentaDTO request){
        return service.createCount(id,request);
    }
    @GetMapping
    public ResponseEntity<Object> getAll(){
       return service.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCuenta(@PathVariable Long id,@RequestBody CuentaDTO request){
        return service.updateCuenta(id,request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
       return service.deleteCuenta(id);
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable Long id){
        return service.deleteCliente(id);
    }




}
