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
      background-color: #b4c5e433;
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
      box-shadow: 0 4px 8px rgba(0,0,0,0.1);
      text-align: center;
    }

    .form-box h1 {
      margin-bottom: 10px;
      font-size: 26px;
      font-weight: bold;
    }

    .form-box p {
      font-size: 14px;
      color: #333;
      margin-bottom: 24px;
    }

    input[type="email"],
    input[type="password"] {
      width: 100%;
      padding: 10px;
      margin-bottom: 12px;
      border: 1px solid #b4c5e4;
      outline: none;
      box-sizing: border-box;
      font-size: 14px;
    }

    button {
      width: 100%;
      padding: 12px;
      background-color: #b4c5e4;
      color: white;
      font-weight: bold;
      font-size: 16px;
      border: none;
      cursor: pointer;
      margin-top: 10px;
    }

    button:hover {
      background-color: #94A8CE;
    }

    .login-text {
      font-size: 13px;
      margin: 16px 0 8px;
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
      font-size: 11px;
      color: #333;
      margin-top: 24px;
      text-align: center;
    }

    small a {
      color: #0000ee;
      text-decoration: none;
    }

    small a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
  <div class="container">
    <form class="form-box" action="<%= request.getContextPath() %>/UsuarioController" method="post">
      <h1>Acesse sua conta</h1>
      <p>Continue sua jornada rumo à liberdade financeira.</p>

      <input type="hidden" name="acao" value="login">
      <input type="email" name="email" placeholder="Email" required>
      <input type="password" name="senha" placeholder="Digite sua senha" required>
      <button type="submit">Acessar</button>

      <p class="login-text">
        Não tem uma conta? <a href="cadastro.jsp">Cadastre-se</a><br>
        <a href="#">Esqueci minha senha</a>
      </p>

      <small>
        Ao se cadastrar no Gestio, você concorda com nossas
        <a href="#">Políticas de Privacidade</a> e
        <a href="#">Termos de Uso</a>. Garantimos a segurança dos seus dados
        e o uso responsável das suas informações.
      </small>
    </form>
  </div>
</body>
</html>