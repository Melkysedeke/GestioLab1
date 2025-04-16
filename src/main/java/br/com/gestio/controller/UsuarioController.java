package br.com.gestio.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.gestio.DAO.CarteiraDAO;
import br.com.gestio.DAO.CategoriaDAO;
import br.com.gestio.DAO.DividaDAO;
import br.com.gestio.DAO.InvestimentoDAO;
import br.com.gestio.DAO.ObjetivoDAO;
import br.com.gestio.DAO.UsuarioDAO;
import br.com.gestio.model.Carteira;
import br.com.gestio.model.Categoria;
import br.com.gestio.model.Usuario;
import br.com.gestio.util.Conexao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conexao = Conexao.getConexao()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            switch (acao) {
                case "login": {
                    String email = request.getParameter("email");
                    String senha = request.getParameter("senha");
                    Usuario usuario = usuarioDAO.autenticar(email, senha);
                    if (usuario != null) {
                        HttpSession sessao = request.getSession();
                        sessao.setAttribute("usuarioSessao", usuario);
                        carregarSessaoUsuario(sessao, usuario, conexao);
                        response.sendRedirect(request.getContextPath() + "/ResumoController");
                    } else {
                        HttpSession sessao = request.getSession();
                        sessao.setAttribute("mensagemErro", "E-mail ou senha inválidos.");
                        response.sendRedirect("pages/login.jsp");
                    }
                    return;
                }
                case "cadastrar": {
                    String nomeCadastro = request.getParameter("nome");
                    String emailCadastro = request.getParameter("email");
                    Long cpfCadastro = Long.parseLong(request.getParameter("cpf"));
                    String senhaCadastro = request.getParameter("senha");
                    Usuario existente = usuarioDAO.buscarPorEmailOuCpf(emailCadastro, cpfCadastro);
                    if (existente == null) {
                        Usuario novoUsuario = new Usuario(cpfCadastro, nomeCadastro, emailCadastro, senhaCadastro, null);
                        usuarioDAO.cadastrar(novoUsuario);
                        Usuario usuarioCadastrado = usuarioDAO.autenticar(emailCadastro, senhaCadastro);
                        if (usuarioCadastrado != null) {
                            HttpSession sessao = request.getSession();
                            sessao.setAttribute("usuarioSessao", usuarioCadastrado);
                            carregarSessaoUsuario(sessao, usuarioCadastrado, conexao);
                            response.sendRedirect(request.getContextPath() + "/ResumoController");
                        } else {
                            request.setAttribute("msg", "Erro ao autenticar após cadastro.");
                            request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("msg", "Usuário já cadastrado.");
                        request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    }
                    return;
                }
                case "atualizar": {
                    Usuario usuarioSessao = (Usuario) request.getSession().getAttribute("usuarioSessao");
                    if (usuarioSessao != null) {
                        String nome = request.getParameter("nome");
                        String emailAtualizado = request.getParameter("email");
                        String senhaAtualizado = request.getParameter("senha");

                        usuarioSessao.setNome(nome);
                        usuarioSessao.setEmail(emailAtualizado);
                        if (senhaAtualizado != null && !senhaAtualizado.trim().isEmpty()) {
                            usuarioSessao.setSenha(senhaAtualizado);
                        }
                        usuarioDAO.atualizar(usuarioSessao);
                        request.setAttribute("mensagem", "Perfil atualizado com sucesso!");
                    }
                    request.getRequestDispatcher("pages/perfil.jsp").forward(request, response);
                    return;
                }
                case "deletar": {
                    HttpSession sessao = request.getSession();
                    Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioSessao");
                    if (usuarioLogado != null) {
                        Long cpf = usuarioLogado.getCpf();
                        usuarioDAO.deletar(cpf);
                        sessao.invalidate();
                    }
                    response.sendRedirect("pages/login.jsp");
                    return;
                }
                default:
                    request.setAttribute("msg", "Ação inválida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "Erro ao processar requisição: " + e.getMessage());
            request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
        }
    }
    private void carregarSessaoUsuario(HttpSession sessao, Usuario usuario, Connection conexao) throws SQLException {
        CarteiraDAO carteiraDAO = new CarteiraDAO(conexao);
        List<Carteira> carteiras = carteiraDAO.listarPorCpf(usuario.getCpf());

        Carteira carteiraPrincipal = null;
        if (usuario.getIdUltimaCarteira() != null && usuario.getIdUltimaCarteira() != 0) {
            carteiraPrincipal = carteiraDAO.buscarPorId(usuario.getIdUltimaCarteira());
        }
        if (carteiraPrincipal == null && !carteiras.isEmpty()) {
            carteiraPrincipal = carteiras.get(0);
        }
        sessao.setAttribute("carteiras", carteiras);
        sessao.setAttribute("carteiraSessao", carteiraPrincipal);

        CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);
        List<Categoria> categorias = categoriaDAO.listar();
        sessao.setAttribute("categoriasSessao", categorias);

        if (carteiraPrincipal != null) {
            DividaDAO dividaDAO = new DividaDAO(conexao);
            sessao.setAttribute("dividasSessao", dividaDAO.listarPorCarteira(carteiraPrincipal.getIdCarteira()));

            InvestimentoDAO investimentoDAO = new InvestimentoDAO(conexao);
            sessao.setAttribute("investimentosSessao", investimentoDAO.listarPorCarteira(carteiraPrincipal.getIdCarteira()));

            ObjetivoDAO objetivoDAO = new ObjetivoDAO(conexao);
            sessao.setAttribute("objetivosSessao", objetivoDAO.listarPorCarteira(carteiraPrincipal.getIdCarteira()));
        }
    }
}
