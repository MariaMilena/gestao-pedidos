<script setup>
import { ref, onMounted, computed } from 'vue';
// Importamos como ClienteService para bater com o export do seu arquivo
import { ClienteService } from '../services/ClienteService.js';
import DataTable from '../components/DataTable.vue';
import ClienteForm from '../components/ClienteForm.vue';

const clientes = ref([]);
const busca = ref('');
const exibindoModal = ref(false);

const colunas = [
  { label: 'Nome', key: 'nome' },
  { label: 'E-mail', key: 'email' },
  { label: 'Data Cadastro', key: 'dataCadastroFormatada' }
];

const carregarDados = async () => {
  try {
    const res = await ClienteService.listarTodos();
    
    if (res.data.success) {
      clientes.value = res.data.data;
    } else {
      console.error("Erro reportado pelo backend:", res.data.message);
    }
    
  } catch (e) { 
    console.error("Erro ao carregar:", e);
    alert("Erro ao carregar dados. Verifique se o backend está rodando."); 
  }
};

const handleSalvar = async (dados) => {
  try {
    await ClienteService.salvar(dados);
    exibindoModal.value = false;
    await carregarDados();
  } catch (e) {
    alert("Erro ao salvar cliente.");
  }
};

const handleExcluir = async (id) => {
  if (confirm("Deseja excluir este cliente?")) {
    try {
      await ClienteService.deletar(id);
      await carregarDados();
    } catch (e) {
      alert("Erro ao excluir. O cliente pode ter pedidos vinculados.");
    }
  }
};

const clientesProcessados = computed(() => {
  if (!clientes.value) return [];
  return clientes.value
    .filter(c => 
      c.nome.toLowerCase().includes(busca.value.toLowerCase()) || 
      (c.id && c.id.toString() === busca.value)
    )
    .map(c => ({
      ...c,
      dataCadastroFormatada: c.dataCadastro ? new Date(c.dataCadastro).toLocaleDateString('pt-BR') : '—'
    }));
});

onMounted(carregarDados);
</script>

<template>
  <div class="container">
    <div class="header">
      <h1>Gestão de Clientes</h1>
      <button class="btn-new" @click="exibindoModal = true">+ Novo Cliente</button>
    </div>

    <div class="search-bar">
      <input v-model="busca" placeholder="Buscar por nome ou identificador (ID)..." />
    </div>

    <DataTable 
      :headers="colunas" 
      :items="clientesProcessados" 
      @delete="handleExcluir" 
    />

    <ClienteForm 
      v-if="exibindoModal" 
      @close="exibindoModal = false" 
      @save="handleSalvar" 
    />
  </div>
</template>

<style scoped>
.container { padding: 30px; max-width: 1200px; margin: 0 auto; font-family: sans-serif; }
.header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.search-bar { margin-bottom: 20px; }
.search-bar input { width: 100%; padding: 12px; border-radius: 8px; border: 1px solid #ddd; box-sizing: border-box; }
.btn-new { background: #42b983; color: white; border: none; padding: 12px 24px; border-radius: 8px; cursor: pointer; font-weight: bold; transition: background 0.3s; }
.btn-new:hover { background: #3aa876; }
</style>