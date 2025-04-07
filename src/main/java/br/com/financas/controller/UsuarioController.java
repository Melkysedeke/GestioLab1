package br.com.financas.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import br.com.financas.DAO.CarteiraDAO;
import br.com.financas.DAO.CategoriaDAO;
import br.com.financas.DAO.DividaDAO;
import br.com.financas.DAO.InvestimentoDAO;
import br.com.financas.DAO.UsuarioDAO;
import br.com.financas.model.Carteira;
import br.com.financas.model.Categoria;
import br.com.financas.model.Divida;
import br.com.financas.model.Investimento;
import br.com.financas.model.Usuario;
import br.com.financas.util.Conexao;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conexao = Conexao.getConexao()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            switch (acao) {
	            case "login":
	                String email = request.getParameter("email");
	                String senha = request.getParameter("senha");
	                Usuario usuario = usuarioDAO.autenticar(email, senha);
	                if (usuario != null) {
	                	HttpSession sessao = request.getSession();
	                	sessao.setAttribute("usuarioSessao", usuario);
	
	                	CarteiraDAO carteiraDAO = new CarteiraDAO(conexao);
	                	List<Carteira> carteiras = carteiraDAO.listarPorUsuario(usuario.getIdUsuario());
	                	Carteira carteiraPrincipal = carteiras.isEmpty() ? null : carteiras.get(0);
	                	sessao.setAttribute("carteiraSessao", carteiraPrincipal);
	
	                	CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
	                	List<Categoria> categorias = categoriaDAO.listar(usuario.getIdUsuario());
	                	sessao.setAttribute("categoriaSessao", categorias);
	
	                	if (carteiraPrincipal != null) {
	                	    DividaDAO dividaDAO = new DividaDAO(conexao);
	                	    List<Divida> dividas = dividaDAO.listarPorCarteira(carteiraPrincipal.getIdCarteira());
	                	    sessao.setAttribute("dividaSessao", dividas);
	
	                	    InvestimentoDAO investimentoDAO = new InvestimentoDAO(conexao);
	                	    List<Investimento> investimentos = investimentoDAO.listarPorCarteira(carteiraPrincipal.getIdCarteira());
	                	    sessao.setAttribute("investimentoSessao", investimentos);
	                	}
	                	response.sendRedirect("pages/dashboard.jsp");
	                } else {
	                    HttpSession sessao = request.getSession();
	                    sessao.setAttribute("mensagemErro", "E-mail ou senha inválidos.");
	                    response.sendRedirect("pages/login.jsp");
	                }
	                return;
	            case "cadastro":
	                String nomeCadastro = request.getParameter("nome");
	                String emailCadastro = request.getParameter("email");
	                String cpfCadastro = request.getParameter("cpf");
	                String senhaCadastro = request.getParameter("senha");
	                Usuario existente = usuarioDAO.buscarPorEmailOuCpf(emailCadastro, cpfCadastro);
	                if (existente == null) {
	                    Timestamp agora = new Timestamp(System.currentTimeMillis());
	                    Usuario novoUsuario = new Usuario(0, nomeCadastro, emailCadastro, senhaCadastro, cpfCadastro, agora, agora);
	                    usuarioDAO.cadastrar(novoUsuario);
	                    Usuario usuarioCadastrado = usuarioDAO.autenticar(emailCadastro, senhaCadastro);
	                    if (usuarioCadastrado != null) {
	                        HttpSession sessao = request.getSession();
	                        sessao.setAttribute("usuarioSessao", usuarioCadastrado);
	                        response.sendRedirect("pages/criarCarteira.jsp");
	                    } else {
	                    	request.setAttribute("msg", "Erro ao autenticar após cadastro.");
	                    }
	                } else {
	                	request.setAttribute("msg", "Usuário existente.");
	                }
	                return;
	            case "atualizar":{
	                Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");

	                if (usuarioSessao != null) {
	                    String nome = request.getParameter("nome");
	                    String emailAtualizado = request.getParameter("email");
	                    String senhaAtualizado = request.getParameter("senha");

	                    // Atualiza os dados recebidos
	                    usuarioSessao.setNome(nome);
	                    usuarioSessao.setEmail(emailAtualizado);

	                    // Só atualiza a senha se ela for preenchida
	                    if (senhaAtualizado != null && !senhaAtualizado.trim().isEmpty()) {
	                        usuarioSessao.setSenha(senhaAtualizado);
	                    }

	                    // Marcar a hora da atualização
	                    usuarioSessao.setAtualizadoEm(new Timestamp(System.currentTimeMillis()));

	                    usuarioDAO.atualizar(usuarioSessao);
	                    request.setAttribute("mensagem", "Perfil atualizado com sucesso!");
	                }

	                // Redireciona de volta pra página de perfil
	                request.getRequestDispatcher("pages/perfil.jsp").forward(request, response);
	                break;
	            }
	            case "deletar":
	                HttpSession sessao = request.getSession();
	                Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioSessao");
	
	                if (usuarioLogado != null) {
	                    int idUsuario = usuarioLogado.getIdUsuario();
	                    usuarioDAO.deletar(idUsuario);
	                    sessao.invalidate(); // limpando tudo
	                }
	
	                response.sendRedirect("pages/login.jsp");
	                return;
	            default:
	            	request.setAttribute("msg", "Ação inválida.");
	                request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
	                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Erro ao processar requisição: " + e.getMessage());
            request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
        }
    }
}
