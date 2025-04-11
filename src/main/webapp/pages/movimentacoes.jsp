<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.gestio.model.Carteira" %>
<%@ page import="br.com.gestio.model.Usuario" %>
<%@ page import="br.com.gestio.model.Movimentacao" %>
<%@ page import="br.com.gestio.model.Categoria" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioSessao");
    List<Movimentacao> movimentacoes = (List<Movimentacao>) session.getAttribute("movimentacoesSessao");
    List<Categoria> categorias = (List<Categoria>) session.getAttribute("categoriasSessao");
    List<Carteira> carteiras = (List<Carteira>) session.getAttribute("carteiras");
    Carteira carteiraSessao = (Carteira) session.getAttribute("carteiraSessao");
    boolean temCarteira = carteiraSessao != null;
%>

<!DOCTYPE html>
<html lang="pt-br">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Gestio</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/abasPrincipais.css" type="text/css">
    <link rel="stylesheet" href="../assets/css/emConstrucao.css" type="text/css">
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
      integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
      crossorigin="anonymous"
      referrerpolicy="no-referrer"
    />
    <style>
      * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        font-family: Arial, Helvetica, sans-serif;
      }

      html,
      body {
        height: 100vh;
      }

      main {
        height: calc(100% - 60px);
      }

      :root {
        --cor001: #b4c5e4;
      }
      
      /* Header */

      .header {
        height: 60px;
        background-color: var(--cor001);
        padding: 0 32px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        z-index: 100;
      }

      .header div {
        display: flex;
        align-items: center;
        gap: 16px;
      }

      .header img {
        height: 40px;
        width: 40px;
        background-color: white;
        border-radius: 50%;
        cursor: pointer;
      }

      .header nav button {
        width: 46px;
        height: 46px;
      }

      .header nav {
        display: flex;
        align-items: center;
        gap: 16px;
        font-size: 13px;
      }

      .header nav strong {
        font-weight: bold;
      }

      /* Header - Menu */

      #menu {
        position: absolute;
        flex-direction: column;
        width: 134px;
        top: 60px;
        right: 0;
        gap: 0;
        box-shadow: 2px 0px 4px rgba(0, 0, 0, 0.2);
        z-index: 1;
        max-height: 0;
        overflow: hidden;
        transition: all 0.3s ease-in-out;
      }

      #menu.aberto {
        max-height: 101px;
      }

      #menu button {
        width: 134px;
        height: 20px;
        padding: 0 0 0 10px;
        border: none;
        background-color: white;
        text-align: left;
        cursor: pointer;
        transition: all 0.2s;
      }

      #menu button:hover {
        background-color: lightgray;
      }

      #btnMenu {
        border: none;
        background-color: var(--cor001);
        cursor: pointer;
      }

      /* Com Carteira */

      #btnCarteira {
        font-size: 13px;
        font-weight: bold;
        text-decoration: underline;
        width: auto;
        border: none;
        background-color: var(--cor001);
        cursor: pointer;
      }

      .carteira-box {
        display: flex;
        flex-direction: column;
        background: #fff;
        border: 1px solid #ccc;
        padding: 6px;
        width: 220px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        z-index: 1;
      }

      .carteira-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 6px 6px;
        font-size: 12px;
        font-weight: bold;
        cursor: pointer;
        transition: all 0.2s ease;
      }

      .carteira-item:hover {
        background-color: #f2f4f8;
      }

      .carteira-item input[type="radio"] {
        display: none;
      }

      .carteira-item .opcoes {
        color: white;
        font-size: 20px;
        cursor: pointer;
        height: 24px;
        width: 24px;
        border-radius: 50%;
        border: none;
        background-color: #b4c5e4;
        flex-shrink: 0;
        text-align: center;
        text-decoration: none;
      }

      .botao-add-carteira {
        margin-top: 10px;
        width: 100%;
        background: none;
        border: none;
        color: #8ba3d3;
        font-size: 22px;
        cursor: pointer;
        padding: 8px 0;
        transition: color 0.2s ease;
      }

      .botao-add-carteira:hover {
        color: #4a6fb3;
      }

      #carteira {
        position: absolute;
        display: none;
        top: 60px;
        right: 143px;
        width: 111px;
        box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
      }

      /* Sem carteira */

      .painel {
        margin: 20px 32px 0;
        padding: 30px 50px;
        text-align: center;
        max-width: 1216px;
        background-color: var(--cor001);
      }

      .painel p {
        max-width: 880px;
        margin: 0px auto 24px auto;
      }

      .painel button {
        width: 300px;
        height: 50px;
        font-size: 24px;
        font-weight: bold;
        border: none;
        transition: all 0.3s ease;
      }

      .painel button:hover {
        background-color: lightgray;
        cursor: pointer;
      }  

      /* Tela - Main */

      .tela {
        display: flex;
        height: 100%;
        font-family: Arial, sans-serif;
      }

      /* Navegação Lateral */

      .navegacaoLateral {
        flex-shrink: 0;
        width: 188px;
        height: 100%;
        border-right: 1px solid var(--cor001);
      }

      .navegacaoLateral input[type="radio"] {
        display: none;
      }

      .navegacaoLateral label {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 40px;
        font-weight: bold;
        background-color: white;
        color: var(--cor001);
        cursor: pointer;
        transition: all 0.2s;
        user-select: none;
      }

      .navegacaoLateral input[type="radio"]:checked + label {
        background: var(--cor001);
        color: white;
      }

      /* Seção */

      .secao-movimentacoes {
        margin: 20px 32px 20px;
        width: 100%;
        height: fit-content;
        background: #ffffff;
        border: 1px solid #858585;
        font-family: Arial, sans-serif;
      }

      .topo-movimentacoes {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 16px 24px;
        border-bottom: 1px solid #858585;
      }

      .topo-movimentacoes h2 {
        margin: 0;
        font-size: 18px;
        color: #000000;
      }

      .header-topo {
        display: flex;
        align-items: center;
        gap: 20px;
      }

      .btn-nova {
        background-color: #b4c5e4;
        border: none;
        color: #ffffff;
        font-weight: bold;
        width: 133px;
        height: 24px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        font-size: 12px;
        transition: all .2s ease;
      }

      .btn-nova:hover {
        background-color: #94a8ce;
      }

      .search-box {
        position: relative;
        z-index: 0;
      }

      .search-box input {
        padding: 6px 32px 6px 8px;
        border: 1px solid #858585;
        border-radius: 2px;
        font-size: 13px;
        color: #000;
      }

      .icone-lupa {
        position: absolute;
        right: 8px;
        top: 50%;
        transform: translateY(-50%);
        color: #757575;
        font-size: 14px;
        pointer-events: none;
      }

      .conteudo {
        font-size: 30px;
        color: #000000;
        text-align: center;
      }
      
      .nenhuma-movimentacao {
      	margin: 10px 0;
      }
      
      .tabela-movimentacoes {
      	border-collapse: collapse;
      	width: 100%;
      	table-layout: fixed;
      	text-align: center;
      }
      
      .tabela-movimentacoes > thead {
      	background: var(--cor001);
      	border-spacing: 0px;
      	height: 30px;
      	width: auto;
      }
      
      .tabela-movimentacoes thead th {
      	font-size: 15px;
      	width: auto;
      }
      
      .tabela-movimentacoes tbody tr {
      	height: 30px;
      	border-bottom: 1px solid rgba(133, 133, 133, 0.5);
      }
      
      .tabela-movimentacoes tbody td {
      	font-size: 12px;
      	width: auto;
      }
      
      /* Modal Overlay */
      
		.modal-overlay {
		  position: absolute;
		  top: 0;
		  left: 0;
		  width: 100%;
		  height: 100%;
		  background-color: rgba(0, 0, 0, 0.2);
		  display: flex;
		  align-items: center;
		  justify-content: center;
		  z-index: 999;
		  backdrop-filter: blur(4px); /* A mágica acontece aqui */
  		-webkit-backdrop-filter: blur(4px); /* Suporte para Safari */
		}
		
		.modal-content {
      margin: auto;
		  background-color: #fff;
		  padding: 24px 20px;
		  width: 100%;
		  max-width: 500px;
		  position: relative;
		  box-shadow: 0px 0px 20px rgba(0,0,0,0.1);
		}
		
		.titulo-form {
		  text-align: center;
		  font-size: 20px;
		  font-weight: bold;
		}
		
		.botoes-tipo {
		  display: flex;
		  justify-content: space-between;
      gap: 10px;
		  margin: 32px 0;
		}
		
		.botao-tipo {
		  flex: 1;
		  height: 30px;
      		display: flex;
      	justify-content: center;
      	align-items: center;
		  cursor: pointer;
		  border: 1px solid #858585;
		  background-color: #fff;
		  transition: all 0.3s ease;
		  font-weight: bold;
      color: #858585;
		}
		
		input[type="radio"]:checked + .botao-tipo {
		  background-color: var(--cor001);
		  color: white;
      border: 1px solid var(--cor001);
		}
		
		.label-input {
		  font-size: 12px;
		  color: var(--cor001);
		  margin-top: 1rem;
		  display: block;
		}
		
		.input-texto {
		  width: 100%;
		  padding: 0 10px;
		  margin-top: 0.3rem;
		  border: 1px solid var(--cor001);
		  background-color: white;
      height: 30px;
		}
		
		/* === Linha dupla para data e categoria === */
		.linha-dupla {
		  display: flex;
		  gap: 10px;
		  margin-top: 1rem;
		}
		
		.campo {
		  flex: 1;
		}
		
		/* === Botões de ação === */
		.botoes-acoes {
		  display: flex;
		  justify-content: space-between;
		  margin-top: 2rem;
      gap: 10px;
      height: 30px;
		}
		
		.botao {
      display: flex;
      justify-content: center;
      align-items: center;
		  border: none;
		  cursor: pointer;
		  width: 100%;
		  font-weight: 600;
		  font-size: 0.95rem;
      transition: all .3s ease;
		}
		
		.salvar {
		  background-color: var(--cor001);
		  color: white;
		}

    .salvar:hover {
      background-color: #94A8CE;
    }
		
		.cancelar {
		  background-color: white;
		  color: #858585;
      border:1px solid #858585;
		}

    .cancelar:hover {
      background-color: lightgray;
    }
    </style>
  </head>
  <body>
    <header class="header">
      <div>
        <img src="" alt="Logo" />
        <h1>Gestio</h1>
      </div>
      <nav>
        <% if (temCarteira) { %>
        <button id="btnCarteira">
          <%= carteiraSessao.getNome() %><i
            class="fa fa-chevron-down"
            id="iconeCarteira"
            style="color: #000000; margin-left: 4px"
          ></i>
        </button>
        <% } else { %>
        <p>Não há<br /><strong>CARTEIRA</strong></p>
        <% } %>
        <button id="btnMenu">Menu</button>
        <img src="" id="imgPerfil" alt="Logo" />
      </nav>
      <div id="menu">
        <form action="pages/perfil.jsp"><button>Meu Perfil</button></form>
        <form action=""><button>Configurações</button></form>
        <form action=""><button>Tema Claro/Escuro</button></form>
        <form action=""><button>Ia Assistente</button></form>
        <form action="<%= request.getContextPath() %>/LogoutController">
          <button>Sair</button>
        </form>
      </div>
      <div id="carteira" class="carteira-box">
        <form
          id="formCarteiras"
          action="<%= request.getContextPath() %>/CarteiraController"
          method="post"
        >
          <input type="hidden" name="acao" value="selecionar" />
          <% if (carteiras != null) { int index = 0; for (Carteira c :
          carteiras) { boolean selecionada = carteiraSessao != null &&
          c.getIdCarteira() == carteiraSessao.getIdCarteira(); String inputId =
          "carteira_" + index; %>
          <div class="carteira-item" style="position: relative">
            <input type="radio" name="idCarteira" value="<%= c.getIdCarteira()
            %>" id="<%= inputId %>" <%= selecionada ? "checked" : "" %>
            onchange="document.getElementById('formCarteiras').submit()" />
            <label for="<%= inputId %>"><span><%= c.getNome() %></span></label>
            <a
              class="opcoes"
              href="pages/editarCarteira.jsp?id=<%= c.getIdCarteira() %>"
              onclick="event.stopPropagation();"
              >⋮</a
            >
          </div>
          <% index++; } } else { %>
          <p>Sem carteiras disponíveis.</p>
          <% } %>
        </form>
        <button
          id="btnNovaCarteira"
          onclick="abrirModalCriarCarteira()"
          class="botao-add-carteira"
        >
          <i class="fa-solid fa-circle-plus" style="color: #b4c5e4"></i>
        </button>
      </div>
    </header>
    <main>
      <% if (temCarteira) { %>
      <div class="tela">
        <aside class="navegacaoLateral">
          <input class="aba" type="radio" name="aba" id="resumo"/>
          <label for="resumo">Resumo</label>

          <input class="aba" type="radio" name="aba" id="movimentacoes"/>
          <label for="movimentacoes">Movimentações</label>

          <input class="aba" type="radio" name="aba" id="dividas" />
          <label for="dividas">Dívidas</label>

          <input class="aba" type="radio" name="aba" id="investimentos" />
          <label for="investimentos">Investimentos</label>
          
          <input class="aba" type="radio" name="aba" id="objetivos" />
          <label for="objetivos">Objetivos</label>

          <input class="aba" type="radio" name="aba" id="ia" />
          <label for="ia">Assistente IA</label>
        </aside>
        <section id="movimentacoes" class="secao-movimentacoes">
		  <div class="topo-movimentacoes">
		    <div class="header-topo">
		      <h2>Minhas movimentações</h2>
		      <button class="btn-nova" onclick="abrirModalMovimentacao()">Nova movimentação</button>
		    </div>
		    <div class="search-box">
		      <input type="text" placeholder="Pesquisar" />
		      <span class="icone-lupa">🔍</span>
		    </div>
		  </div>
		
		  <!-- Modal de criação -->
		  <div id="modalMovimentacao" class="modal-overlay" style="display: none;">
		    <div class="modal-content">
		      <form action="<%= request.getContextPath() %>/MovimentacaoController" method="post" class="form-movimentacao">
				  <input type="hidden" name="acao" value="criar">

				  <h2 class="titulo-form">Adicionar Movimentação</h2>
				
				  <div class="botoes-tipo">
				    <input type="radio" name="tipo" value="entrada" id="entrada" checked hidden>
				    <label for="entrada" class="botao-tipo">Entrada</label>
				
				    <input type="radio" name="tipo" value="saida" id="saida" hidden>
				    <label for="saida" class="botao-tipo">Saída</label>
				  </div>
				
				  <label for="descricao" class="label-input">Descrição (opcional)</label>
				  <input type="text" name="descricao" id="descricao" placeholder="Descreva a movimentação (ex: Netflix, Farmácia, Transferência...)" class="input-texto">
				
				  <div class="linha-dupla">
				    <div class="campo">
				      <label for="data" class="label-input">Data da movimentação</label>
				      <input type="date" name="data" id="data" class="input-texto">
				    </div>
				
				    <div class="campo">
				      <label for="categoria" class="label-input">Categoria</label>
				      <select name="idCategoria" id="categoria" class="input-texto">
				        <% if (categorias != null) {
				          for (Categoria cat : categorias) {
				        %>
				          <option value="<%= cat.getIdCategoria() %>"><%= cat.getNome() %></option>
				        <% } } %>
				      </select>
				    </div>
				  </div>
				
				  <label for="formaPagamento" class="label-input">Forma de Pagamento</label>
				  <select name="formaPagamento" id="formaPagamento" class="input-texto">
				    <option value="Dinheiro">Dinheiro</option>
				    <option value="Cartão de crédito">Cartão de crédito</option>
				    <option value="Cartão de débito">Cartão de débito</option>
				    <option value="Pix">Pix</option>
				    <option value="Boleto">Boleto</option>
				    <option value="Transferência">Transferência</option>
				  </select>
				
				  <label for="valor" class="label-input">Valor da movimentação</label>
				  <input type="number" step="0.01" name="valor" id="valor" class="input-texto" placeholder="R$ 00,00">
				
				  <div class="botoes-acoes">
				    <button type="submit" class="botao salvar">Salvar</button>
				    <button type="button" onclick="fecharModalMovimentacao()" class="botao cancelar">Cancelar</button>
				  </div>
				</form>
		    </div>
		  </div>
		
		  <!-- Modal de edição -->
		  <div id="modalEditarMovimentacao" class="modal-overlay" style="display: none">
			<div class="modal-content">
		    		<form action="<%= request.getContextPath() %>/MovimentacaoController" method="post" class="form-movimentacao">
		        		<input type="hidden" name="acao" value="atualizar" />
						<input type="hidden" name="idMovimentacao" id="editarIdMovimentacao" />
		        		<h2 class="titulo-form">Editar Movimentação</h2>
		        		<div class="botoes-tipo" id="editarTipo">
						    <input type="radio" name="tipo" value="entrada" id="editarEntrada" hidden />
						    <label for="editarEntrada" class="botao-tipo">Entrada</label>
						
						    <input type="radio" name="tipo" value="saida" id="editarSaida" hidden />
						    <label for="editarSaida" class="botao-tipo">Saída</label>
						</div>

			       		<label for="editarDescricao" class="label-input">Descrição (opcional)</label>
		        		<input type="text" name="descricao" id="editarDescricao" placeholder="Descreva a movimentação (ex: Netflix, Farmácia, Transferência...)" class="input-texto" />
		
			        	<div class="linha-dupla">
		        			<div class="campo">
		            				<label for="editarData" class="label-input">Data da movimentação</label>
		            				<input type="date" name="data" id="editarData" class="input-texto" />
		        			</div>
		
			        		<div class="campo">
		        	    			<label for="editarCategoria" class="label-input">Categoria</label>
		            				<select name="idCategoria" id="editarCategoria" class="input-texto">
		            					<% if (categorias != null) { for (Categoria cat : categorias) {
		            					%>
		            					<option value="<%= cat.getIdCategoria() %>">
		                					<%= cat.getNome() %>
		            					</option>
		            					<% } } %>
		            				</select>
		        			</div>
		        		</div>
		
			      	  	<label for="editarFormaPagamento" class="label-input">Forma de Pagamento</label>
		        		<select name="formaPagamento" id="editarFormaPagamento" class="input-texto">
		        			<option value="Dinheiro">Dinheiro</option>
		        			<option value="Cartão de crédito">Cartão de crédito</option>
		        			<option value="Cartão de débito">Cartão de débito</option>
		        			<option value="Pix">Pix</option>
		        			<option value="Boleto">Boleto</option>
		        			<option value="Transferência">Transferência</option>
		        		</select>
		
			       		<label for="editarValor" class="label-input">Valor da movimentação</label>
		        		<input type="number" step="0.01" name="valor" id="editarValor" class="input-texto" placeholder="R$ 00,00" />
			        	<div class="botoes-acoes">
		        			<button type="submit" class="botao salvar">Salvar</button>
		        			<button type="button" onclick="fecharModalEditar()" class="botao cancelar"> Cancelar </button>
		        		</div>
		    		</form>
			</div>
		</div>

		  <div class="conteudo">
		    <% if (movimentacoes != null && !movimentacoes.isEmpty()) { %>
		      <table class="tabela-movimentacoes">
		        <thead>
		          <tr>
		            <th>Tipo</th>
		            <th>Descrição</th>
		            <th>Valor</th>
		            <th>Data</th>
		            <th>Pagamento</th>
		            <th>Categoria</th>
		            <th>Ações</th>
		          </tr>
		        </thead>
		        <tbody>
		          <% for (Movimentacao m : movimentacoes) { %>
		            <tr>
		              <td><%= m.getTipo() %></td>
		              <td><%= m.getDescricao() %></td>
		              <td>R$ <%= String.format("%.2f", m.getValor()) %></td>
		              <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(m.getData()) %></td>
		              <td><%= m.getFormaPagamento() %></td>
		              <td><%= m.getNomeCategoria() %></td>
		              <td>
		                <button type="button" onclick="abrirModalEditar(
		                  <%= m.getIdMovimentacao() %>,
		                  '<%= m.getTipo() %>',
		                  '<%= m.getDescricao().replace("'", "\\'") %>',
		                  <%= m.getValor() %>,
		                  '<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(m.getData()) %>',
		                  '<%= m.getFormaPagamento() %>',
		                  <%= m.getIdCategoria() %>)">✏️ Editar</button>
		
		                <form action="<%= request.getContextPath() %>/MovimentacaoController" method="post" style="display:inline;">
		                  <input type="hidden" name="acao" value="deletar" />
		                  <input type="hidden" name="idMovimentacao" value="<%= m.getIdMovimentacao() %>" />
		                  <button type="submit" onclick="return confirm('Tem certeza que deseja deletar esta movimentação?')">🗑️ Deletar</button>
		                </form>
		              </td>
		            </tr>
		          <% } %>
		        </tbody>
		      </table>
		    <% } else { %>
		      <p class="nenhuma-movimentacao">Não há movimentações</p>
		    <% } %>
		  </div>
		</section>
      </div>
      <% } else { %>
      <section class="painel">
        <p>
          Organize suas finanças de forma inteligente! Crie sua carteira agora e
          tenha controle total sobre suas receitas, despesas, investimentos e
          metas financeiras.
        </p>
        <form action="criarCarteira.jsp">
          <button>Criar uma carteira</button>
        </form>
      </section>
      <% } %>
    </main>
    <script>
      // abrir menu

      const btnMenu = document.getElementById("btnMenu");
      const imgPerfil = document.getElementById("imgPerfil");
      const menu = document.getElementById("menu");

      btnMenu.addEventListener("click", abrirMenu);
      imgPerfil.addEventListener("click", abrirMenu);

      function abrirMenu() {
        menu.classList.toggle("aberto");
      }

      document.addEventListener("click", (event) => {
        if (
          !menu.contains(event.target) &&
          event.target !== btnMenu &&
          event.target !== imgPerfil
        ) {
          menu.classList.remove("aberto");
        }
      });

      // abrir carteira

      const btnCarteira = document.getElementById("btnCarteira");
      const modalCarteira = document.getElementById("carteira");

      btnCarteira?.addEventListener("click", (event) => {
        event.stopPropagation();
        const aberto = modalCarteira.style.display === "block";
        modalCarteira.style.display = aberto ? "none" : "block";

        if (iconeCarteira) {
          iconeCarteira.classList.toggle("fa-chevron-down", aberto);
          iconeCarteira.classList.toggle("fa-chevron-up", !aberto);
        }
      });

      document.addEventListener("click", (event) => {
        if (
          !modalCarteira.contains(event.target) &&
          event.target !== btnCarteira
        ) {
          modalCarteira.style.display = "none";
        }
      });

      function abrirModalCriarCarteira() {
        window.location.href = "pages/criarCarteira.jsp";
      }

   // alterna página

      const path = window.location.pathname;
      if (path.includes("resumo")) {
        document.getElementById("resumo").checked = true;
      } else if (path.includes("movimentacoes")) {
        document.getElementById("movimentacoes").checked = true;
      } else if (path.includes("MovimentacaoController")) {
    	  document.getElementById("movimentacoes").checked = true;
      } else if (path.includes("dividas")) {
        document.getElementById("dividas").checked = true;
      } else if (path.includes("DividaController")) {
        document.getElementById("dividas").checked = true;
      } else if (path.includes("investimentos")) {
        document.getElementById("investimentos").checked = true;
      } else if (path.includes("InvestimentoController")) {
        document.getElementById("investimentos").checked = true;
      } else if (path.includes("objetivos")) {
          document.getElementById("objetivos").checked = true;
      } else if (path.includes("ObjetivoController")) {
          document.getElementById("objetivos").checked = true;
      } else if (path.includes("assistente")) {
        document.getElementById("ia").checked = true;
      }

      document
        .querySelectorAll('.navegacaoLateral input[type="radio"]')
        .forEach((radio) => {
          radio.addEventListener("change", function () {
            if (this.checked) {
              switch (this.id) {
                case "resumo":
                  window.location.href = "pages/resumo.jsp";
                  break;
                case "movimentacoes":
                  window.location.href = "<%= request.getContextPath() %>/MovimentacaoController?acao=prepararPagina";
                  break;
                case "dividas":
                  window.location.href = "<%= request.getContextPath() %>/DividaController?acao=prepararPagina";
                  break;
                case "investimentos":
                  window.location.href = "<%= request.getContextPath() %>/InvestimentoController?acao=prepararPagina";
                  break;
                case "objetivos":
                    window.location.href = "<%= request.getContextPath() %>/ObjetivoController?acao=prepararPagina";
                    break;
                case "ia":
                  window.location.href = "pages/assistente.jsp";
                  break;
                default:
                  console.warn("Aba não mapeada: " + this.id);
              }
            }
          });
        });

      // movimentacao
      
      function abrirModalMovimentacao() {
	    document.getElementById('modalMovimentacao').style.display = 'flex';
	  }
	
	  function fecharModalMovimentacao() {
	    document.getElementById('modalMovimentacao').style.display = 'none';
	  }
	
	  function abrirModalEditar(id, tipo, descricao, valor, data, formaPagamento, idCategoria) {
		    document.getElementById('editarIdMovimentacao').value = id;
		    const radios = document.getElementsByName("tipo");
		    radios.forEach(radio => {
		        radio.checked = (radio.value === tipo);
		    });
		    document.getElementById('editarDescricao').value = descricao;
		    document.getElementById('editarValor').value = valor;
		    document.getElementById('editarData').value = data;
		    document.getElementById('editarFormaPagamento').value = formaPagamento;
		    document.getElementById('editarCategoria').value = idCategoria;
		    document.getElementById('modalEditarMovimentacao').style.display = 'flex';
		}

	  function fecharModalEditar() {
	    document.getElementById('modalEditarMovimentacao').style.display = 'none';
	  }
	
	  // Fecha os modais se clicar fora deles
	  window.onclick = function(event) {
	    const modalCriar = document.getElementById('modalMovimentacao');
	    const modalEditar = document.getElementById('modalEditarMovimentacao');
	
	    if (event.target == modalCriar) {
	      modalCriar.style.display = "none";
	    }
	
	    if (event.target == modalEditar) {
	      modalEditar.style.display = "none";
	    }
	  }
    </script>
  </body>
</html>