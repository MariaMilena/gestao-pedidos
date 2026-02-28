INSERT INTO public.clientes (nome, email, data_cadastro)
VALUES 
    ('João Silva', 'joao.silva@email.com', '2025-01-15'),
    ('Maria Oliveira', 'maria.oliveira@exemplo.com', '2025-02-10'),
    ('Pedro Santos', 'pedro.santos@empresa.com', CURRENT_DATE)
ON CONFLICT (email) DO NOTHING;