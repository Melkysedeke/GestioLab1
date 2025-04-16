# 💸 Gestio - Sistema de Gestão de Finanças Pessoais

Bem-vindo ao **Gestio**, sua plataforma inteligente de controle financeiro pessoal. Aqui você pode criar carteiras, registrar movimentações, controlar dívidas, investimentos e definir metas — tudo isso via um sistema web simples e funcional!

---

## 🚀 Tecnologias Utilizadas

- Java 11+ 
- JSP / Servlets  
- MySQL 8.0
- Apache Tomcat 10.1.39 
- JDBC  
- HTML/CSS/JS puro  

---

## 🛠️ Pré-requisitos

- Java 11+  
- Apache Tomcat 10.1.39+ 
- MySQL 8+  
- Eclipse IDE for Enterprise Java Developers  
- mysql-connector-j-9.2.0.jar

---

## 📦 Instalação e Execução

### 1. Clone o projeto

git clone https://github.com/Melkysedeke/GestioLab1.git

### 2. Importe no Eclipse

File > Import > Existing Projects into Workspace  
Selecione a pasta do projeto.

### 3. Configure o Build Path

Clique com o botão direito no projeto > Build Path > Configure Build Path > Libraries > Add JARs  

### 4. Configure o Tomcat no Eclipse

Servers > New > Server > Apache Tomcat 10.1.39 
Aponte para a pasta do Tomcat baixado.

### 5. Configure o Banco de Dados

Abra o MySQL e execute os seguintes comandos:

-- Criar banco novo
CREATE DATABASE gestio;
USE gestio;

-- Tabela Usuario
CREATE TABLE Usuario (
    cpf BIGINT PRIMARY KEY UNIQUE NOT NULL,
    nome VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    senha VARCHAR(255),
    idUltimaCarteira INT,
    criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela Carteira
CREATE TABLE Carteira (
    idCarteira INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    cpfUsuario BIGINT,
    FOREIGN KEY (cpfUsuario) REFERENCES Usuario(cpf) ON DELETE CASCADE
);


-- Tabela Categoria
CREATE TABLE Categoria (
    idCategoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100)
);

-- Tabela Movimentacao
CREATE TABLE Movimentacao (
    idMovimentacao INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('entrada', 'saida') NOT NULL,
    descricao VARCHAR(255),
    valor DECIMAL(10,2) NOT NULL,
    data DATE NOT NULL,
	formaPagamento ENUM('Dinheiro', 'Cartão de crédito', 'Cartão de débito', 'Pix', 'Boleto', 'Transferência') DEFAULT 'Dinheiro',
    idCarteira INT NOT NULL,
    idCategoria INT,
    criadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizadoEm TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (idCarteira) REFERENCES Carteira(idCarteira) ON DELETE CASCADE,
    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria) ON DELETE SET NULL
);


-- Tabela Objetivo
CREATE TABLE Objetivo (
    idObjetivo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    descricao VARCHAR(255),
    valorObjetivo DECIMAL(10,2),
	valorAtual DECIMAL(10,2) DEFAULT 0.00,
    prazo DATE,
    dataCriacao DATE DEFAULT (CURRENT_DATE),
    status ENUM('Em andamento', 'Atingida', 'Vencida') DEFAULT 'Em andamento',
    idCarteira INT,
    FOREIGN KEY (idCarteira) REFERENCES Carteira(idCarteira) ON DELETE CASCADE
);


-- Tabela Investimento
CREATE TABLE Investimento (
    idInvestimento INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM ('Ações', 'Tesouro Direto', 'CDB', 'LCI', 'LCA', 'Fundos Imobiliários', 'Criptomoedas', 'Poupança', 'Fundos de Investimento', 'Outros'),
    valor DECIMAL(10,2),
    quantidade INT,
    dataCriacao DATE,
    dataVencimento DATE,
    idCarteira INT,
    FOREIGN KEY (idCarteira) REFERENCES Carteira(idCarteira) ON DELETE CASCADE
);

-- Tabela Divida
CREATE TABLE Divida (
    idDivida INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('Dívida', 'Empréstimo') NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    dataCriacao DATE DEFAULT (CURRENT_DATE),
    dataVencimento DATE,
    dataQuitacao DATE,
    status ENUM('pendente', 'pago') DEFAULT 'pendente',
    idCarteira INT NOT NULL,
    FOREIGN KEY (idCarteira) REFERENCES Carteira(idCarteira) ON DELETE CASCADE
);

### 6. Ajuste a conexão com o banco

No arquivo `Conexao.java`, edite conforme seu ambiente:

String url = "jdbc:mysql://localhost:3306/gestio";  
String user = "root";  
String password = "sua_senha";

### 7. Execute o projeto

Botão direito no projeto > Run As > Run on Server

---

## 📊 Funcionalidades principais

- 🔐 Login e cadastro de usuários  
- 💼 Criação de múltiplas carteiras por usuário  
- 💸 Registro de movimentações (receitas e despesas)  
- 📈 Gráfico de gastos por categoria (dinâmico com Chart.js)  
- 💳 Controle de dívidas e empréstimos  
- 📊 Gestão de investimentos  
- 🎯 Metas financeiras com status e progresso  

---

## 🧠 Sobre o projeto

O **Gestio** é voltado para pessoas que querem mais clareza e controle sobre seu dinheiro. A interface é simples, os dados são organizados por carteira e o foco é em visualização e planejamento — com gráficos, metas e categorização de gastos.
