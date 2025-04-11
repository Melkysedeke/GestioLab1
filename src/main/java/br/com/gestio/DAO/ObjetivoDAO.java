package br.com.gestio.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.gestio.model.Objetivo;

public class ObjetivoDAO {
    private Connection conexao;

    public ObjetivoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Objetivo objetivo) throws SQLException {
        String sql = "INSERT INTO objetivo (nome, descricao, valorObjetivo, valorAtual, prazo, status, idCarteira) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, objetivo.getNome());
            stmt.setString(2, objetivo.getDescricao());
            stmt.setDouble(3, objetivo.getValorObjetivo());
            stmt.setDouble(4, objetivo.getValorAtual());
            stmt.setDate(5, new java.sql.Date(objetivo.getPrazo().getTime()));
            stmt.setString(6, objetivo.getStatus());
            stmt.setInt(7, objetivo.getIdCarteira());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    objetivo.setIdObjetivo(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Objetivo> listarPorCarteira(int idCarteira) throws SQLException {
        List<Objetivo> objetivos = new ArrayList<>();
        String sql = "SELECT * FROM objetivo WHERE idCarteira = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Objetivo objetivo = new Objetivo(
                        rs.getInt("idObjetivo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("valorObjetivo"),
                        rs.getDouble("valorAtual"),
                        rs.getDate("prazo"),
                        rs.getDate("dataCriacao"),
                        rs.getString("status"),
                        rs.getInt("idCarteira")
                    );
                    objetivos.add(objetivo);
                }
            }
        }
        return objetivos;
    }

    public void atualizar(Objetivo objetivo) throws SQLException {
        String sql = "UPDATE objetivo SET nome = ?, descricao = ?, valorObjetivo = ?, valorAtual = ?, prazo = ?, status = ? WHERE idObjetivo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, objetivo.getNome());
            stmt.setString(2, objetivo.getDescricao());
            stmt.setDouble(3, objetivo.getValorObjetivo());
            stmt.setDouble(4, objetivo.getValorAtual());
            stmt.setDate(5, new java.sql.Date(objetivo.getPrazo().getTime()));
            stmt.setString(6, objetivo.getStatus());
            stmt.setInt(7, objetivo.getIdObjetivo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM objetivo WHERE idObjetivo = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
