package com.victorvillar.baozistore.service;

import com.victorvillar.baozistore.dto.PedidoRequest;
import com.victorvillar.baozistore.dto.PedidoResponse;
import com.victorvillar.baozistore.model.Cliente;
import com.victorvillar.baozistore.model.Pedido;
import com.victorvillar.baozistore.model.Produto;
import com.victorvillar.baozistore.repository.ClienteRepository;
import com.victorvillar.baozistore.repository.PedidoRepository;
import com.victorvillar.baozistore.repository.ProdutoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class PedidoService {
    private final PedidoRepository repository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository repository, ClienteRepository clienteRepository,
                         ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoResponse create(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.clienteId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado: " + request.clienteId()));
        Produto produto = produtoRepository.findById(request.produtoId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + request.produtoId()));
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O produto está sem estoque disponível");
        }
        return PedidoResponse.from(repository.save(new Pedido(cliente, produto, request.quantidade())));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(PedidoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse findById(Long id) { return PedidoResponse.from(required(id)); }

    public PedidoResponse update(Long id, PedidoRequest request) {
        Pedido pedido = required(id);
        Cliente cliente = clienteRepository.findById(request.clienteId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado: " + request.clienteId()));
        Produto produto = produtoRepository.findById(request.produtoId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + request.produtoId()));
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O produto está sem estoque disponível");
        }
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setQuantidade(request.quantidade());
        return PedidoResponse.from(repository.save(pedido));
    }

    public void delete(Long id) { repository.delete(required(id)); }

    private Pedido required(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado: " + id));
    }
}
