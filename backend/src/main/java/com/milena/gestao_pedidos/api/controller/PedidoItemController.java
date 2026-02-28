package com.milena.gestao_pedidos.api.controller;

import com.milena.gestao_pedidos.domain.PedidoItem;
import com.milena.gestao_pedidos.aplication.service.PedidoItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido-itens")
public class PedidoItemController {

    private final PedidoItemService pedidoItemService;

    public PedidoItemController(PedidoItemService pedidoItemService) {
        this.pedidoItemService = pedidoItemService;
    }

    @PostMapping
    public ResponseEntity<PedidoItem> adicionarItem(
            @RequestParam Long pedidoId,
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade) {

        return ResponseEntity.ok(
                pedidoItemService.adicionarItem(pedidoId, produtoId, quantidade)
        );
    }
}

