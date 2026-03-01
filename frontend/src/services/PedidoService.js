import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/pedidos'
});

export const PedidoService = {
  listarTodos: () => api.get(''),
  salvar: (pedido) => api.post('', pedido), // Envia Pedido + Itens
  buscarPorId: (id) => api.get(`/${id}`),
  deletar: (id) => api.delete(`/${id}`)
};