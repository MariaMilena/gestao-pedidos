<script setup>
import { ref, onMounted } from 'vue';
import { PedidoService } from '../services/PedidoService';
import DataTable from '../components/DataTable.vue';
import PedidoForm from '../components/PedidoForm.vue';

const pedidos = ref([]);
const exibindoModal = ref(false);

const colunas = [
  { label: 'Cliente', key: 'clienteNome' },
  { label: 'Data', key: 'dataPedidoFormatada' },
  { label: 'Total Desconto', key: 'descontoFormatado' }, // Nova coluna
  { label: 'Total Geral', key: 'totalFormatado' }
];

const carregarPedidos = async () => {
  try {
    const res = await PedidoService.listarTodos();
    
    pedidos.value = res.data.map(p => {
      // Cálculo dos totais
      let somaDesconto = 0;
      let somaTotal = 0;

      if (p.itens) {
        p.itens.forEach(item => {
          somaDesconto += (item.desconto || 0);
          somaTotal += (item.quantidade * item.valor) - (item.desconto || 0);
        });
      }

      return {
        ...p,
        // Se o Java ainda não mandar o cliente, vamos tentar pegar de dentro do seu JSON se houver
        clienteNome: p.cliente ? p.cliente.nome : "Aguardando objeto cliente",
        dataPedidoFormatada: new Date(p.dataPedido).toLocaleDateString('pt-BR'),
        descontoFormatado: `R$ ${somaDesconto.toFixed(2)}`,
        totalFormatado: `R$ ${somaTotal.toFixed(2)}`
      };
    });
  } catch (e) {
    console.error(e);
  }
};

const salvarPedido = async (dados) => {
  console.log("Dados recebidos do formulário:", dados); // Se isso não aparecer no F12, o erro está no PedidoForm
  try {
    await PedidoService.salvar(dados);
    exibindoModal.value = false;
    await carregarPedidos(); // Recarrega a lista
    alert("Pedido realizado com sucesso!");
  } catch (e) {
    console.error("Erro na API:", e);
    const mensagemErro = e.response?.data?.message || "Erro ao salvar pedido. Verifique o estoque!";
    alert(mensagemErro);
  }
};

const excluirPedido = async (id) => {
  if (confirm("Excluir pedido?")) {
    await PedidoService.deletar(id);
    carregarPedidos();
  }
};

onMounted(carregarPedidos);
</script>

<template>
  <div class="container">
    <div class="header">
      <h1>Gestão de Pedidos</h1>
      <button class="btn-new" @click="exibindoModal = true">+ Novo Pedido</button>
    </div>
    
    <DataTable :headers="colunas" :items="pedidos" @delete="excluirPedido" />
    <PedidoForm v-if="exibindoModal" @close="exibindoModal = false" @save="salvarPedido" />
  </div>
</template>

<style scoped>
.container { padding: 30px; }
.header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.btn-new { background: #42b983; color: white; border: none; padding: 12px 24px; border-radius: 8px; cursor: pointer; font-weight: bold; }
</style>