import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/clientes'
});

export const ClienteService = {
  listarTodos: () => api.get(''),
  salvar: (cliente) => api.post('', cliente),
  deletar: (id) => api.delete(`/${id}`)
};