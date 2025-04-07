<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.financas.model.Carteira" %>
<%
    List<Carteira> carteiras = (List<Carteira>) request.getAttribute("carteiras");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Gestio</title>
    <style>
    	/* Resetzinho de leve */
		* {
		    margin: 0;
		    padding: 0;
		    box-sizing: border-box;
		}
		
		body {
		    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		    background-color: #f9f9f9;
		    color: #333;
		    line-height: 1.6;
		    min-height: 100vh;
		}
		
		/* Header */
		header {
		    background-color: #a4b8db;
		    color: white;
		    padding: 1rem 2rem;
		    display: flex;
		    justify-content: space-between;
		    align-items: center;
		    flex-wrap: wrap;
		}
		
		header div {
	        display: flex;
	        justify-content: center;
	        align-items: center;
	      }
		
		header h1 {
		    margin-left: 1rem;
		    font-size: 2rem;
		}
		
		header img {
		    width: 40px;
		    height: 40px;
		}
		
		header form {
		    display: inline-block;
		    margin-left: 10px;
		}
		
		header button {
		    padding: 8px 16px;
		    background-color: white;
		    color: #a4b8db;
		    border: none;
		    border-radius: 5px;
		    font-weight: bold;
		    cursor: pointer;
		    transition: 0.3s;
		}
		
		header button:hover {
		    background-color: #cce4f6;
		}
		
		/* Navegação */
		nav {
		    background-color: #a4b8db;
		    padding: 0.5rem 1rem;
		}
		
		 nav ul {
	        list-style: none;
	        display: flex;
	        justify-content: center;
	        flex-wrap: wrap;
	      }
		
		nav ul li a {
	        color: white;
	        text-decoration: none;
	        padding: 0.5rem 14px;
	        transition: background 0.3s;
	      }
		
		nav ul li a:hover {
	        border-bottom: 4px solid white;
	      }
		
		/* Main */
		main {
		    padding: 2rem;
		}
		
		section {
		    background-color: white;
		    padding: 2rem;
		    margin-bottom: 2rem;
		    border-radius: 10px;
		    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		h2 {
		    margin-bottom: 1rem;
		    color: #1565c0;
		}
		
		p {
		    color: #555;
		}
		
		.modal-usuario {
		    display: none;
		    position: absolute;
		    right: 10px;
		    top: 60px;
		    background-color: white;
		    border: 1px solid #ccc;
		    border-radius: 12px;
		    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
		    padding: 10px;
		    z-index: 1000;
		}
		
		.modal-conteudo {
		    display: flex;
		    flex-direction: column;
		    gap: 10px;
		}
		
		.botao-modal {
		    padding: 10px;
		    background-color: #f5f5f5;
		    border: none;
		    text-align: left;
		    border-radius: 8px;
		    cursor: pointer;
		    text-decoration: none;
		    color: #333;
		    font-size: 14px;
		    transition: background 0.2s;
		}
		
		.botao-modal:hover {
		    background-color: #e0e0e0;
		}
    </style>
</head>
<body>
    <header style="display: flex; justify-content: space-between; align-items: center; padding: 1rem;">
    <!-- Logo e nome -->
    <div style="display: flex; align-items: center; gap: 0.5rem;">
        <img src="assets/logo.png" alt="Logo" style="width: 40px;" />
        <h1>Gestio</h1>
    </div>

    <div style="display: flex; align-items: center; gap: 1rem;">
		<%
		    Carteira carteiraSessao = (Carteira) session.getAttribute("carteiraSessao");
		    if (carteiraSessao != null) {
		%>
		    <h2><%= carteiraSessao.getNome() %></h2>
		<%
		    } else {
		%>
		    <p>Nenhuma carteira selecionada.</p>
		<%
		    }
		%>
        <!-- Ícone do usuário -->
		<img src="assets/user.png" alt="Usuário" style="width: 40px; border-radius: 50%; cursor: pointer;"
		     onclick="abrirModal()" />
		<!-- Modal -->
		<div id="modalUsuario" class="modal-usuario">
		    <div class="modal-conteudo">
		        <a href="perfil.jsp" class="botao-modal">Perfil</a>
		        
		        <form action="<%= request.getContextPath() %>/LogoutController" method="get" style="margin: 0;">
		            <button type="submit" class="botao-modal">Sair</button>
		        </form>
		    </div>
	    </div>
	</div>
	</header>
    <nav>
        <ul>
            <li><a href=dashboard.jsp">Dashboard</a></li>
            <li><a href="<%= request.getContextPath() %>/TransacaoController?acao=prepararPagina">Transações</a></li>
            <li><a href="<%= request.getContextPath() %>/DividaController?acao=prepararPagina">Dívidas e Empréstimos</a></li>
            <li><a href="investimento.jsp">Investimentos</a></li>
            <li><a href="meta.jsp">Metas</a></li>
            <li><a href="suporte.jsp">Suporte com IA</a></li>
        </ul>
    </nav>

    <main>
        <section id="investimentos">
    <h2>Investimentos</h2>
    <p>Registre e acompanhe seus investimentos.</p>

    <form action="<%= request.getContextPath() %>/InvestimentoController" method="post" style="margin-bottom: 2rem;">
        <input type="hidden" name="acao" value="inserir" />

        <label for="tipo">Tipo:</label>
		<select name="tipo" id="tipo" required>
		    <option value="" disabled selected>Selecione o tipo de investimento</option>
		    <option value="CDB">CDB</option>
		    <option value="CDI">CDI</option>
		    <option value="LCA">LCA</option>
		    <option value="LCI">LCI</option>
		    <option value="FII">FII</option>
		    <option value="Ação">Ação</option>
		    <option value="FOF">FOF</option>
		</select>

        <label for="valor">Valor (R$):</label>
        <input type="number" name="valor" id="valor" step="0.01" required /><br><br>

        <label for="quantidade">Quantidade:</label>
        <input type="number" name="quantidade" id="quantidade" required /><br><br>

        <label for="data">Data do Investimento:</label>
        <input type="date" name="data" id="data" required /><br><br>

        <label for="dataVencimento">Data de Vencimento:</label>
        <input type="date" name="dataVencimento" id="dataVencimento" /><br><br>

        <button type="submit">Cadastrar Investimento</button>
    </form>

    <%
        List<br.com.financas.model.Investimento> investimentos = (List<br.com.financas.model.Investimento>) session.getAttribute("investimentoSessao");
        if (investimentos != null && !investimentos.isEmpty()) {
    %>
        <h2>Meus Investimentos</h2>
        <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; width: 100%; margin-bottom: 2rem;">
            <thead style="background-color: #f0f0f0;">
                <tr>
                    <th>Tipo</th>
                    <th>Valor</th>
                    <th>Quantidade</th>
                    <th>Data</th>
                    <th>Data de Vencimento</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                    for (br.com.financas.model.Investimento i : investimentos) {
                %>
                    <tr>
                        <td><%= i.getTipo() %></td>
                        <td>R$ <%= String.format("%.2f", i.getValor()) %></td>
                        <td><%= i.getQuantidade() %></td>
                        <td><%= sdf.format(i.getData()) %></td>
                        <td><%= i.getDataVencimento() != null ? sdf.format(i.getDataVencimento()) : "-" %></td>
                        <td>
                            <form action="<%= request.getContextPath() %>/InvestimentoController" method="get" style="display:inline;">
                                <input type="hidden" name="acao" value="deletar" />
                                <input type="hidden" name="idInvestimento" value="<%= i.getIdInvestimento() %>" />
                                <button type="submit" onclick="return confirm('Tem certeza que deseja excluir este investimento?')">Excluir</button>
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <% } else { %>
        <p>Nenhum investimento registrado ainda.</p>
    <% } %>
</section>

    </main>
    <script>
    function abrirModal() {
        const modal = document.getElementById('modalUsuario');
        modal.style.display = modal.style.display === 'block' ? 'none' : 'block';
    }

    // Fecha modal clicando fora
    window.onclick = function(event) {
        const modal = document.getElementById('modalUsuario');
        if (event.target !== modal && !modal.contains(event.target) && event.target.tagName !== "IMG") {
            modal.style.display = 'none';
        }
    };
</script>
    
</body>
</html>
