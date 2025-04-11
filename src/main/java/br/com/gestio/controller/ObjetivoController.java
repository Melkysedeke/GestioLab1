package br.com.gestio.controller;

import br.com.gestio.DAO.ObjetivoDAO;

import br.com.gestio.model.Objetivo;
import br.com.gestio.util.Conexao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/ObjetivoController")
public class ObjetivoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");

        try (Connection conexao = Conexao.getConexao()) {
            ObjetivoDAO dao = new ObjetivoDAO(conexao);
            HttpSession sessao = request.getSession();
            var carteiraSessao = (br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao");

            if (carteiraSessao == null) {
                response.sendRedirect("pages/login.jsp");
                return;
            }

            switch (acao) {
                case "criar": {
                    String nome = request.getParameter("nome");
                    String descricao = request.getParameter("descricao");
                    double valorObjetivo = Double.parseDouble(request.getParameter("valorObjetivo"));
                    Date prazo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("prazo"));
                    Date dataCriacao = new Date(); 

                    Objetivo objetivo = new Objetivo(
                        0, nome, descricao, valorObjetivo, 0.0, prazo, dataCriacao, "Em andamento", carteiraSessao.getIdCarteira()
                    );
                    dao.inserir(objetivo);
                    break;
                }

                case "atualizar": {
                    int idObjetivo = Integer.parseInt(request.getParameter("idObjetivo"));
                    String nome = request.getParameter("nome");
                    String descricao = request.getParameter("descricao");
                    double valorObjetivo = Double.parseDouble(request.getParameter("valorObjetivo"));
                    double valorAtual = Double.parseDouble(request.getParameter("valorAtual"));
                    Date prazo = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("prazo"));
                    String status = request.getParameter("status");
                    int idCarteira = Integer.parseInt(request.getParameter("idCarteira"));

                    Objetivo objetivoAtualizado = new Objetivo(
                        idObjetivo, nome, descricao, valorObjetivo, valorAtual, prazo, new Date(), status, idCarteira
                    );

                    dao.atualizar(objetivoAtualizado);
                    break;
                }
            }

            List<Objetivo> objetivos = dao.listarPorCarteira(carteiraSessao.getIdCarteira());
            sessao.setAttribute("objetivosSessao", objetivos);
            response.sendRedirect(request.getContextPath() + "/ObjetivoController?acao=prepararPagina");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        try (Connection conexao = Conexao.getConexao()) {
            ObjetivoDAO objetivoDAO = new ObjetivoDAO(conexao);

            if ("prepararPagina".equals(acao)) {
                HttpSession sessao = request.getSession();
                int idCarteira = ((br.com.gestio.model.Carteira) sessao.getAttribute("carteiraSessao")).getIdCarteira();
                List<Objetivo> objetivos = objetivoDAO.listarPorCarteira(idCarteira);
                sessao.setAttribute("objetivosSessao", objetivos);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/objetivos.jsp");
                dispatcher.forward(request, response);

            } else if ("deletar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("idObjetivo"));
                objetivoDAO.deletar(id);
                response.sendRedirect(request.getContextPath() + "/ObjetivoController?acao=prepararPagina");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
} 
