<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Novo Cliente</h2>
      <form @submit.prevent="handleSubmit">
        <div class="field">
          <label>Nome:</label>
          <input v-model="form.nome" placeholder="Nome completo" required />
        </div>
        <div class="field">
          <label>E-mail:</label>
          <input v-model="form.email" type="email" placeholder="email@exemplo.com" required />
        </div>
        <div class="actions">
          <button type="button" class="btn-cancel" @click="$emit('close')">Cancelar</button>
          <button type="submit" class="btn-save">Salvar Cliente</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
const emit = defineEmits(['close', 'save']);
const form = ref({ nome: '', email: '' });

const handleSubmit = () => {
  emit('save', { ...form.value });
};
</script>

<style scoped>
.modal-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); display: flex; justify-content: center; align-items: center; z-index: 1000; }
.modal-content { background: white; padding: 30px; border-radius: 8px; width: 400px; }
.field { display: flex; flex-direction: column; margin-bottom: 15px; }
input { padding: 10px; border: 1px solid #ccc; border-radius: 4px; margin-top: 5px; }
.actions { display: flex; justify-content: flex-end; gap: 10px; margin-top: 20px; }
.btn-save { background: #42b983; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
.btn-cancel { background: #95a5a6; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; }
</style>