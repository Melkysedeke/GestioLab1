<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Gestio</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/index.css" type="text/css">
  <link rel="shortcut icon" href="assets/img/bGestio.png" type="image/png">
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
  
    .header {
      height: 60px;
      background-color: var(--cor001);
      padding: 0 32px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .header div {
      display: flex;
      align-items: center;
      gap: 16px;
    }

    .header div img {
      height: 40px;
      width: 40px;
    }

    .header nav {
      display: flex;
      gap: 10px;
    }

    .header nav button {
      border: none;
      width: 81px;
      height: 32px;
      background-color: white;
      color: #000;
      transition: all 0.3s ease;
      font-weight: bold;
    }

    .header nav button:hover {
      background-color: #d3d3d3;
      cursor: pointer;
    }

    .main {
      padding: 20px 32px;
    }

    .painel {
    	margin: auto;
      max-width: 1216px;
      background-color: var(--cor001);
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 32px;
      flex-wrap: wrap;
    }

    .painel-texto {
      margin-left: 55px;
      max-width: 600px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .painel-texto h2 {
      font-size: 36px;
      width: 100%;
    }

    .painel-texto p {
      margin: 14px 0;
      font-size: 20px;
      width: 100%;
    }

    .painel-texto button {
      border: none;
      width: 237px;
      height: 52px;
      font-size: 20px;
      font-weight: bold;
      background-color: white;
      color: #000;
      cursor: pointer;
      transition: 0.3s;
    }

    .painel-texto button:hover {
      background-color: #ddd;
    }

    .painel img {
      max-width: 500px;
      width: 100%;
      flex: 1;
      object-fit: contain;
    }

    .info {
      max-width: 1216px;
      margin: 0 auto;
      text-align: center;
    }

    .info > h2 {
      font-weight: 400;
      font-size: 24px;
    }

    .info > h2 > span {
      font-weight: bold;
      text-decoration: underline;
    }

    .cards-container {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: 16px;
      margin-top: 32px;
    }

    .card {
      background-color: var(--cor001);
      padding: 16px;
      text-align: left;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      min-height: 120px;
    }

    .card .headerCard {
      display: flex;
      gap: 10px;
      align-items: center;
      margin-bottom: 10px;
    }

    .card h3 {
      font-size: 20px;
    }

    .card small {
      font-size: 14px;
    }

    .card .headerCard div {
      font-weight: bold;
      background-color: white;
      height: 30px;
      width: 30px;
      display: inline-flex;
      justify-content: center;
      align-items: center;
      font-size: 20px;
    }

    .card p {
      font-size: 14px;
      color: #333;
    }

    @media screen and (max-width: 768px) {
      .painel {
        flex-direction: column;
        align-items: flex-start;
      }

      .painel img {
        margin: 0 auto;
      }

      .painel-texto {
        width: 100%;
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
      <form action="pages/login.jsp" method="get" style="display: inline;">
        <button type="submit">Entrar</button>
      </form>
      <form action="pages/cadastro.jsp" method="get" style="display: inline;">
        <button type="submit">Cadastrar</button>
      </form>
    </nav>
  </header>

  <main class="main">
    <section class="painel">
      <div class="painel-texto">
        <h2>Controle Total das Suas Finanças</h2>
        <p>Planeje seus gastos, economize melhor e atinja seus objetivos financeiros com facilidade.</p>
        <form action="pages/cadastro.jsp" method="get">
          <button type="submit">Comece agora</button>
        </form>
      </div>
      <img src="assets/img/dashboardImg.png" alt="dashboard" />
    </section>

    <section class="info">
      <h2>E como o <span>Gestio</span> irá lhe auxiliar?</h2>
      <div class="cards-container">
        <div class="card">
          <div class="headerCard">
            <div>1</div>
            <h3>G<small>estão de Transações</small></h3>
          </div>
          <p>Registre e categorize suas entradas e saídas com facilidade.</p>
        </div>
        <div class="card">
          <div class="headerCard">
            <div>2</div>
            <h3>D<small>ívidas e Empréstimos</small></h3>
          </div>
          <p>Monitore suas dívidas e planeje seus pagamentos.</p>
        </div>
        <div class="card">
          <div class="headerCard">
            <div>3</div> 
            <h3>I<small>nvestimentos e Metas</small></h3>
          </div>
          <p>Acompanhe seus investimentos e defina metas para conquistar seus sonhos.</p>
        </div>
        <div class="card">
          <div class="headerCard">
            <div>4</div>
            <h3>S<small>uporte com IA</small></h3>
          </div>
          <p>Receba insights personalizados e dicas para otimizar seus gastos.</p>
        </div>
      </div>
    </section>
  </main>
</body>
</html>
