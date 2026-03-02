package com.milena.gestao_pedidos.aplication.service;

import com.milena.gestao_pedidos.domain.Cliente;
import com.milena.gestao_pedidos.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.buscarTodosClientes();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deletar(id);
    }
}

