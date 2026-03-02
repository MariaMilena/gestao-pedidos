package com.milena.gestao_pedidos.repository;

import com.milena.gestao_pedidos.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List; 
import java.util.Optional; 
import java.math.BigDecimal;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produtos ORDER BY descricao ASC", nativeQuery = true)
    List<Produto> listarTodos();

    @Modifying
    @Query(value = "UPDATE produtos SET quantidade_estoque = quantidade_estoque - :qtd WHERE id = :id AND quantidade_estoque >= :qtd", nativeQuery = true)
    int baixarEstoqueNativo(Long id, Integer qtd);

    @Query(value = "SELECT * FROM produtos WHERE id = :id", nativeQuery = true)
    Optional<Produto> buscarPorId(Long id);

    @Modifying
    @Query(value = "INSERT INTO produtos (descricao, valor, quantidade_estoque) VALUES (:descricao, :valor, :estoque)", nativeQuery = true)
    void salvar(String descricao, BigDecimal valor, Integer estoque);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM produtos WHERE id = :id", nativeQuery = true)
    void deletar(@Param("id") Long id);
}
