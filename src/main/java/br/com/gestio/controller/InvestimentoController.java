package br.com.gestio.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.gestio.DAO.InvestimentoDAO;
import br.com.gestio.model.Investimento;
import br.com.gestio.util.Conexao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/InvestimentoController")
public class InvestimentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String acao = request.getParameter("acao");

	    try (Connection conexao = Conexao.getConexao()) {
	        HttpSession sessao = request.getSession();
	        var carteiraSessao = (br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao");

	        if (carteiraSessao == null) {
	            response.sendRedirect("pages/login.jsp");
	            return;
	        }

	        InvestimentoDAO dao = new InvestimentoDAO(conexao);

	        switch (acao) {
	            case "inserir": {
	                String tipo = request.getParameter("tipo");
	                double valor = Double.parseDouble(request.getParameter("valor"));
	                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
	                Date dataCriacao = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataCriacao"));

	                String dataVencimentoStr = request.getParameter("dataVencimento");
	                Date dataVencimento = null;
	                if (dataVencimentoStr != null && !dataVencimentoStr.isEmpty()) {
	                    dataVencimento = new SimpleDateFormat("yyyy-MM-dd").parse(dataVencimentoStr);
	                }

	                Investimento investimento = new Investimento(
	                    0, tipo, valor, quantidade, dataCriacao, dataVencimento, carteiraSessao.getIdCarteira()
	                );

	                dao.inserir(investimento);

	                break;
	            }
	            case "atualizar": {
	                int idInvestimento = Integer.parseInt(request.getParameter("idInvestimento"));
	                String tipo = request.getParameter("tipo");
	                double valor = Double.parseDouble(request.getParameter("valor"));
	                int quantidade = Integer.parseInt(request.getParameter("quantidade"));
	                Date dataCriacao = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dataCriacao"));

	                String dataVencimentoStr = request.getParameter("dataVencimento");
	                Date dataVencimento = null;
	                if (dataVencimentoStr != null && !dataVencimentoStr.isEmpty()) {
	                    dataVencimento = new SimpleDateFormat("yyyy-MM-dd").parse(dataVencimentoStr);
	                }

	                Investimento investimento = new Investimento(
	                    idInvestimento, tipo, valor, quantidade, dataCriacao, dataVencimento, carteiraSessao.getIdCarteira()
	                );

	                dao.atualizar(investimento);

	                break;
	            }
	            default:
	                throw new ServletException("Ação POST não reconhecida: " + acao);
	        }

	        List<Investimento> investimentos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
	        sessao.setAttribute("investimentosSessao", investimentos);
	        response.sendRedirect(request.getContextPath() + "/InvestimentoController?acao=prepararPagina");

	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String acao = request.getParameter("acao");

	    try (Connection conexao = Conexao.getConexao()) {
	        HttpSession sessao = request.getSession();
	        var carteiraSessao = (br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao");

	        if (carteiraSessao == null) {
	            response.sendRedirect("pages/login.jsp");
	            return;
	        }

	        InvestimentoDAO dao = new InvestimentoDAO(conexao);

	        switch (acao) {
	            case "prepararPagina": {
	                List<Investimento> investimentos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
	                sessao.setAttribute("investimentosSessao", investimentos);

	                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/investimentos.jsp");
	                dispatcher.forward(request, response);
	                return;
	            }
	            case "deletar": {
	                int idInvestimento = Integer.parseInt(request.getParameter("idInvestimento"));
	                dao.deletar(idInvestimento);

	                List<Investimento> investimentos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
	                sessao.setAttribute("investimentosSessao", investimentos);

	                response.sendRedirect(request.getContextPath() + "/InvestimentoController?acao=prepararPagina");
	                return;
	            }
	            default:
	                throw new ServletException("Ação GET não reconhecida: " + acao);
	        }
	    } catch (Exception e) {
	        throw new ServletException(e);
	    }
	}
}
