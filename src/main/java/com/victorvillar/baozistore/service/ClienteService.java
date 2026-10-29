package com.victorvillar.baozistore.service;

import com.victorvillar.baozistore.dto.ClienteRequest;
import com.victorvillar.baozistore.dto.ClienteResponse;
import com.victorvillar.baozistore.model.Cliente;
import com.victorvillar.baozistore.repository.ClienteRepository;
import com.victorvillar.baozistore.repository.PedidoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository repository;
    private final PedidoRepository pedidoRepository;

    public ClienteService(ClienteRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ClienteResponse create(ClienteRequest request) {
        return ClienteResponse.from(repository.save(new Cliente(request.nome().trim(), request.clienteDesde())));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(ClienteResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse findById(Long id) { return ClienteResponse.from(required(id)); }

    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = required(id);
        cliente.setNome(request.nome().trim());
        cliente.setClienteDesde(request.clienteDesde());
        return ClienteResponse.from(repository.save(cliente));
    }

    public void delete(Long id) {
        Cliente cliente = required(id);
        if (pedidoRepository.existsByCliente_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O cliente possui pedidos vinculados");
        }
        repository.delete(cliente);
    }

    private Cliente required(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado: " + id));
    }
}
