package br.com.gestio.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.gestio.DAO.CarteiraDAO;
import br.com.gestio.DAO.DividaDAO;
import br.com.gestio.DAO.InvestimentoDAO;
import br.com.gestio.DAO.MovimentacaoDAO;
import br.com.gestio.DAO.ObjetivoDAO;
import br.com.gestio.model.Usuario;
import br.com.gestio.util.Conexao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ResumoController")
public class ResumoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioSessao");

        try (Connection conexao = Conexao.getConexao()) {
            CarteiraDAO carteiraDAO = new CarteiraDAO(conexao);
            int idCarteira = carteiraDAO.buscarIdUltimaCarteiraPorCpf(usuario.getCpf());
            

            MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO(conexao);
            DividaDAO dividaDAO = new DividaDAO(conexao);
            InvestimentoDAO investimentoDAO = new InvestimentoDAO(conexao);
            ObjetivoDAO objetivoDAO = new ObjetivoDAO(conexao);

            int entradas = movimentacaoDAO.somarPorTipo(idCarteira, "entrada");
            int saidas = movimentacaoDAO.somarPorTipo(idCarteira, "saida");
            double totalDividas = dividaDAO.somarDividas(idCarteira);
            double totalInvestido = investimentoDAO.somarInvestimentos(idCarteira);
            double progressoObjetivo = objetivoDAO.calcularProgressoTotal(idCarteira);

            request.setAttribute("entradas", entradas);
            request.setAttribute("saidas", saidas);
            request.setAttribute("saldo", entradas - saidas);
            request.setAttribute("totalDividas", totalDividas);
            request.setAttribute("totalInvestido", totalInvestido);
            request.setAttribute("progressoObjetivo", progressoObjetivo);

            request.getRequestDispatcher("pages/resumo.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao carregar dados do resumo.", e);
        }
    }
}
