<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg = (String) request.getAttribute("msg");
    if (msg == null) {
        msg = "Ocorreu um erro inesperado. 😥";
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Ops! Algo deu errado</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .error-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 600px;
        }

        h1 {
            color: #dc3545;
            font-size: 2.2rem;
            margin-bottom: 20px;
        }

        p {
            font-size: 1.1rem;
            margin-bottom: 30px;
        }

        a.button {
            background-color: #007bff;
            color: white;
            text-decoration: none;
            padding: 12px 24px;
            border-radius: 8px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        a.button:hover {
            background-color: #0056b3;
        }

        .emoji {
            font-size: 3rem;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="emoji">🚧</div>
    <h1>Algo deu errado!</h1>
    <p><%= msg %></p>
    <a href="javascript:history.back()" class="button">Voltar</a>
</div>
</body>
</html>
