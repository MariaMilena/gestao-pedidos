<script setup>
import { ref, onMounted, computed } from 'vue';
import { ProdutoService } from '../services/ProdutoService.js';
import DataTable from '../components/DataTable.vue';
import ProdutoForm from '../components/ProdutoForm.vue';

const produtos = ref([]);
const busca = ref('');
const exibindoModal = ref(false);

const colunas = [
  { label: 'Descrição', key: 'descricao' },
  { label: 'Valor', key: 'valorFormatado' },
  { label: 'Estoque', key: 'quantidadeEstoque' }
];

const carregarDados = async () => {
  try {
    const res = await ProdutoService.listarTodos();
    produtos.value = res.data;
  } catch (e) { console.error(e); }
};

const handleSalvar = async (dados) => {
  await ProdutoService.salvar(dados);
  exibindoModal.value = false;
  carregarDados();
};

const handleExcluir = async (id) => {
  if (confirm("Deseja excluir este produto?")) {
    await ProdutoService.deletar(id);
    carregarDados();
  }
};

const produtosFiltrados = computed(() => {
  return produtos.value
    .filter(p => 
      p.descricao.toLowerCase().includes(busca.value.toLowerCase()) || 
      (p.id && p.id.toString() === busca.value)
    )
    .map(p => ({
      ...p,
      valorFormatado: `R$ ${p.valor.toFixed(2)}`
    }));
});

onMounted(carregarDados);
</script>

<template>
  <div class="container">
    <div class="header">
      <h1>Gestão de Produtos</h1>
      <button class="btn-new" @click="exibindoModal = true">+ Novo Produto</button>
    </div>

    <div class="search-bar">
      <input v-model="busca" placeholder="Buscar por descrição ou ID..." />
    </div>

    <DataTable :headers="colunas" :items="produtosFiltrados" @delete="handleExcluir" />

    <ProdutoForm v-if="exibindoModal" @close="exibindoModal = false" @save="handleSalvar" />
  </div>
</template>

<style scoped>
.container { padding: 30px; }
.header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.search-bar input { width: 100%; padding: 12px; border-radius: 8px; border: 1px solid #ddd; box-sizing: border-box; }
.btn-new { background: #42b983; color: white; border: none; padding: 12px 24px; border-radius: 8px; cursor: pointer; font-weight: bold; }
</style>