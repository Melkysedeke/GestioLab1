package br.com.financas.controller;

import br.com.financas.DAO.InvestimentoDAO;
import br.com.financas.model.Investimento;
import br.com.financas.util.Conexao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/InvestimentoController")
public class InvestimentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("inserir".equals(acao)) {
            try (Connection conexao = Conexao.getConexao()) {
                String tipo = request.getParameter("tipo");
                double valor = Double.parseDouble(request.getParameter("valor"));
                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                Date data = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));

                String dataVencimentoStr = request.getParameter("dataVencimento");
                Date dataVencimento = null;
                if (dataVencimentoStr != null && !dataVencimentoStr.isEmpty()) {
                    dataVencimento = new SimpleDateFormat("yyyy-MM-dd").parse(dataVencimentoStr);
                }

                HttpSession sessao = request.getSession();
                var carteiraSessao = (br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao");

                if (carteiraSessao == null) {
                    response.sendRedirect("pages/login.jsp");
                    return;
                }

                Investimento investimento = new Investimento(
                    0, tipo, valor, quantidade, data, dataVencimento, carteiraSessao.getIdCarteira()
                );

                InvestimentoDAO dao = new InvestimentoDAO(conexao);
                dao.inserir(investimento);

                // Atualiza a lista na sessão
                List<Investimento> investimentos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
                sessao.setAttribute("investimentoSessao", investimentos);

                response.sendRedirect("pages/investimento.jsp");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if ("deletar".equals(acao)) {
            int idInvestimento = Integer.parseInt(request.getParameter("idInvestimento"));

            try (Connection conexao = Conexao.getConexao()) {
                InvestimentoDAO dao = new InvestimentoDAO(conexao);
                dao.deletar(idInvestimento);

                HttpSession sessao = request.getSession();
                var carteiraSessao = (br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao");

                if (carteiraSessao != null) {
                    List<Investimento> investimentos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
                    sessao.setAttribute("investimentoSessao", investimentos);
                }

                response.sendRedirect("pages/dashboard.jsp");
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }
    }
}
