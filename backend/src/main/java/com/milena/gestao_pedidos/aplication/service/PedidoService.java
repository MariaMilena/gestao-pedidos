package com.milena.gestao_pedidos.aplication.service;

import com.milena.gestao_pedidos.domain.Pedido;
import com.milena.gestao_pedidos.domain.PedidoItem;
import com.milena.gestao_pedidos.domain.Produto;
import com.milena.gestao_pedidos.domain.Cliente; // IMPORT QUE FALTA 1
import com.milena.gestao_pedidos.repository.PedidoRepository;
import com.milena.gestao_pedidos.repository.ProdutoRepository;
import com.milena.gestao_pedidos.repository.ClienteRepository; // IMPORT QUE FALTA 2

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository; // DECLARAÇÃO QUE FALTA

    // CONSTRUTOR ATUALIZADO (Injeção de dependência)
    public PedidoService(PedidoRepository pedidoRepository, 
                         ProdutoRepository produtoRepository, 
                         ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Validação do Cliente
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new RuntimeException("ID do Cliente não enviado!");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
            .orElseThrow(() -> new RuntimeException("Cliente inexistente"));
        pedido.setCliente(cliente);

        // Processamento dos Itens e Estoque
        if (pedido.getItens() != null) {
            pedido.getItens().forEach(item -> {
                item.setPedido(pedido); 
                
                Produto produto = produtoRepository.findById(item.getProduto().getId())
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

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}