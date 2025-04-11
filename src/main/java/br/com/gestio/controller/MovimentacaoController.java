package br.com.gestio.controller;

import br.com.gestio.DAO.CategoriaDAO;
import br.com.gestio.DAO.MovimentacaoDAO;
import br.com.gestio.model.Carteira;
import br.com.gestio.model.Categoria;
import br.com.gestio.model.Movimentacao;
import br.com.gestio.util.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/MovimentacaoController")
public class MovimentacaoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String acao = request.getParameter("acao");

        Carteira carteira = (Carteira) session.getAttribute("carteiraSessao");

        if (carteira == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        try (Connection conexao = Conexao.getConexao()) {
            MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conexao);

            switch (acao) {
                case "criar":
                    criarMovimentacao(request, response, movimentacaoDAO, carteira);
                    return;

                case "listar": {
                    List<Movimentacao> movimentacoes = movimentacaoDAO.listarPorCarteira(carteira.getIdCarteira());
                    session.setAttribute("movimentacoes", movimentacoes);
                    return;
                }
                case "atualizar":
                    try {
                        int id = Integer.parseInt(request.getParameter("idMovimentacao"));
                        String tipo = request.getParameter("tipo");
                        String descricao = request.getParameter("descricao");
                        double valor = Double.parseDouble(request.getParameter("valor"));
                        java.util.Date data = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
                        String formaPagamento = request.getParameter("formaPagamento");
                        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
                        int idCarteira = carteira.getIdCarteira();

                        Movimentacao movimentacaoAtualizada = new Movimentacao(id, tipo, descricao, valor, data, formaPagamento, idCarteira, idCategoria);
                        movimentacaoDAO.atualizar(movimentacaoAtualizada);

                        response.sendRedirect(request.getContextPath() + "/MovimentacaoController?acao=prepararPagina");
                        return;
                    } catch (Exception e) {
                        throw new ServletException("Erro ao atualizar a movimentação", e);
                    }
                case "deletar":
                    int idDeletar = Integer.parseInt(request.getParameter("idMovimentacao"));
                    movimentacaoDAO.deletar(idDeletar);
                    response.sendRedirect(request.getContextPath() + "/MovimentacaoController?acao=prepararPagina");
                    return;
                default:
                    request.setAttribute("msg", "Ação inválida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    return;
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar o banco de dados", e);
        } catch (Exception e) {
            throw new ServletException("Erro ao processar os dados da movimentação", e);
        }
    }

    private void criarMovimentacao(HttpServletRequest request, HttpServletResponse response, MovimentacaoDAO movimentacaoDAO, Carteira carteira) throws Exception {
        String tipo = request.getParameter("tipo");
        String descricao = request.getParameter("descricao");
        double valor = Double.parseDouble(request.getParameter("valor"));
        String dataStr = request.getParameter("data");
        String formaPagamento = request.getParameter("formaPagamento");
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = sdf.parse(dataStr);

        Movimentacao movimentacao = new Movimentacao(
            0, tipo, descricao, valor, data, formaPagamento, carteira.getIdCarteira(), idCategoria
        );

        movimentacaoDAO.inserir(movimentacao);
        response.sendRedirect(request.getContextPath() + "/MovimentacaoController?acao=prepararPagina");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String acao = request.getParameter("acao");
        HttpSession session = request.getSession();

        Carteira carteira = (Carteira) session.getAttribute("carteiraSessao");
        if (carteira == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        try (Connection conexao = Conexao.getConexao()) {
            MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conexao);
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);

            switch (acao) {
                case "prepararPagina":
                    List<Categoria> categorias = categoriaDAO.listarPorCpf(carteira.getCpfUsuario());
                    List<Movimentacao> movimentacoes = movimentacaoDAO.listarPorCarteira(carteira.getIdCarteira());

                    session.setAttribute("categoriasSessao", categorias);
                    session.setAttribute("movimentacoesSessao", movimentacoes);

                    request.getRequestDispatcher("pages/movimentacoes.jsp").forward(request, response);
                    break;

                default:
                    request.setAttribute("msg", "Ação inválida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException("Erro ao preparar a página de movimentações", e);
        }
    }

}
