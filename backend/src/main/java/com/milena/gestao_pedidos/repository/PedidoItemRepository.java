package com.milena.gestao_pedidos.repository;

import com.milena.gestao_pedidos.domain.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}

