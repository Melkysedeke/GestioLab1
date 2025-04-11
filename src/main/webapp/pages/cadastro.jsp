<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Cadastro</title>
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
      font-size: 26px;
      font-weight: bold;
      margin-bottom: 10px;
    }

    .form-box p {
      font-size: 14px;
      color: #333;
      margin-bottom: 24px;
    }

    input[type="text"],
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

    .senha-dupla {
      display: flex;
      gap: 10px;
    }

    .senha-dupla input {
      flex: 1;
    }

    button {
      width: 100%;
      padding: 12px;
      background-color: #b4c5e4;
      color: white;
      font-weight: bold;
      font-size: 16px;
      border: none;
      margin-top: 10px;
      cursor: pointer;
    }

    button:hover {
      background-color: #94A8CE;
    }

    .login-text {
      font-size: 13px;
      margin-top: 16px;
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
      <h1>Crie sua conta</h1>
      <p>Alcance o controle total das suas finanças!</p>

      <input type="hidden" name="acao" value="cadastrar" />
      <input type="text" name="nome" placeholder="Nome" required />
      <input type="email" name="email" placeholder="Email" required />
      <input type="text" name="cpf" placeholder="CPF" required />

      <div class="senha-dupla">
        <input type="password" name="senha" placeholder="Digite sua senha" required />
        <input type="password" name="confirmaSenha" placeholder="Redigite sua senha" required />
      </div>

      <button type="submit">Cadastrar</button>

      <p class="login-text">Já possuo uma conta. <a href="login.jsp">Fazer Login</a></p>

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