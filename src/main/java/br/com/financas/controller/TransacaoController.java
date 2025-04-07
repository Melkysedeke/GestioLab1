package br.com.financas.controller;

import br.com.financas.DAO.CategoriaDAO;
import br.com.financas.DAO.TransacaoDAO;
import br.com.financas.model.Carteira;
import br.com.financas.model.Categoria;
import br.com.financas.model.Transacao;
import br.com.financas.util.Conexao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/TransacaoController")
public class TransacaoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String acao = request.getParameter("acao");

        Carteira carteira = (Carteira) session.getAttribute("carteiraSessao");

        if (carteira == null) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        try (Connection conexao = Conexao.getConexao()) {
            TransacaoDAO transacaoDAO = new TransacaoDAO(conexao);

            switch (acao) {
                case "criar":
                    criarTransacao(request, response, transacaoDAO, carteira);
                    return;

                case "listar":{
                    List<Transacao> transacoes = transacaoDAO.listar(carteira.getIdCarteira());
                    session.setAttribute("transacoes", transacoes);
                    return;
                }
                case "atualizar":
                    try {
                        int id = Integer.parseInt(request.getParameter("idTransacao"));
                        String tipo = request.getParameter("tipo");
                        String descricao = request.getParameter("descricao");
                        double valor = Double.parseDouble(request.getParameter("valor"));
                        java.util.Date data = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
                        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
                        int idCarteira = carteira.getIdCarteira();

                        Transacao transacaoAtualizada = new Transacao(id, tipo, descricao, valor, data, idCarteira, idCategoria);

                        transacaoDAO.atualizar(transacaoAtualizada);

                        response.sendRedirect(request.getContextPath() + "/TransacaoController?acao=prepararPagina");
                        return;
                    } catch (Exception e) {
                        throw new ServletException("Erro ao atualizar a transação", e);
                    }
                case "deletar":
                    int idDeletar = Integer.parseInt(request.getParameter("idTransacao"));
                    transacaoDAO.deletar(idDeletar);
                    response.sendRedirect(request.getContextPath() + "/TransacaoController?acao=prepararPagina");
                    return;
                default:
                    request.setAttribute("msg", "Ação inválida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    return;
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar o banco de dados", e);
        } catch (Exception e) {
            throw new ServletException("Erro ao processar os dados da transação", e);
        }
    }

    private void criarTransacao(HttpServletRequest request, HttpServletResponse response, TransacaoDAO transacaoDAO, Carteira carteira) throws Exception {
        String tipo = request.getParameter("tipo");
        String descricao = request.getParameter("descricao");
        double valor = Double.parseDouble(request.getParameter("valor"));
        String dataStr = request.getParameter("data");
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        // Conversão de data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = sdf.parse(dataStr);

        Transacao transacao = new Transacao(
            0, tipo, descricao, valor, data, carteira.getIdCarteira(), idCategoria
        );
        
        transacaoDAO.inserir(transacao);
        response.sendRedirect(request.getContextPath() + "/TransacaoController?acao=prepararPagina");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        HttpSession session = request.getSession();

        Carteira carteira = (Carteira) session.getAttribute("carteiraSessao");
        if (carteira == null) {
            response.sendRedirect("pages/dashboard.jsp");
            return;
        }

        try (Connection conexao = Conexao.getConexao()) {
            TransacaoDAO transacaoDAO = new TransacaoDAO(conexao);
            CategoriaDAO categoriaDAO = new CategoriaDAO(conexao);

            switch (acao) {
                case "prepararPagina":
                    // Buscar categorias e transações
                    List<Categoria> categorias = categoriaDAO.listar(carteira.getIdCarteira());
                    List<Transacao> transacoes = transacaoDAO.listar(carteira.getIdCarteira());

                    // Setar na sessão ou request
                    session.setAttribute("categoriasSessao", categorias);
                    session.setAttribute("transacoesSessao", transacoes);

                    // Redirecionar para a página de transações
                    response.sendRedirect("pages/transacoes.jsp");
                    break;

                default:
                    request.setAttribute("msg", "Ação inválida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            throw new ServletException("Erro ao preparar a página de transações", e);
        }
    }

}
