<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Criar Carteira</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #b7c5e5;
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
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      text-align: center;
    }
    .form-box h1 {
      margin-bottom: 10px;
      font-size: 28px;
    }
    .form-box p {
      margin-bottom: 20px;
      font-size: 16px;
      color: #333;
    }
    input[type="text"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 12px;
      border: 1px solid #b7c5e5;
      border-radius: 4px;
      box-sizing: border-box;
    }
    .button-group {
      display: flex;
      gap: 10px;
      justify-content: center;
      margin-top: 20px;
    }
    button {
      flex: 1;
      padding: 12px;
      background-color: #b7c5e5;
      color: white;
      font-weight: bold;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }
    button:hover {
      background-color: #a4b8db;
    }
    small {
      display: block;
      font-size: 10px;
      color: #333;
      margin-top: 20px;
      text-align: center;
    }
  </style>
</head>
<body>
  <div class="container">
    <form class="form-box" action="<%= request.getContextPath() %>/CarteiraController" method="post">
      <h1>Nova Carteira</h1>
      <p>Dê um nome à sua nova carteira financeira</p>
      <input type="hidden" name="acao" value="criar">

      <input type="text" name="nomeCarteira" placeholder="Nome da carteira" required>

      <div class="button-group">
        <button type="submit">Criar</button>
        <button type="button" onclick="window.history.back();">Cancelar</button>
      </div>

      <small>
        Você poderá editar ou remover essa carteira depois, se desejar.
      </small>
    </form>
  </div>
</body>
</html>
