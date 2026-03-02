package com.milena.gestao_pedidos.repository;

import com.milena.gestao_pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "SELECT * FROM pedidos WHERE id = :id", nativeQuery = true)
    Optional<Pedido> buscarPorId(Long id);

    @Query(value = "SELECT * FROM pedidos ORDER BY data_pedido DESC", nativeQuery = true)
    List<Pedido> listarTodos();

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM pedidos WHERE id = :pedidoId", nativeQuery = true)
    void deletarPorId(@Param("pedidoId") Long id);
}

