# 🛒 Sistema de Gestão de Pedidos

Desafio técnico desenvolvido para a posição de **Analista de Sistemas**. O projeto é uma aplicação Full Stack para gestão de clientes, produtos e pedidos, com controlo de stock automatizado.

<img width="1852" height="884" alt="image" src="https://github.com/user-attachments/assets/f5d70bfe-20b1-45d3-96f5-6a1a1b040493" />
<img width="1849" height="880" alt="image" src="https://github.com/user-attachments/assets/9508ef50-f6b1-4817-aed5-034a7a27664c" />
<img width="1849" height="883" alt="image" src="https://github.com/user-attachments/assets/6b544c85-c2b3-48da-8aeb-d997060e200e" />

## 🚀 Tecnologias Utilizadas

### Backend
* **Java 17**
* **Spring Boot 3**
* **Spring Data JPA** (Persistência)
* **PostgreSQL (Cloud)** - Alojado no **Neon.tech**
* **Maven** (Gestor de dependências)

### Frontend
* **Vue.js 3** (Composition API)
* **Axios** (Consumo de API)
* **CSS3** (Layout responsivo)

---

## 🛠️ Funcionalidades principais
- **Gestão de Pedidos:** Registo de novos pedidos vinculados a clientes existentes.
- **Carrinho de Compras:** Adição dinâmica de itens com cálculo automático de subtotal e descontos.
- **Regra de Negócio (Stock):** O sistema valida a disponibilidade do produto antes de finalizar a venda e abate automaticamente a quantidade do inventário.
- **Integridade de Dados:** Utilização de transações para garantir que o pedido só é gravado se todas as atualizações de stock forem concluídas com sucesso.

---

## 📋 Como Executar o Projeto

### 1. Pré-requisitos
* Java 17 instalado.
* Node.js instalado.

### 2. Base de Dados (Cloud)
O projeto utiliza o **Neon.tech** (PostgreSQL na nuvem). 
**Não é necessário instalar ou configurar o PostgreSQL localmente**, pois o backend já está configurado para se ligar à instância remota. Os dados de teste (Clientes e Produtos) já se encontram pré-carregados na nuvem.

### 3. Executando o Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 4. Executando o Frontend
```bash
cd frontend
npm install
npm run dev
```
