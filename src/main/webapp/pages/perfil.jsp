<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="br.com.gestio.model.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioSessao");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
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

        header {
            background-color: #a4b8db;
            color: white;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        main {
            padding: 2rem;
        }

        .perfil-container {
            background-color: white;
            padding: 2rem;
            max-width: 600px;
            margin: auto;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }

        h2 {
            color: #1565c0;
            margin-bottom: 1rem;
        }

        label {
            display: block;
            margin-top: 1rem;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 10px;
            margin-top: 0.3rem;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        .botoes {
            margin-top: 2rem;
            display: flex;
            justify-content: space-between;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
        }

        .btn-salvar {
            background-color: #1565c0;
            color: white;
        }

        .btn-salvar:hover {
            background-color: #0d47a1;
        }

        .btn-deletar {
            background-color: #e53935;
            color: white;
        }

        .btn-deletar:hover {
            background-color: #c62828;
        }
    </style>
</head>
<body>
    <header>
        <h1>Gestio</h1>
        <a href="<%= request.getContextPath() %>/LogoutController" style="color:white;">Sair</a>
    </header>

    <main>
        <div class="perfil-container">
            <h2>Meu Perfil</h2>
            <form action="<%= request.getContextPath() %>/UsuarioController" method="post">
		    <input type="hidden" name="acao" value="atualizar" />

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="<%= usuario.getNome() %>" required />

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= usuario.getEmail() %>" required />

                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="••••••••" />
		    <button type="submit" class="btn btn-salvar">Salvar Alterações</button>
			</form>
			
			<!-- Formulário separado para deletar -->
			<form action="<%= request.getContextPath() %>/UsuarioController" method="post" onsubmit="return confirmarExclusao();" style="margin-top: 1rem;">
			    <input type="hidden" name="acao" value="deletar" />
			    <button type="submit" class="btn btn-deletar">Deletar Conta</button>
			</form>
        </div>
    </main>

    <script>
        function confirmarExclusao() {
            return confirm("Tem certeza que deseja excluir sua conta? Esta ação não poderá ser desfeita!");
        }
    </script>
</body>
</html>
