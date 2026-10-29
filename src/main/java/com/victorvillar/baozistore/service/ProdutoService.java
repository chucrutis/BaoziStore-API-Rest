package com.victorvillar.baozistore.service;

import com.victorvillar.baozistore.dto.ProdutoRequest;
import com.victorvillar.baozistore.dto.ProdutoResponse;
import com.victorvillar.baozistore.model.Produto;
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
public class ProdutoService {
    private final ProdutoRepository repository;
    private final PedidoRepository pedidoRepository;

    public ProdutoService(ProdutoRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    public ProdutoResponse create(ProdutoRequest request) {
        return ProdutoResponse.from(repository.save(new Produto(
                request.nome().trim(), request.preco(), request.estoque())));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(ProdutoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponse findById(Long id) { return ProdutoResponse.from(required(id)); }

    public ProdutoResponse update(Long id, ProdutoRequest request) {
        Produto produto = required(id);
        produto.setNome(request.nome().trim());
        produto.setPreco(request.preco());
        produto.setEstoque(request.estoque());
        return ProdutoResponse.from(repository.save(produto));
    }

    public void delete(Long id) {
        Produto produto = required(id);
        if (pedidoRepository.existsByProduto_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O produto possui pedidos vinculados");
        }
        repository.delete(produto);
    }

    private Produto required(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado: " + id));
    }
}
