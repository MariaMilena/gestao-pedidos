CREATE TABLE IF NOT EXISTS public.clientes (
    id          SERIAL          PRIMARY KEY,
    nome        VARCHAR(150)    NOT NULL,
    email       VARCHAR(120)    UNIQUE NOT NULL,
    data_cadastro DATE          DEFAULT CURRENT_DATE NOT NULL,
    
    -- Campos opcionais úteis para auditoria/futuro
    criado_em   TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);