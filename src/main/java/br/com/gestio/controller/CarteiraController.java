package br.com.gestio.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.gestio.DAO.CarteiraDAO;
import br.com.gestio.model.Carteira;
import br.com.gestio.model.Usuario;
import br.com.gestio.util.Conexao;
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

                    Carteira novaCarteira = new Carteira(0, nomeCarteira, usuario.getCpf());
                    carteiraDAO.criarCarteira(novaCarteira);

                    Carteira carteiraCriada = carteiraDAO.buscarUltimaCarteiraPorCpf(usuario.getCpf());
                    if (carteiraCriada != null) {
                        carteiraDAO.atualizarUltimaCarteira(usuario.getCpf(), carteiraCriada.getIdCarteira());
                    }

                    recarregarSessaoCarteiras(sessao, carteiraDAO, usuario.getCpf());
                    response.sendRedirect(request.getContextPath() + "/ResumoController");
                    break;
                }

                case "editar": {
                    String idCarteiraStr = request.getParameter("idCarteira");
                    String novoNome = request.getParameter("novoNome");

                    if (idCarteiraStr == null || novoNome == null || novoNome.trim().isEmpty()) {
                        request.setAttribute("msg", "Dados inválidos para edição.");
                        request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                        return;
                    }

                    int idCarteira = Integer.parseInt(idCarteiraStr);
                    carteiraDAO.editarCarteira(idCarteira, novoNome.trim());

                    recarregarSessaoCarteiras(sessao, carteiraDAO, usuario.getCpf());
                    response.sendRedirect(request.getContextPath() + "/ResumoController");
                    break;
                }

                case "selecionar": {
                    String idCarteiraStr = request.getParameter("idCarteira");
                    if (idCarteiraStr == null || idCarteiraStr.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }

                    int idCarteira = Integer.parseInt(idCarteiraStr);
                    carteiraDAO.atualizarUltimaCarteira(usuario.getCpf(), idCarteira);
                    Carteira novaCarteira = carteiraDAO.buscarPorId(idCarteira);
                    System.out.println("Carteira selecionada corretamente: " + novaCarteira.getIdCarteira());
                    sessao.setAttribute("carteiraSessao", novaCarteira);
                    recarregarSessaoCarteiras(sessao, carteiraDAO, usuario.getCpf());

                    response.sendRedirect(request.getContextPath() + "/ResumoController");
                    break;
                }


                case "deletar": {
                    String idCarteiraStr = request.getParameter("idCarteira");
                    if (idCarteiraStr == null || idCarteiraStr.isEmpty()) {
                        request.setAttribute("msg", "ID da carteira não informado.");
                        request.getRequestDispatcher("pages/erro.jsp").forward(request, response);
                        return;
                    }

                    int idCarteira = Integer.parseInt(idCarteiraStr);
                    carteiraDAO.excluirCarteira(idCarteira);

                    List<Carteira> carteirasRestantes = carteiraDAO.listarPorCpf(usuario.getCpf());
                    if (!carteirasRestantes.isEmpty()) {
                        Carteira novaCarteira = carteirasRestantes.get(0);
                        carteiraDAO.atualizarUltimaCarteira(usuario.getCpf(), novaCarteira.getIdCarteira());
                    } else {
                        carteiraDAO.atualizarUltimaCarteira(usuario.getCpf(), 0);
                    }

                    recarregarSessaoCarteiras(sessao, carteiraDAO, usuario.getCpf());
                    response.sendRedirect(request.getContextPath() + "/ResumoController");
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

    private void recarregarSessaoCarteiras(HttpSession sessao, CarteiraDAO carteiraDAO, Long cpfUsuario) throws SQLException {
        List<Carteira> carteiras = carteiraDAO.listarPorCpf(cpfUsuario);
        sessao.setAttribute("carteiras", carteiras);

        Carteira carteiraSelecionada = null;
        int idUltimaCarteira = carteiraDAO.buscarIdUltimaCarteiraPorCpf(cpfUsuario);

        if (idUltimaCarteira > 0) {
            carteiraSelecionada = carteiraDAO.buscarPorId(idUltimaCarteira);
        }
        if (carteiraSelecionada == null && !carteiras.isEmpty()) {
            carteiraSelecionada = carteiras.get(0);
            carteiraDAO.atualizarUltimaCarteira(cpfUsuario, carteiraSelecionada.getIdCarteira());
        }

        sessao.setAttribute("carteiraSessao", carteiraSelecionada);
    }
}
