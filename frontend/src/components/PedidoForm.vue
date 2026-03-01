<template>
  <div class="modal-overlay">
    <div class="modal-content pedido-modal">
      <h2>🛒 Novo Pedido</h2>
      
      <div class="field">
        <label>Selecione o Cliente:</label>
        <select v-model="pedido.cliente.id" required>
          <option disabled value="">Escolha um cliente...</option>
          <option v-for="c in clientes" :key="c.id" :value="c.id">{{ c.nome }}</option>
        </select>
      </div>

      <hr />

      <div class="item-builder">
        <h3>Adicionar Itens</h3>
        <div class="input-group">
          <div class="col">
            <label>Produto</label>
            <select v-model="itemAtual.produto.id" @change="atualizarPrecoOriginal">
              <option disabled value="">Selecione...</option>
              <option v-for="p in produtos" :key="p.id" :value="p.id">
                {{ p.descricao }} (Estoque: {{p.quantidadeEstoque}})
              </option>
            </select>
          </div>
          <div class="col small">
            <label>Qtd.</label>
            <input type="number" v-model.number="itemAtual.quantidade" min="1" />
          </div>
          <div class="col small">
            <label>Desc. (R$)</label>
            <input type="number" v-model.number="itemAtual.desconto" step="0.01" />
          </div>
          <div class="col btn-col">
            <label>&nbsp;</label> <button type="button" @click="adicionarItem" class="btn-add">Adicionar</button>
          </div>
        </div>
      </div>

      <div class="itens-list" v-if="pedido.itens.length > 0">
        <table>
          <thead>
            <tr>
              <th>Produto</th>
              <th>Qtd</th>
              <th>Valor Un.</th>
              <th>Desc.</th>
              <th>Subtotal</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(it, idx) in pedido.itens" :key="idx">
              <td>{{ getNomeProduto(it.produto.id) }}</td>
              <td>{{ it.quantidade }}</td>
              <td>R$ {{ it.valor.toFixed(2) }}</td>
              <td>R$ {{ it.desconto.toFixed(2) }}</td>
              <td>R$ {{ ((it.quantidade * it.valor) - it.desconto).toFixed(2) }}</td>
              <td><button class="btn-remove" @click="removerItem(idx)">🗑️</button></td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="footer-pedido">
        <div class="total">TOTAL DO PEDIDO: <span>R$ {{ totalGeral.toFixed(2) }}</span></div>
        <div class="actions">
          <button type="button" class="btn-cancel" @click="$emit('close')">Cancelar</button>
          <button type="button" class="btn-save" @click="finalizarPedido" 
            :disabled="!pedido.cliente.id || pedido.itens.length === 0">
            Finalizar Pedido
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { ClienteService } from '../services/ClienteService';
import { ProdutoService } from '../services/ProdutoService';

const emit = defineEmits(['close', 'save']);
const clientes = ref([]);
const produtos = ref([]);

// AJUSTE: Estrutura de objeto conforme as Entidades Java
const pedido = ref({ 
  cliente: { id: '' }, 
  itens: [] 
});

const itemAtual = ref({ 
  produto: { id: '' }, 
  quantidade: 1, 
  valor: 0, 
  desconto: 0 
});

onMounted(async () => {
  const [resC, resP] = await Promise.all([ClienteService.listarTodos(), ProdutoService.listarTodos()]);
  clientes.value = resC.data;
  produtos.value = resP.data;
});

const atualizarPrecoOriginal = () => {
  const prod = produtos.value.find(p => p.id === itemAtual.value.produto.id);
  if (prod) itemAtual.value.valor = prod.valor;
};

const adicionarItem = () => {
  if (!itemAtual.value.produto.id || itemAtual.value.quantidade <= 0) {
    alert("Selecione um produto e a quantidade.");
    return;
  }
  // Cria uma cópia profunda do item para não bugar a lista
  pedido.value.itens.push({ 
    produto: { id: itemAtual.value.produto.id },
    quantidade: itemAtual.value.quantidade,
    valor: itemAtual.value.valor,
    desconto: itemAtual.value.desconto || 0
  });
  // Reseta o formulário de item
  itemAtual.value = { produto: { id: '' }, quantidade: 1, valor: 0, desconto: 0 };
};

const removerItem = (index) => pedido.value.itens.splice(index, 1);
const getNomeProduto = (id) => produtos.value.find(p => p.id === id)?.descricao;

const totalGeral = computed(() => {
  return pedido.value.itens.reduce((acc, it) => acc + (it.quantidade * it.valor - it.desconto), 0);
});

const finalizarPedido = () => {
  // Envia o objeto exatamente como o PedidoService do Java espera
  emit('save', pedido.value);
};
</script>

<style scoped>
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.pedido-modal { background: white; padding: 25px; border-radius: 12px; width: 850px; max-height: 90vh; overflow-y: auto; }

.input-group { display: flex; gap: 15px; margin-bottom: 20px; align-items: flex-end; }
.col { display: flex; flex-direction: column; flex: 2; }
.col.small { flex: 0.8; }
.col label { font-size: 0.85rem; font-weight: bold; margin-bottom: 5px; color: #666; }

select, input { padding: 10px; border: 1px solid #ddd; border-radius: 6px; }

.btn-add { background: #3498db; color: white; border: none; padding: 11px 20px; border-radius: 6px; cursor: pointer; font-weight: bold; }
.btn-remove { background: none; border: none; cursor: pointer; font-size: 1.2rem; }

.itens-list { margin-top: 20px; border: 1px solid #eee; border-radius: 8px; overflow: hidden; }
table { width: 100%; border-collapse: collapse; }
th { background: #f8f9fa; padding: 12px; text-align: left; font-size: 0.9rem; }
td { padding: 12px; border-top: 1px solid #eee; }

.footer-pedido { margin-top: 30px; display: flex; justify-content: space-between; align-items: center; background: #f9f9f9; padding: 20px; border-radius: 8px; }
.total span { font-size: 1.6rem; font-weight: bold; color: #2c3e50; }

.btn-save { background: #42b983; color: white; border: none; padding: 12px 30px; border-radius: 8px; cursor: pointer; font-weight: bold; }
.btn-cancel { background: #ccc; color: #333; border: none; padding: 12px 30px; border-radius: 8px; cursor: pointer; }
</style>