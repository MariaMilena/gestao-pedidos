package com.milena.gestao_pedidos.repository;

import com.milena.gestao_pedidos.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    @Modifying
    @Query(value = "INSERT INTO pedido_itens (pedido_id, produto_id, quantidade, valor, desconto) " +
                   "VALUES (:pedidoId, :produtoId, :quantidade, :valor, :desconto)", nativeQuery = true)
    void salvarItem(Long pedidoId, Long produtoId, Integer quantidade, BigDecimal valor, BigDecimal desconto);
}

