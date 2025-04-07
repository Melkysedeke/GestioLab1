package br.com.financas.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.financas.DAO.CarteiraDAO;
import br.com.financas.model.Carteira;
import br.com.financas.model.Usuario;
import br.com.financas.util.Conexao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CarteiraController")
public class CarteiraController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioSessao");

        if (usuario == null) {
            response.sendRedirect("pages/login.jsp");
            return;
        }

        try (Connection conexao = Conexao.getConexao()) {
            CarteiraDAO carteiraDAO = new CarteiraDAO(conexao);

            switch (acao) {
                case "criar": {
                    String nomeCarteira = request.getParameter("nomeCarteira");

                    if (nomeCarteira == null || nomeCarteira.trim().isEmpty()) {
                        request.setAttribute("msg", "Nome da carteira inválido.");
                        request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                        return;
                    }

                    Carteira novaCarteira = new Carteira(0, nomeCarteira, usuario.getIdUsuario());
                    carteiraDAO.criarCarteira(novaCarteira);

                    // Atualiza a sessão com a carteira recém-criada
                    Carteira carteiraCriada = carteiraDAO.buscarCarteira(nomeCarteira, usuario.getIdUsuario());
                    if (carteiraCriada != null) {
                        sessao.setAttribute("carteiraSessao", carteiraCriada);
                    }

                    response.sendRedirect("pages/dashboard.jsp");
                    break;
                }
                default: {
                    request.setAttribute("msg", "Ação desconhecida.");
                    request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                    break;
                }
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("msg", "Erro ao processar requisição: " + e.getMessage());
            request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
        }
    }
}
