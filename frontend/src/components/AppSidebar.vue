<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <h2>Gestão de Pedidos</h2>
    </div>
    
    <nav class="nav-menu">
      <router-link to="/clientes" class="nav-item" active-class="active">
        <span class="icon">👥</span> Clientes
      </router-link>
      
      <router-link to="/produtos" class="nav-item" active-class="active">
        <span class="icon">📦</span> Produtos
      </router-link>
      
      <router-link to="/pedidos" class="nav-item" active-class="active">
        <span class="icon">🛒</span> Pedidos
      </router-link>
    </nav>

    <div class="sidebar-footer">
      <small>Entrega: 01/03/2026</small>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 260px;
  height: 100vh;
  background-color: #2c3e50;
  color: white;
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
}

.sidebar-header {
  padding: 30px 20px;
  text-align: center;
  background-color: #1a252f;
}

.nav-menu {
  flex: 1;
  padding: 20px 0;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 15px 25px;
  color: #ecf0f1;
  text-decoration: none;
  transition: all 0.3s;
}

.nav-item:hover {
  background-color: #34495e;
}

.active {
  background-color: #42b983;
  border-left: 6px solid #fff;
  font-weight: bold;
}

.sidebar-footer {
  padding: 20px;
  font-size: 0.75rem;
  color: #bdc3c7;
  text-align: center;
  border-top: 1px solid #34495e;
}
</style>