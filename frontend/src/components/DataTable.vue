<template>
  <div class="table-container">
    <table>
      <thead>
        <tr>
          <th v-for="header in headers" :key="header.key">{{ header.label }}</th>
          <th style="width: 80px; text-align: center;">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in items" :key="item.id">
          <td v-for="header in headers" :key="header.key">{{ item[header.key] }}</td>
          <td style="text-align: center;">
            <button class="btn-delete" @click="$emit('delete', item.id)" title="Excluir">🗑️</button>
          </td>
        </tr>
        <tr v-if="items.length === 0">
          <td :colspan="headers.length + 1" class="empty-msg">Nenhum registro encontrado.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
defineProps({ headers: Array, items: Array });
defineEmits(['delete']);
</script>

<style scoped>
.table-container { margin-top: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); overflow: hidden; }
table { width: 100%; border-collapse: collapse; }
th { background-color: #f8f9fa; padding: 12px; text-align: left; border-bottom: 2px solid #dee2e6; }
td { padding: 12px; border-bottom: 1px solid #eee; }
.btn-delete { background: none; border: none; cursor: pointer; font-size: 1.2rem; transition: 0.2s; }
.btn-delete:hover { transform: scale(1.2); }
.empty-msg { text-align: center; padding: 20px; color: #666; }
</style>