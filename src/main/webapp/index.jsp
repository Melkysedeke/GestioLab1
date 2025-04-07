<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestio</title>
    <style>
    	/* Resetzinho de leve */
		* {
		    margin: 0;
		    padding: 0;
		    box-sizing: border-box;
		}
		
		body {
		    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		    background-color: #f9f9f9;
		    color: #333;
		    line-height: 1.6;
		    min-height: 100vh;
		}
		
		/* Header */
		header {
		    background-color: #a4b8db;
		    color: white;
		    padding: 1rem 2rem;
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		    flex-wrap: wrap;
		}
		
		header div {
	        display: flex;
	        justify-content: center;
	        align-items: center;
	      }
		
		header h1 {
		    margin-left: 1rem;
		    font-size: 2rem;
		}
		
		header img {
		    width: 40px;
		    height: 40px;
		}
		
		header form {
		    display: inline-block;
		    margin-left: 10px;
		}
		
		header button {
		    padding: 8px 16px;
		    background-color: white;
		    color: #a4b8db;
		    border: none;
		    border-radius: 5px;
		    font-weight: bold;
		    cursor: pointer;
		    transition: 0.3s;
		}
		
		header button:hover {
		    background-color: #cce4f6;
		}
		
		/* Navegação */
		nav {
		    background-color: #a4b8db;
		    padding: 0.5rem 1rem;
		}
		
		 nav ul {
	        list-style: none;
	        display: flex;
	        justify-content: center;
	        flex-wrap: wrap;
	      }
		
		nav ul li a {
	        color: white;
	        text-decoration: none;
	        padding: 0.5rem 14px;
	        transition: background 0.3s;
	      }
		
		nav ul li a:hover {
	        border-bottom: 4px solid white;
	      }
		
		/* Main */
		main {
		    padding: 2rem;
		}
		
		section {
		    background-color: white;
		    padding: 2rem;
		    margin-bottom: 2rem;
		    border-radius: 10px;
		    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		h2 {
		    margin-bottom: 1rem;
		    color: #1565c0;
		}
		
		p {
		    color: #555;
		}
    </style>
</head>
<body>
    <header>
        <div>
            <img src="" alt="Logo">
            <h1>Gestio</h1>
        </div>
        <div>
            <form action="pages/login.jsp" method="get" style="display: inline;">
                <button type="submit">Entrar</button>
            </form>
            <form action="pages/cadastro.jsp" method="get" style="display: inline;">
                <button type="submit">Cadastrar</button>
            </form>
        </div>
    </header>
    
    <nav>
        <ul>
            <li><a href="#" onclick="mostrarSecao('dashboard')">Dashboard</a></li>
            <li><a href="#" onclick="mostrarSecao('transacoes')">Transações</a></li>
            <li><a href="#" onclick="mostrarSecao('dividas')">Dívidas e Empréstimos</a></li>
            <li><a href="#" onclick="mostrarSecao('investimentos')">Investimentos</a></li>
            <li><a href="#" onclick="mostrarSecao('metas')">Metas</a></li>
            <li><a href="#" onclick="mostrarSecao('suporte')">Suporte com IA</a></li>
        </ul>
    </nav>

    <main>
        <section id="dashboard" style="display: block;">
            <h2>Visão Geral</h2>
            <p>Aqui será exibido um resumo das receitas e custos, além de relatórios financeiros.</p>
        </section>
        <section id="transacoes" style="display: none;">
            <h2>Transações</h2>
            <p>Área para submissão de receitas e despesas com categorização.</p>
        </section>
        <section id="dividas" style="display: none;">
            <h2>Dívidas e Empréstimos</h2>
            <p>Gerenciamento de dívidas e empréstimos, com prazos e valores.</p>
        </section>
        <section id="investimentos" style="display: none;">
            <h2>Investimentos</h2>
            <p>Gestão de investimentos com informações detalhadas.</p>
        </section>
        <section id="metas" style="display: none;">
            <h2>Metas</h2>
            <p>Definição de objetivos financeiros e prazos para cumprimento.</p>
        </section>
        <section id="suporte" style="display: none;">
            <h2>Suporte com IA</h2>
            <p>Insights financeiros personalizados com base no seu perfil.</p>
        </section>
    </main>

    <script>
        function mostrarSecao(id) {
            const secoes = document.querySelectorAll("main section");
            secoes.forEach(sec => sec.style.display = "none");
            document.getElementById(id).style.display = "block";
        }
    </script>
</body>
</html>
