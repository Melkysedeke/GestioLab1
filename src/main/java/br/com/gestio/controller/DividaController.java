package br.com.gestio.controller;

import br.com.gestio.DAO.DividaDAO;
import br.com.gestio.model.Divida;
import br.com.gestio.util.Conexao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/DividaController")
public class DividaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        try (Connection conexao = Conexao.getConexao()) {
            DividaDAO dividaDAO = new DividaDAO(conexao);

            switch (acao) {
                case "prepararPagina": {
                    HttpSession sessao = request.getSession();
                    int idCarteira = ((br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();
                    List<Divida> dividas = dividaDAO.listarPorCarteira(idCarteira);
                    sessao.setAttribute("dividasSessao", dividas);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("pages/dividas.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                case "deletar": {
                    int id = Integer.parseInt(request.getParameter("idDivida"));
                    dividaDAO.deletar(id);
                    response.sendRedirect(request.getContextPath() + "/DividaController?acao=prepararPagina");
                    return;
                }
                default:
                    throw new ServletException("Ação GET não reconhecida: " + acao);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        try (Connection conexao = Conexao.getConexao()) {
            DividaDAO dividaDAO = new DividaDAO(conexao);

            switch (acao) {
                case "criar": {
                    String tipo = request.getParameter("tipo");
                    String descricao = request.getParameter("descricao");
                    double valor = Double.parseDouble(request.getParameter("valor"));

                    Date dataCriacao = new Date();
                    String dataVencStr = request.getParameter("dataVencimento");
                    String dataQuitStr = request.getParameter("dataQuitacao");

                    Date dataVencimento = parseDateOrNull(dataVencStr);
                    Date dataQuitacao = parseDateOrNull(dataQuitStr);

                    String status = request.getParameter("status");

                    HttpSession sessao = request.getSession();
                    int idCarteira = ((br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();

                    Divida nova = new Divida();
                    nova.setTipo(tipo);
                    nova.setDescricao(descricao);
                    nova.setValor(valor);
                    nova.setDataCriacao(dataCriacao);
                    nova.setDataVencimento(dataVencimento);
                    nova.setDataQuitacao(dataQuitacao);
                    nova.setStatus(status);
                    nova.setIdCarteira(idCarteira);

                    dividaDAO.inserir(nova);

                    response.sendRedirect(request.getContextPath() + "/DividaController?acao=prepararPagina");
                    return;
                }

                case "atualizar": {
                    int idDivida = Integer.parseInt(request.getParameter("idDivida"));
                    String tipo = request.getParameter("tipo");
                    String descricao = request.getParameter("descricao");
                    double valor = Double.parseDouble(request.getParameter("valor"));

                    String dataCriacaoStr = request.getParameter("dataCriacao");
                    String dataVencStr = request.getParameter("dataVencimento");
                    String dataQuitStr = request.getParameter("dataQuitacao");

                    Date dataCriacao = parseDateOrNull(dataCriacaoStr);
                    Date dataVencimento = parseDateOrNull(dataVencStr);
                    Date dataQuitacao = parseDateOrNull(dataQuitStr);

                    String status = request.getParameter("status");
                    System.out.println("Status recebido: " + status);

                    HttpSession sessao = request.getSession();
                    int idCarteira = ((br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();

                    Divida dividaAtualizada = new Divida();
                    dividaAtualizada.setIdDivida(idDivida);
                    dividaAtualizada.setTipo(tipo);
                    dividaAtualizada.setDescricao(descricao);
                    dividaAtualizada.setValor(valor);
                    dividaAtualizada.setDataCriacao(dataCriacao);
                    dividaAtualizada.setDataVencimento(dataVencimento);
                    dividaAtualizada.setDataQuitacao(dataQuitacao);
                    dividaAtualizada.setStatus(status);
                    dividaAtualizada.setIdCarteira(idCarteira);

                    dividaDAO.atualizar(dividaAtualizada);

                    response.sendRedirect(request.getContextPath() + "/DividaController?acao=prepararPagina");
                    return;
                }

                default:
                    throw new ServletException("Ação POST não reconhecida: " + acao);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
    private Date parseDateOrNull(String dateStr) throws ParseException {
        return (dateStr != null && !dateStr.isEmpty()) ? sdf.parse(dateStr) : null;
    }
}
