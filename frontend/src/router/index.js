import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ClientesView from '../views/ClientesView.vue'
import ProdutosView from '../views/ProdutosView.vue'
import PedidosView from '../views/PedidosView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/clientes',
      name: 'clientes',
      component: ClientesView
    },
    {
      path: '/produtos',
      name: 'produtos',
      component: ProdutosView
    },
    {
      path: '/pedidos',
      name: 'pedidos',
      component: PedidosView
    }
  ]
})

export default router
