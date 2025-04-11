package br.com.gestio.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.gestio.model.Divida;

public class DividaDAO {
    private Connection conexao;

    public DividaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Divida divida) throws SQLException {
        String sql = "INSERT INTO divida (tipo, descricao, valor, dataCriacao, dataVencimento, dataQuitacao, status, idCarteira) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, divida.getTipo());
            stmt.setString(2, divida.getDescricao());
            stmt.setDouble(3, divida.getValor());
            stmt.setDate(4, new java.sql.Date(divida.getDataCriacao().getTime()));
            stmt.setDate(5, divida.getDataVencimento() != null ? new java.sql.Date(divida.getDataVencimento().getTime()) : null);
            stmt.setDate(6, divida.getDataQuitacao() != null ? new java.sql.Date(divida.getDataQuitacao().getTime()) : null);
            stmt.setString(7, divida.getStatus());
            stmt.setInt(8, divida.getIdCarteira());
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
        String sql = "SELECT * FROM divida WHERE idCarteira = ? ORDER BY dataCriacao DESC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Divida divida = new Divida();
                    divida.setIdDivida(rs.getInt("idDivida"));
                    divida.setTipo(rs.getString("tipo"));
                    divida.setDescricao(rs.getString("descricao"));
                    divida.setValor(rs.getDouble("valor"));
                    divida.setDataCriacao(rs.getDate("dataCriacao"));
                    divida.setDataVencimento(rs.getDate("dataVencimento"));
                    divida.setDataQuitacao(rs.getDate("dataQuitacao"));
                    divida.setStatus(rs.getString("status"));
                    divida.setIdCarteira(rs.getInt("idCarteira"));
                    dividas.add(divida);
                }
            }
        }

        return dividas;
    }

    public void atualizar(Divida divida) throws SQLException {
        String sql = "UPDATE divida SET tipo = ?, descricao = ?, valor = ?, dataCriacao = ?, dataVencimento = ?, dataQuitacao = ?, status = ?, idCarteira = ? WHERE idDivida = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, divida.getTipo());
            stmt.setString(2, divida.getDescricao());
            stmt.setDouble(3, divida.getValor());
            stmt.setDate(4, new java.sql.Date(divida.getDataCriacao().getTime()));
            stmt.setDate(5, divida.getDataVencimento() != null ? new java.sql.Date(divida.getDataVencimento().getTime()) : null);
            stmt.setDate(6, divida.getDataQuitacao() != null ? new java.sql.Date(divida.getDataQuitacao().getTime()) : null);
            stmt.setString(7, divida.getStatus());
            stmt.setInt(8, divida.getIdCarteira());
            stmt.setInt(9, divida.getIdDivida());
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
