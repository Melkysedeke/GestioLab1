<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="br.com.gestio.model.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioSessao");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String criadoEmFormatado = usuario.getCriadoEm() != null ? sdf.format(usuario.getCriadoEm()) : "N/A";
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Perfil - Gestio</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 0;
        }
        
        :root {
	        --cor001: #b4c5e4;
	        --hcor001: #94A8CE;
	        --cor002: #C1121F;
	        --hrcor002: rgb(193, 18, 31, 50%);
	      }

        header {
            background-color: var(--cor001);
            color: white;
            padding: 1rem 2rem;
            display: flex;
            align-items: center;
        }

        header h1 {
            margin: 0;
        }

        .container {
            display: flex;
            justify-content: center;
            padding: 2rem;
        }

        .perfil-box {
            background-color: white;
            padding: 2rem;
            border: 1px solid var(--cor001);
            width: 600px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }

        .voltar {
            font-size: 1.2rem;
            cursor: pointer;
            color: #5c6bc0;
        }

        .foto-perfil {
            background-color: var(--cor001);
            width: 100px;
            height: 100px;
            margin: 1rem auto;
            border-radius: 50%;
        }

        h2 {
            text-align: center;
            margin-bottom: 0.5rem;
        }

        p {
            text-align: center;
            font-style: italic;
            color: #555;
        }

        .form-container {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            margin-top: 1.5rem;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full {
            grid-column: 1 / -1;
        }

        label {
            margin-bottom: 0.3rem;
            color: var(--cor001);
        }

        input {
            padding: 10px;
            border: 1px solid var(--cor001);
        }

        .botoes {
            display: flex;
            justify-content: space-between;
            margin-top: 2rem;
            gap: 10px;
        }

        .btn {
            padding: 10px 20px;
            border: 1px solid;
            font-weight: bold;
            font-size: 14px;
            width: 100%;
            cursor: pointer;
            text-align: center;
            flex: 1;
        }

        .btn-salvar {
            background-color: var(--cor001);
            color: white;
            border: none;
			transition: all .2s ease;
        }

        .btn-salvar:hover {
            background-color: var(--hcor001);
        }

        .btn-logout {
        	text-decoration: none;
            border: 1px solid var(--cor002);
            color: var(--cor002);
            background-color: white;
            transition: all .2s ease;
        }
        
        .btn-logout:hover {
        	color: white;
        	background-color: #C1121F;
        }

        .excluir-container {
            margin-top: 1.5rem;
            display: flex;
            justify-content: center;
        }

        .btn-deletar {
            background-color: var(--hrcor002);
            color: white;
            border: none;
            padding: 10px 30px;
            transition: all .2s ease;
        }

        .btn-deletar:hover {
            background-color: var(--cor002);
        }
    </style>
</head>
<body>
    <header>
        <h1>Gestio</h1>
    </header>

    <div class="container">
        <div class="perfil-box">
            <div class="voltar" onclick="window.history.back();">&#8592;</div>
            <div class="foto-perfil"></div>
            <h2>Minhas informações</h2>
            <p>“O melhor investimento é o que você faz em você mesmo.”</p>

            <form action="<%= request.getContextPath() %>/UsuarioController" method="post">
                <input type="hidden" name="acao" value="atualizar" />

                <div class="form-container">
                    <div class="form-group full">
                        <label for="nome">Nome</label>
                        <input type="text" name="nome" value="<%= usuario.getNome() %>" required />
                    </div>

                    <div class="form-group full">
                        <label for="email">E-mail</label>
                        <input type="email" name="email" value="<%= usuario.getEmail() %>" required />
                    </div>

                    <div class="form-group">
                        <label for="cpf">CPF</label>
                        <input type="text" name="cpf" value="<%= usuario.getCpf() %>" readonly />
                    </div>

                    <div class="form-group">
                        <label for="senha">Nova senha</label>
                        <input type="password" name="senha" placeholder="Digite nova senha" />
                    </div>

                    <div class="form-group">
                        <label for="criadoEm">Criado em</label>
                        <input type="text" value="<%= criadoEmFormatado %>" readonly />
                    </div>

                    <div class="form-group">
                        <label for="senha2">Confirmar senha</label>
                        <input type="password" name="senha2" placeholder="Redigite nova senha" />
                    </div>
                </div>

                <div class="botoes">
                    <button type="submit" class="btn btn-salvar">Salvar</button>
                    <a href="<%= request.getContextPath() %>/LogoutController" class="btn btn-logout">Logout</a>
                </div>
            </form>

            <form action="<%= request.getContextPath() %>/UsuarioController" method="post" onsubmit="return confirmarExclusao();">
                <input type="hidden" name="acao" value="deletar" />
                <div class="excluir-container">
                    <button type="submit" class="btn btn-deletar">Excluir Conta</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function confirmarExclusao() {
            return confirm("Tem certeza que deseja excluir sua conta? Esta ação é irreversível!");
        }
    </script>
</body>
</html>
