<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.gestio.model.Carteira" %>
<%@ page import="br.com.gestio.DAO.CarteiraDAO" %>
<%@ page import="br.com.gestio.util.Conexao" %>
<%@ page import="java.sql.Connection" %>

<%
    int idCarteira = Integer.parseInt(request.getParameter("id"));
    Connection conexao = Conexao.getConexao();
    CarteiraDAO carteiraDAO = new CarteiraDAO(conexao);
    Carteira carteira = carteiraDAO.buscarPorId(idCarteira);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Carteira</title>
    <link rel="shortcut icon" href="../assets/img/bGestio.png" type="image/png">
    <style>
      body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        background-color: #b4c5e433;
      }
      
      :root {
	        --cor001: #b4c5e4;
	        --hcor001: #94A8CE;
	        --cor002: #C1121F;
	        --hrcor002: rgb(193, 18, 31, 50%);
	   }

      .container {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100vh;
      }

      .form-box {
        background-color: white;
        padding: 40px;
        width: 400px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        text-align: center;
      }

      .form-box h1 {
        margin-bottom: 20px;
        font-size: 24px;
        font-weight: bold;
        color: black;
      }

      .form-group {
        text-align: left;
        margin-bottom: 16px;
      }

      .form-group label {
        display: block;
        font-size: 14px;
        color: var(--cor001);
        margin-bottom: 6px;
      }

      .form-group input[type="text"] {
        width: 100%;
        padding: 10px;
        border: 1px solid var(--cor001);
        font-size: 14px;
        box-sizing: border-box;
        outline: none;
      }

      button {
        width: 100%;
        padding: 12px;
        background-color: var(--cor001);
        color: white;
        font-weight: bold;
        font-size: 16px;
        border: none;
        cursor: pointer;
        margin-top: 10px;
        transition: all .2s ease;
      }

      button:hover {
        background-color: var(--hcor001);
      }

      .btn-excluir {
        background-color: white;
        border: 1px solid var(--cor002);
        color: var(--cor002);
        margin-top: 10px;
        transition: all .2s ease;
      }

      .btn-excluir:hover {
        background-color: var(--cor002);
        color: white;
      }

      .back-link {
        display: block;
        text-align: center;
        margin-top: 20px;
        font-size: 13px;
      }

      .back-link a {
        color: #7d97c3;
        text-decoration: none;
      }

      .back-link a:hover {
        text-decoration: underline;
      }
    </style>
</head>
<body>
  <div class="container">
    <div class="form-box">
      <h1>Editar Carteira</h1>
      <form action="<%= request.getContextPath() %>/CarteiraController" method="post">
        <input type="hidden" name="idCarteira" value="<%= carteira.getIdCarteira() %>">
        <div class="form-group">
          <label for="novoNome">Nome da Carteira</label>
          <input type="text" id="novoNome" name="novoNome" value="<%= carteira.getNome() %>" required>
        </div>
        <button type="submit" name="acao" value="editar">Salvar Alterações</button>
      </form>
      <form action="<%= request.getContextPath() %>/CarteiraController" method="post" onsubmit="return confirm('Tem certeza que deseja excluir esta carteira?');">
        <input type="hidden" name="idCarteira" value="<%= carteira.getIdCarteira() %>">
        <button type="submit" name="acao" value="deletar" class="btn-excluir">Excluir Carteira</button>
      </form>
      <div class="back-link">
	  <a href="javascript:history.back()">← Voltar para a Página Anterior</a>
	</div>
    </div>
  </div>
</body>
</html>
