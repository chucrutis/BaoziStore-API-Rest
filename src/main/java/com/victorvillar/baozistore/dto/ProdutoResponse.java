package com.victorvillar.baozistore.dto;

import com.victorvillar.baozistore.model.Produto;
import java.math.BigDecimal;

public record ProdutoResponse(Long id, String nome, BigDecimal preco, Boolean estoque) {
    public static ProdutoResponse from(Produto produto) {
        return new ProdutoResponse(produto.getId(), produto.getNome(), produto.getPreco(), produto.getEstoque());
    }
}
