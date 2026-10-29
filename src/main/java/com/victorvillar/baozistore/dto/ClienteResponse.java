package com.victorvillar.baozistore.dto;

import com.victorvillar.baozistore.model.Cliente;
import java.time.LocalDate;

public record ClienteResponse(Long id, String nome, LocalDate clienteDesde) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getClienteDesde());
    }
}
