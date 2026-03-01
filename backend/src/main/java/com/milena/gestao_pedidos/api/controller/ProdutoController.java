package com.milena.gestao_pedidos.api.controller;

import com.milena.gestao_pedidos.domain.Produto;
import com.milena.gestao_pedidos.aplication.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:5173", 
             allowedHeaders = "*", 
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvar(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
