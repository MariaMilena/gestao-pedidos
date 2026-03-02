package com.milena.gestao_pedidos.repository;

import com.milena.gestao_pedidos.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM clientes ORDER BY nome ASC", nativeQuery = true)
    List<Cliente> buscarTodosClientes();

    @Query(value = "SELECT * FROM clientes WHERE id = :id", nativeQuery = true)
    Optional<Cliente> buscarPorId(Long id);

    @Modifying(clearAutomatically = true) 
    @Transactional
    @Query(value = "DELETE FROM clientes WHERE id = :id", nativeQuery = true)
    void deletar(@Param("id") Long id);

    Optional<Cliente> findByEmail(String email);

}

