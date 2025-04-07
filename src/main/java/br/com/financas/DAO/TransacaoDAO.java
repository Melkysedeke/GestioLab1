package br.com.financas.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.financas.model.Transacao;

public class TransacaoDAO {
    private final Connection conexao;

    public TransacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO transacao (tipo, descricao, valor, data, idCarteira, idCategoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transacao.getTipo());
            stmt.setString(2, transacao.getDescricao());
            stmt.setDouble(3, transacao.getValor());
            stmt.setDate(4, new java.sql.Date(transacao.getData().getTime()));
            stmt.setInt(5, transacao.getIdCarteira());
            stmt.setInt(6, transacao.getIdCategoria());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transacao.setIdTransacao(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Transacao> listar(int idCarteira) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE idCarteira = ? ORDER BY data DESC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transacao transacao = new Transacao(
                        rs.getInt("idTransacao"),
                        rs.getString("tipo"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getDate("data"),
                        rs.getInt("idCarteira"),
                        rs.getInt("idCategoria")
                    );
                    transacoes.add(transacao);
                }
            }
        }
        return transacoes;
    }

    /**
     * Busca uma transação específica pelo ID.
     */
    public Transacao buscarPorId(int idTransacao) throws SQLException {
        String sql = "SELECT * FROM transacao WHERE idTransacao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idTransacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Transacao(
                        rs.getInt("idTransacao"),
                        rs.getString("tipo"),
                        rs.getString("descricao"),
                        rs.getDouble("valor"),
                        rs.getDate("data"),
                        rs.getInt("idCarteira"),
                        rs.getInt("idCategoria")
                    );
                }
            }
        }
        return null; // ou lançar uma exceção se quiser ser mais rígido
    }

    /**
     * Atualiza uma transação existente.
     */
    public void atualizar(Transacao transacao) throws SQLException {
        String sql = "UPDATE transacao SET tipo = ?, descricao = ?, valor = ?, data = ?, idCategoria = ? WHERE idTransacao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, transacao.getTipo());
            stmt.setString(2, transacao.getDescricao());
            stmt.setDouble(3, transacao.getValor());
            stmt.setDate(4, new java.sql.Date(transacao.getData().getTime()));
            stmt.setInt(5, transacao.getIdCategoria());
            stmt.setInt(6, transacao.getIdTransacao());
            stmt.executeUpdate();
        }
    }

    /**
     * Deleta uma transação pelo ID.
     */
    public void deletar(int idTransacao) throws SQLException {
        String sql = "DELETE FROM transacao WHERE idTransacao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idTransacao);
            stmt.executeUpdate();
        }
    }
}
