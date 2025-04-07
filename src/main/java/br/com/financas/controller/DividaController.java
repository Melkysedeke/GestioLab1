package br.com.financas.controller;

import br.com.financas.DAO.DividaDAO;
import br.com.financas.model.Divida;
import br.com.financas.util.Conexao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/DividaController")
public class DividaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        try (Connection conexao = Conexao.getConexao()) {
            DividaDAO dividaDAO = new DividaDAO(conexao);

            if ("prepararPagina".equals(acao)) {
                HttpSession sessao = request.getSession();
                int idCarteira = ((br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();
                List<Divida> dividas = dividaDAO.listarPorCarteira(idCarteira);
                sessao.setAttribute("dividaSessao", dividas);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/divida.jsp");
                dispatcher.forward(request, response);

            } else if ("deletar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("idDivida"));
                dividaDAO.deletar(id);
                response.sendRedirect(request.getContextPath() + "/DividaController?acao=prepararPagina");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        try (Connection conexao = Conexao.getConexao()) {
            DividaDAO dividaDAO = new DividaDAO(conexao);

            if ("inserir".equals(acao)) {
                String descricao = request.getParameter("descricao");
                double valor = Double.parseDouble(request.getParameter("valor"));
                java.util.Date data = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
                String dataQuitacaoStr = request.getParameter("dataQuitacao");
                java.util.Date dataQuitacao = (dataQuitacaoStr != null && !dataQuitacaoStr.isEmpty()) ?
                        new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dataQuitacaoStr) : null;

                HttpSession sessao = request.getSession();
                int idCarteira = ((br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();

                Divida nova = new Divida(0, descricao, valor, data, dataQuitacao, idCarteira);
                dividaDAO.inserir(nova);

                response.sendRedirect(request.getContextPath() + "/DividaController?acao=prepararPagina");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
