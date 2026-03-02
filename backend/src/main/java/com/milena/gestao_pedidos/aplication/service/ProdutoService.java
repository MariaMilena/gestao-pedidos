package com.milena.gestao_pedidos.aplication.service;

import com.milena.gestao_pedidos.domain.Produto;
import com.milena.gestao_pedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() {
        return produtoRepository.listarTodos();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        produtoRepository.deletar(id);
    }
}

