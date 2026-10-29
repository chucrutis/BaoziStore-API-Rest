package com.victorvillar.baozistore.dto;

import com.victorvillar.baozistore.model.Pedido;

public record PedidoResponse(
        Long id,
        Long clienteId,
        String clienteNome,
        Long produtoId,
        String produtoNome,
        Integer quantidade) {
    public static PedidoResponse from(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getProduto().getId(),
                pedido.getProduto().getNome(),
                pedido.getQuantidade());
    }
}
