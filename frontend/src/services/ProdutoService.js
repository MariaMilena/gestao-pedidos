import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/produtos' // Verifique se o seu @RequestMapping é /produtos
});

export const ProdutoService = {
  listarTodos: () => api.get(''),
  salvar: (produto) => api.post('', produto),
  deletar: (id) => api.delete(`/${id}`)
};