package com.milena.gestao_pedidos.aplication.service;

import com.milena.gestao_pedidos.domain.Pedido;
import com.milena.gestao_pedidos.domain.PedidoItem;
import com.milena.gestao_pedidos.domain.Produto;
import com.milena.gestao_pedidos.domain.Cliente; 
import com.milena.gestao_pedidos.repository.PedidoRepository;
import com.milena.gestao_pedidos.repository.ProdutoRepository;
import com.milena.gestao_pedidos.repository.ClienteRepository; 

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository; 

    public PedidoService(PedidoRepository pedidoRepository, 
                         ProdutoRepository produtoRepository, 
                         ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.listarTodos();
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Validação do Cliente
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new RuntimeException("ID do Cliente não enviado!");
        }

        Cliente cliente = clienteRepository.buscarPorId(pedido.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente inexistente"));
        pedido.setCliente(cliente);

        // Processamento dos Itens e Estoque
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> {
                item.setPedido(pedido); 
                
                Produto produto = produtoRepository.buscarPorId(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                
                if (produto.getQuantidadeEstoque() < item.getQuantidade()) {
                    throw new RuntimeException("Estoque insuficiente para: " + produto.getDescricao());
                }
                
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
                produtoRepository.save(produto);
            });
        }
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void deletar(Long id) {
        // 1. Buscar o pedido para saber o que tinha dentro dele
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // 2. Devolver os itens ao estoque
        for (PedidoItem item : pedido.getItens()) {
            Produto produto = item.getProduto();
            // Soma a quantidade que estava no pedido de volta ao estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }

        // 3. Deletar o pedido (usando seu método nativo)
        pedidoRepository.deletarPorId(id);
    }
}