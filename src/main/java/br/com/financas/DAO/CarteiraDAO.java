package br.com.financas.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.financas.model.Carteira;

public class CarteiraDAO {
    private Connection conexao;

    public CarteiraDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarCarteira(Carteira carteira) throws SQLException {
        String sql = "INSERT INTO carteira (nome, idUsuario) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, carteira.getNome());
            stmt.setInt(2, carteira.getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carteira.setIdCarteira(generatedKeys.getInt(1));
                }
            }
        }
    }

    public Carteira buscarCarteira(String nome, int idUsuario) throws SQLException {
        String sql = "SELECT * FROM carteira WHERE nome = ? AND idUsuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Carteira(
                        rs.getInt("idCarteira"),
                        rs.getString("nome"),
                        rs.getInt("idUsuario")
                    );
                }
            }
        }
        return null;
    }

    public List<Carteira> listarPorUsuario(int idUsuario) throws SQLException {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT * FROM carteira WHERE idUsuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Carteira c = new Carteira(
                    rs.getInt("idCarteira"),
                    rs.getString("nome"),
                    rs.getInt("idUsuario")
                );
                carteiras.add(c);
            }
        }
        return carteiras;
    }
}
