package com.victorvillar.baozistore.repository;

import com.victorvillar.baozistore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    boolean existsByCliente_Id(Long clienteId);
    boolean existsByProduto_Id(Long produtoId);
}
