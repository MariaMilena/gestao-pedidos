CREATE TABLE IF NOT EXISTS public.pedido_itens (
    id SERIAL PRIMARY KEY,
    pedido_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    quantidade INTEGER NOT NULL,
    desconto DECIMAL(10,2),

    CONSTRAINT fk_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES public.pedidos(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES public.produtos(id)
        ON DELETE RESTRICT
);

CREATE INDEX idx_item_pedido ON public.pedido_itens(pedido_id);
CREATE INDEX idx_item_produto ON public.pedido_itens(produto_id);
