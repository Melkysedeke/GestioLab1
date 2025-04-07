<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.financas.model.Carteira" %>
<%@ page import="br.com.financas.model.Usuario" %>
<%@ page import="br.com.financas.model.Transacao" %>
<%@ page import="br.com.financas.model.Categoria" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<Transacao> transacoes = (List<Transacao>) session.getAttribute("transacoesSessao");
    List<Categoria> categorias = (List<Categoria>) session.getAttribute("categoriasSessao");
    
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
		<img src="assets/user.png" alt="Usuário" style="width: 40px; border-radius: 50%; cursor: pointer;"
		     onclick="abrirModal()" />
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
            <li><a href="dashboard.jsp">Dashboard</a></li>
            <li><a href="<%= request.getContextPath() %>/TransacaoController?acao=prepararPagina">Transações</a></li>
            <li><a href="<%= request.getContextPath() %>/DividaController?acao=prepararPagina">Dívidas e Empréstimos</a></li>
            <li><a href="investimento.jsp">Investimentos</a></li>
            <li><a href="meta.jsp">Metas</a></li>
            <li><a href="suporte.jsp">Suporte com IA</a></li>
        </ul>
    </nav>
    <main>
        <section id="transacoes">
            <h2>Transações</h2>
            <p>Área para submissão de receitas e despesas com categorização.</p>
            <button onclick="abrirModalTransacao()" style="margin-bottom: 1rem;">+ Nova Transação</button>
            <div id="modalTransacao" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
		    background-color: rgba(0,0,0,0.5); z-index: 9999; justify-content: center; align-items: center;">
		    
		    <div id="modalTransacaoContent" style="background: white; padding: 2rem; border-radius: 8px; width: 400px; position: relative;">
		        <span onclick="fecharModalTransacao()" style="position: absolute; top: 10px; right: 15px; cursor: pointer; font-weight: bold;">&times;</span>
		        
		        <h3>Nova Transação</h3>
		        <form action="<%= request.getContextPath() %>/TransacaoController" method="post">
		            <input type="hidden" name="acao" value="criar">
		        
		            <label for="tipo">Tipo:</label>
		            <select name="tipo" id="tipo" required>
		                <option value="entrada">Entrada</option>
		                <option value="saida">Saída</option>
		            </select><br><br>
		        
		            <label for="descricao">Descrição:</label>
		            <input type="text" name="descricao" id="descricao" required><br><br>
		        
		            <label for="valor">Valor:</label>
		            <input type="number" step="0.01" name="valor" id="valor" required><br><br>
		        
		            <label for="data">Data:</label>
		            <input type="date" name="data" id="data" required><br><br>
		        
		            <label for="categoria">Categoria:</label>
		            <select name="idCategoria" id="categoria" required>
		                <%
		                    if (categorias != null) {
		                        for (Categoria cat : categorias) {
		                %>
		                    <option value="<%= cat.getIdCategoria() %>"><%= cat.getNome() %></option>
		                <%
		                        }
		                    }
		                %>
		            </select><br><br>
		            
		            <button type="submit">Salvar Transação</button>
		        </form>
		    </div>
		</div>
		<div id="modalEditarTransacao" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 9999;">
		  <div style="background-color: white; padding: 20px; width: 400px; margin: 10% auto; border-radius: 8px; position: relative;">
		    <h3>Editar Transação</h3>
		    <form action="<%= request.getContextPath() %>/TransacaoController" method="post">
		      <input type="hidden" name="acao" value="atualizar" />
		      <input type="hidden" name="idTransacao" id="editarIdTransacao" />
		
		      <label for="editarTipo">Tipo:</label>
		      <select name="tipo" id="editarTipo" required>
		        <option value="entrada">Entrada</option>
		        <option value="saida">Saída</option>
		      </select>
		
		      <label for="editarDescricao">Descrição:</label>
		      <input type="text" name="descricao" id="editarDescricao" required />
		
		      <label for="editarValor">Valor:</label>
		      <input type="number" step="0.01" name="valor" id="editarValor" required />
		
		      <label for="editarData">Data:</label>
		      <input type="date" name="data" id="editarData" required />
		
		      <label for="editarCategoria">Categoria:</label>
		      <select name="idCategoria" id="editarCategoria" required>
		        <% if (categorias != null) {
		            for (Categoria cat : categorias) { %>
		              <option value="<%= cat.getIdCategoria() %>"><%= cat.getNome() %></option>
		        <%  }
		        } %>
		      </select>
		
		      <br><br>
		      <button type="submit">Salvar</button>
		      <button type="button" onclick="fecharModalEditar()">Cancelar</button>
		    </form>
		  </div>
		</div>
			<%
			    if (transacoes != null && !transacoes.isEmpty()) {
			%>
			    <table border="1" cellpadding="10" cellspacing="0" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
			        <thead style="background-color: #f0f0f0;">
			            <tr>
			                <th>Tipo</th>
			                <th>Descrição</th>
			                <th>Valor</th>
			                <th>Data</th>
			                <th>Categoria</th>
			                <th>Ações</th>
			            </tr>
			        </thead>
			        <tbody>
			            <%
			                for (Transacao t : transacoes) {
			            %>
			                <tr>
			                    <td><%= t.getTipo() %></td>
			                    <td><%= t.getDescricao() %></td>
			                    <td>R$ <%= String.format("%.2f", t.getValor()) %></td>
			                    <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(t.getData()) %></td>
			                    <td><%= t.getIdCategoria() %></td> <%-- Substituir por nome da categoria futuramente --%>
			                    <td style="text-align: center;">
			                        <button type="button" onclick="abrirModalEditar(<%= t.getIdTransacao() %>, '<%= t.getTipo() %>', '<%= t.getDescricao() %>', <%= t.getValor() %>, '<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(t.getData()) %>', <%= t.getIdCategoria() %>)">
									    ✏️ Editar
									</button>

			                        <form action="<%= request.getContextPath() %>/TransacaoController" method="post" style="display: inline;">
									    <input type="hidden" name="acao" value="deletar" />
									    <input type="hidden" name="idTransacao" value="<%= t.getIdTransacao() %>" />
									    <button type="submit" onclick="return confirm('Tem certeza que deseja deletar esta transação?')">🗑️ Deletar</button>
									</form>
			                    </td>
			                </tr>
			            <%
			                }
			            %>
			        </tbody>
			    </table>
			<%
			    } else {
			%>
			    <p style="margin-top: 20px; font-style: italic;">Nenhuma transação registrada ainda.</p>
			<%
			    }
			%>
        </section>
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
		
		    // Fechar o modal se clicar fora do conteúdo
		    window.onclick = function(event) {
		        const modal = document.getElementById("modalTransacao");
		        if (event.target == modal) {
		            modal.style.display = "none";
		        }
		    }
		    
		    function abrirModalTransacao() {
		        const modal = document.getElementById('modalTransacao');
		        modal.style.display = 'flex';
		    }
		
		    function fecharModalTransacao() {
		        const modal = document.getElementById('modalTransacao');
		        modal.style.display = 'none';
		    }
		
		    // Fecha modal se clicar fora do conteúdo (sem afetar o modal do perfil)
		    window.addEventListener('click', function(event) {
		        const modal = document.getElementById('modalTransacao');
		        const content = document.getElementById('modalTransacaoContent');
		        if (event.target === modal) {
		            modal.style.display = 'none';
		        }
		    });
		    
		    function abrirModalEditar(id, tipo, descricao, valor, data, idCategoria) {
		        document.getElementById("editarIdTransacao").value = id;
		        document.getElementById("editarTipo").value = tipo;
		        document.getElementById("editarDescricao").value = descricao;
		        document.getElementById("editarValor").value = valor;
		        document.getElementById("editarData").value = data;
		        document.getElementById("editarCategoria").value = idCategoria;

		        document.getElementById("modalEditarTransacao").style.display = "block";
		      }

		      function fecharModalEditar() {
		        document.getElementById("modalEditarTransacao").style.display = "none";
		      }

		      // Fecha o modal ao clicar fora dele
		      window.onclick = function(event) {
		        const modal = document.getElementById("modalEditarTransacao");
		        if (event.target === modal) {
		          fecharModalEditar();
		        }
		      }
		</script>
    </main>
</body>
</html>
