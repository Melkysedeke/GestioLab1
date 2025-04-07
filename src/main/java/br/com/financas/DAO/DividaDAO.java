package br.com.financas.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.financas.model.Divida;

public class DividaDAO {
    private Connection conexao;

    public DividaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Divida divida) throws SQLException {
        String sql = "INSERT INTO divida (descricao, valor, data, dataQuitacao, idCarteira) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, divida.getDescricao());
            stmt.setDouble(2, divida.getValor());
            stmt.setDate(3, new java.sql.Date(divida.getData().getTime()));
            stmt.setDate(4, divida.getDataQuitacao() != null ? new java.sql.Date(divida.getDataQuitacao().getTime()) : null);
            stmt.setInt(5, divida.getIdCarteira());
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    divida.setIdDivida(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Divida> listarPorCarteira(int idCarteira) throws SQLException {
        List<Divida> dividas = new ArrayList<>();
        String sql = "SELECT * FROM divida WHERE idCarteira = ? ORDER BY data DESC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dividas.add(new Divida(
                        rs.getInt("idDivida"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getDate("data"),
                        rs.getDate("dataQuitacao"),
                        rs.getInt("idCarteira")
                    ));
                }
            }
        }

        return dividas;
    }

    public void atualizar(Divida divida) throws SQLException {
        String sql = "UPDATE divida SET descricao = ?, valor = ?, data = ?, dataQuitacao = ?, idCarteira = ? WHERE idDivida = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, divida.getDescricao());
            stmt.setDouble(2, divida.getValor());
            stmt.setDate(3, new java.sql.Date(divida.getData().getTime()));
            stmt.setDate(4, divida.getDataQuitacao() != null ? new java.sql.Date(divida.getDataQuitacao().getTime()) : null);
            stmt.setInt(5, divida.getIdCarteira());
            stmt.setInt(6, divida.getIdDivida());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM divida WHERE idDivida = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
