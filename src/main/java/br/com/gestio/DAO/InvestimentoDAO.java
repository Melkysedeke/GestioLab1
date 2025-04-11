package br.com.gestio.DAO;

import br.com.gestio.model.Investimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestimentoDAO {
    private Connection conexao;

    public InvestimentoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Investimento investimento) throws SQLException {
        String sql = "INSERT INTO investimento (tipo, valor, quantidade, data, dataVencimento, idCarteira) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, investimento.getTipo());
            stmt.setDouble(2, investimento.getValor());
            stmt.setInt(3, investimento.getQuantidade());
            stmt.setDate(4, new java.sql.Date(investimento.getData().getTime()));
            stmt.setDate(5, investimento.getDataVencimento() != null ? new java.sql.Date(investimento.getDataVencimento().getTime()) : null);
            stmt.setInt(6, investimento.getIdCarteira());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    investimento.setIdInvestimento(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Investimento> listarPorCarteira(int idCarteira) throws SQLException {
        List<Investimento> investimentos = new ArrayList<>();
        String sql = "SELECT * FROM investimento WHERE idCarteira = ? ORDER BY data DESC";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Investimento investimento = new Investimento(
                        rs.getInt("idInvestimento"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getInt("quantidade"),
                        rs.getDate("data"),
                        rs.getDate("dataVencimento"),
                        rs.getInt("idCarteira")
                    );
                    investimentos.add(investimento);
                }
            }
        }
        return investimentos;
    }

    public void atualizar(Investimento investimento) throws SQLException {
        String sql = "UPDATE investimento SET tipo = ?, valor = ?, quantidade = ?, data = ?, dataVencimento = ?, idCarteira = ? WHERE idInvestimento = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, investimento.getTipo());
            stmt.setDouble(2, investimento.getValor());
            stmt.setInt(3, investimento.getQuantidade());
            stmt.setDate(4, new java.sql.Date(investimento.getData().getTime()));
            stmt.setDate(5, investimento.getDataVencimento() != null ? new java.sql.Date(investimento.getDataVencimento().getTime()) : null);
            stmt.setInt(6, investimento.getIdCarteira());
            stmt.setInt(7, investimento.getIdInvestimento());

            stmt.executeUpdate();
        }
    }

    public void deletar(int idInvestimento) throws SQLException {
        String sql = "DELETE FROM investimento WHERE idInvestimento = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idInvestimento);
            stmt.executeUpdate();
        }
    }
}
