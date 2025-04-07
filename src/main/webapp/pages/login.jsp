<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
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
    input[type="email"],
    input[type="password"] {
        width: 100%;
        padding: 10px;
        margin-bottom: 12px;
        border: 1px solid #b7c5e5;
        border-radius: 4px;
        box-sizing: border-box;
    }
    button {
        width: 100%;
        padding: 12px;
        background-color: #b7c5e5;
        color: white;
        font-weight: bold;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 10px;
    }
    button:hover {
        background-color: #a4b8db;
    }
    .login-text {
        font-size: 12px;
        margin: 10px 0;
    }
    .login-text a {
        color: #7d97c3;
        text-decoration: none;
    }
    .login-text a:hover {
        text-decoration: underline;
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
    <form class="form-box" action="<%= request.getContextPath() %>/UsuarioController" method="post">
      <h1>Login</h1>
      <input type="hidden" name="acao" value="login">
      <input type="email" name="email" placeholder="Email" required>
      <input type="password" name="senha" placeholder="Senha" required>
      <button type="submit">Entrar</button>
      <p class="login-text">Não tem uma conta? <a href="cadastro.jsp">Cadastre-se</a></p>
      <small>
        Ao fazer login no Gestio, você concorda com nossas <a href="#">Políticas de Privacidade</a> e <a href="#">Termos de Uso</a>.
      </small>
    </form>
  </div>
</body>
</html>
