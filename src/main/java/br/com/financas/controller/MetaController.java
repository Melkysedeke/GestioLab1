package br.com.financas.controller;

import br.com.financas.DAO.MetaDAO;
import br.com.financas.model.Meta;
import br.com.financas.util.Conexao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/MetaController")
public class MetaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        if ("inserir".equals(acao)) {
            try (Connection conexao = Conexao.getConexao()) {
                String descricao = request.getParameter("descricao");
                double valor = Double.parseDouble(request.getParameter("valorMeta"));
                Date prazo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("prazo"));

                HttpSession sessao = request.getSession();
                var carteiraSessao = (br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao");

                if (carteiraSessao == null) {
                    response.sendRedirect("pages/login.jsp");
                    return;
                }

                Meta meta = new Meta(0, descricao, valor, prazo, carteiraSessao.getIdCarteira());

                MetaDAO dao = new MetaDAO(conexao);
                dao.inserir(meta);

                // Atualiza a lista na sessão
                int idCarteira = ((br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();
                List<Meta> metas = dao.listarPorCarteira(idCarteira);
                sessao.setAttribute("metaSessao", metas);

                response.sendRedirect(request.getContextPath() + "/MetaController?acao=prepararPagina");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        try (Connection conexao = Conexao.getConexao()) {
            MetaDAO metaDAO = new MetaDAO(conexao);

            if ("prepararPagina".equals(acao)) {
                HttpSession sessao = request.getSession();
                int idCarteira = ((br.com.financas.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();
                List<Meta> metas = metaDAO.listarPorCarteira(idCarteira);
                sessao.setAttribute("metaSessao", metas);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/meta.jsp");
                dispatcher.forward(request, response);

            } else if ("deletar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("idMeta"));
                metaDAO.deletar(id);
                response.sendRedirect(request.getContextPath() + "/MetaController?acao=prepararPagina");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
