<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.gestio.model.Carteira" %>
<%
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
    <link rel="shortcut icon" href="assets/img/bGestio.png" type="image/png">
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
	        --hcor001: #94A8CE;
	        --cor002: #C1121F;
	        --hrcor002: rgb(193, 18, 31, 50%);
	   }
      
      @charset "UTF-8";

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

      header #imgPerfil {
        height: 40px;
        width: 40px;
        background-color: white;
        border-radius: 50%;
        cursor: pointer;
		}
		
		.header #logo {
			height: 40px;
			width: 40px;
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
        margin: 20px auto 0;
        padding: 30px 50px;
        text-align: center;
        max-width: 1216px;
        background-color: var(--cor001);
      }
      
      @media screen and (max-width: 1280px) {
		  .painel {
		    margin: 20px 32px;
		  }
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
      
      /* Em construção*/

      .em-construcao-container {
        margin: auto;
        display: flex;
        align-items: center;
        justify-content: center;
      }

      .em-construcao-box {
        text-align: center;
        background: #ffffff;
        padding: 40px 30px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        max-width: 500px;
        width: 100%;
      }

      .em-construcao-box .emoji {
        font-size: 48px;
        animation: bounce 1.5s infinite;
      }

      .em-construcao-box h1 {
        font-size: 24px;
        color: #333;
        margin-top: 15px;
      }

      .em-construcao-box p {
        color: #666;
        margin: 10px 0 20px;
        font-size: 16px;
      }

      .loader {
        border: 4px solid #e0e0e0;
        border-top: 4px solid #7d97c3;
        border-radius: 50%;
        width: 36px;
        height: 36px;
        margin: 0 auto;
        animation: spin 1s linear infinite;
      }

      @keyframes spin {
        0% {
          transform: rotate(0deg);
        }
        100% {
          transform: rotate(360deg);
        }
      }

      @keyframes bounce {
        0%,
        100% {
          transform: translateY(0);
        }
        50% {
          transform: translateY(-10px);
        }
      }
      
      .dashboard {
		  display: flex;
		  flex-direction: column;
		  gap: 20px;
		  margin: 30px;
		  font-family: Arial, sans-serif;
		  width: 100%;
		}
		
		.linha {
		  display: flex;
		  gap: 20px;
		  flex-wrap: wrap;
		  justify-content: center;
		}
		
		.card {
		  background-color: white;
		  padding: 20px;
		  flex: 1;
		  min-width: 200px;
		  border: 1px solid var(--cor001);
		  box-shadow: 0 2px 4px rgba(0,0,0,0.2);
		}
		
		.card h2, .card h3 {
		  margin-top: 0;
		  color: #333;
		}
		
		.card p, .card span {
		  font-size: 1.2em;
		  font-weight: bold;
		  color: #444;
		}
		
		.card.entrada h3{
			color: green;
		}
		
		.card.saida h3{
			color: var(--cor002);
		}
		
		.card.objetivo {
		  width: 100%;
		  max-width: none;
		  text-align: center;
		  background: white;
		}
		
		.card.objetivo progress {
		  width: 80%;
		  height: 20px;
		  margin: 10px 0 0;
		  border-radius: 10px;
		}
		
		@media (max-width: 768px) {
		  .linha {
		    flex-direction: column;
		    align-items: center;
		  }
		}     
    </style>
  </head>
  <body>
    <header class="header">
      <div>
        <img id="logo" src="assets/img/bGestio.png" alt="Logo" />
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
        <form action="perfil.jsp"><button>Configurações</button></form>
        <form action="pages/assistente.jsp"><button>Ia Assistente</button></form>
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
          <input class="aba" type="radio" name="aba" id="resumo" checked />
          <label for="resumo">Resumo</label>

          <input class="aba" type="radio" name="aba" id="movimentacoes" />
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
		<!-- 
        <div class="em-construcao-container">
          <div class="em-construcao-box">
            <div class="emoji">🚧</div>
            <h1>Em Construção</h1>
            <p>
              Estamos trabalhando nessa funcionalidade. Em breve você poderá
              acessá-la!
            </p>
            <div class="loader"></div>
          </div>
        </div>
		-->
      <div class="dashboard">
		  <div class="linha linha-1">
		    <div class="card saldo">
		      <h2>Saldo Atual</h2>
		      <p>R$ ${saldo}</p>
		    </div>
		    <div class="card entrada">
		      <h3>Entradas</h3>
		      <p>R$ ${entradas}</p>
		    </div>
		    <div class="card saida">
		      <h3>Saídas</h3>
		      <p>R$ ${saidas}</p>
		    </div>
		  </div>
		
		  <div class="linha linha-2">
		    <div class="card divida">
		      <h3>Dívidas Ativas</h3>
		      <p>R$ ${totalDividas}</p>
		    </div>
		    <div class="card investimento">
		      <h3>Investimentos</h3>
		      <p>R$ ${totalInvestido}</p>
		    </div>
		  </div>
		
		  <div class="linha linha-3">
		    <div class="card objetivo">
		      <h3>Progresso dos Objetivos</h3>
		      <progress value="${progressoObjetivo}" max="100"></progress>
		      <span>${progressoObjetivo}%</span>
		    </div>
		  </div>
		</div>

      </div>
      
      <% } else { %>
      <section class="painel">
        <p>
          Organize suas finanças de forma inteligente! Crie sua carteira agora e
          tenha controle total sobre suas receitas, despesas, investimentos e
          metas financeiras.
        </p>
        <form action="pages/criarCarteira.jsp">
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
      } else if (path.includes("investimentos")) {
        document.getElementById("investimentos").checked = true;
      } else if (path.includes("objetivos")) {
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
                  window.location.href = "resumo.jsp";
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
    </script>
  </body>
</html>