package com.victorvillar.baozistore.controller;

import com.victorvillar.baozistore.dto.PedidoRequest;
import com.victorvillar.baozistore.dto.PedidoResponse;
import com.victorvillar.baozistore.service.PedidoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<PedidoResponse> create(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/pedidos/" + created.id())).body(created);
    }

    @GetMapping
    public List<PedidoResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public PedidoResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public PedidoResponse update(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
