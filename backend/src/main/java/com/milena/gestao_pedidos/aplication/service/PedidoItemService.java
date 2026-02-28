package com.milena.gestao_pedidos.aplication.service;

import com.milena.gestao_pedidos.domain.Pedido;
import com.milena.gestao_pedidos.domain.PedidoItem;
import com.milena.gestao_pedidos.domain.Produto;
import com.milena.gestao_pedidos.repository.PedidoItemRepository;
import com.milena.gestao_pedidos.repository.PedidoRepository;
import com.milena.gestao_pedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoItemService {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoItemService(PedidoItemRepository pedidoItemRepository,
                             PedidoRepository pedidoRepository,
                             ProdutoRepository produtoRepository) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoItem adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPreco_unitario(produto.getValor());

        return pedidoItemRepository.save(item);
    }
}
