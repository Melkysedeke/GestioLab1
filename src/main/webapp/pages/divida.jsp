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
			<li><a href="pages/dashboard.jsp">Dashboard</a></li>
            <li><a href="<%= request.getContextPath() %>/TransacaoController?acao=prepararPagina">Transações</a></li>
            <li><a href="<%= request.getContextPath() %>/DividaController?acao=prepararPagina">Dívidas e Empréstimos</a></li>
            <li><a href="pages/investimento.jsp">Investimentos</a></li>
            <li><a href="pages/meta.jsp">Metas</a></li>
            <li><a href="pages/suporte.jsp">Suporte com IA</a></li>
        </ul>
    </nav>

    <main>
       <section id="dividas" style="margin-top: 2rem;">
		    <h2>Dívidas e Empréstimos</h2>
		    <p>Gerencie suas dívidas e empréstimos com facilidade.</p>
		
		    <button onclick="abrirModalDivida()" style="margin-bottom: 1rem;">+ Nova Dívida</button>
		    <div id="modalDivida" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 9999; justify-content: center; align-items: center;">
		        <div style="background: white; padding: 2rem; border-radius: 8px; width: 400px; position: relative;">
		            <span onclick="fecharModalDivida()" style="position: absolute; top: 10px; right: 15px; cursor: pointer; font-weight: bold;">&times;</span>
		            <h3>Nova Dívida</h3>
		            <form action="<%= request.getContextPath() %>/DividaController" method="post">
		                <input type="hidden" name="acao" value="inserir" />
		
		                <label for="descricao">Descrição:</label>
		                <input type="text" name="descricao" id="descricao" required style="width: 100%; padding: 0.5rem;" /><br><br>
		
		                <label for="valor">Valor:</label>
		                <input type="number" name="valor" id="valor" step="0.01" required style="width: 100%; padding: 0.5rem;" /><br><br>
		
		                <label for="data">Data:</label>
		                <input type="date" name="data" id="data" required style="width: 100%; padding: 0.5rem;" /><br><br>
		
		                <label for="dataQuitacao">Data de Quitação:</label>
		                <input type="date" name="dataQuitacao" id="dataQuitacao" style="width: 100%; padding: 0.5rem;" /><br><br>
		
		                <button type="submit">Salvar Dívida</button>
		            </form>
		        </div>
		    </div>
		
		    <% List<br.com.financas.model.Divida> dividas = (List<br.com.financas.model.Divida>) session.getAttribute("dividaSessao");
		       if (dividas != null && !dividas.isEmpty()) { %>
		        <table border="1" cellpadding="10" cellspacing="0" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
		            <thead style="background-color: #f0f0f0;">
		                <tr>
		                    <th>Descrição</th>
		                    <th>Valor</th>
		                    <th>Data</th>
		                    <th>Data de Quitação</th>
		                    <th>Ações</th>
		                </tr>
		            </thead>
		            <tbody>
		                <% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		                   for (br.com.financas.model.Divida d : dividas) { %>
		                    <tr>
		                        <td><%= d.getDescricao() %></td>
		                        <td>R$ <%= String.format("%.2f", d.getValor()) %></td>
		                        <td><%= sdf.format(d.getData()) %></td>
		                        <td><%= d.getDataQuitacao() != null ? sdf.format(d.getDataQuitacao()) : "-" %></td>
		                        <td style="text-align: center;">
		                            <form action="<%= request.getContextPath() %>/DividaController" method="get" style="display:inline;">
		                                <input type="hidden" name="acao" value="deletar" />
		                                <input type="hidden" name="idDivida" value="<%= d.getIdDivida() %>" />
		                                <button type="submit" onclick="return confirm('Tem certeza que deseja excluir esta dívida?')">🗑️ Excluir</button>
		                            </form>
		                        </td>
		                    </tr>
		                <% } %>
		            </tbody>
		        </table>
		    <% } else { %>
		        <p style="margin-top: 20px; font-style: italic;">Nenhuma dívida registrada ainda.</p>
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
    
    function abrirModalMeta() {
        const modal = document.getElementById('modalMeta');
        modal.style.display = 'flex';
    }

    function fecharModalMeta() {
        const modal = document.getElementById('modalMeta');
        modal.style.display = 'none';
    }

    function abrirModalEditarMeta(id, descricao, valorMeta, prazo) {
        document.getElementById("editarIdMeta").value = id;
        document.getElementById("editarDescricaoMeta").value = descricao;
        document.getElementById("editarValorMeta").value = valorMeta;
        document.getElementById("editarPrazoMeta").value = prazo;

        document.getElementById("modalEditarMeta").style.display = "block";
    }

    function fecharModalEditarMeta() {
        document.getElementById("modalEditarMeta").style.display = "none";
    }

    // Fecha os modais de meta clicando fora
    window.addEventListener('click', function(event) {
        const modalMeta = document.getElementById('modalMeta');
        const contentMeta = document.getElementById('modalMetaContent');
        if (event.target === modalMeta) {
            modalMeta.style.display = 'none';
        }

        const modalEditarMeta = document.getElementById('modalEditarMeta');
        if (event.target === modalEditarMeta) {
            fecharModalEditarMeta();
        }
    });
</script>
    
</body>
</html>
