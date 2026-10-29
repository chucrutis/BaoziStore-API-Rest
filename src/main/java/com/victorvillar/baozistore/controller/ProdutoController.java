package com.victorvillar.baozistore.controller;

import com.victorvillar.baozistore.dto.ProdutoRequest;
import com.victorvillar.baozistore.dto.ProdutoResponse;
import com.victorvillar.baozistore.service.ProdutoService;
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
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService service;
    public ProdutoController(ProdutoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@Valid @RequestBody ProdutoRequest request) {
        ProdutoResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/produtos/" + created.id())).body(created);
    }

    @GetMapping
    public List<ProdutoResponse> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ProdutoResponse findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public ProdutoResponse update(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
