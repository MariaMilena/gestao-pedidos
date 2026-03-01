<template>
  <div class="app-layout">
    <AppSidebar />
    
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import AppSidebar from './components/AppSidebar.vue'
</script>

<style>
/* Estilos globais para garantir que a sidebar não cubra o conteúdo */
body {
  margin: 0;
  font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif;
  background-color: #f4f7f6;
}

.app-layout {
  display: flex;
}

.main-content {
  margin-left: 260px; /* Mesma largura da sidebar */
  flex: 1;
  min-height: 100vh;
  padding: 20px;
}
</style>