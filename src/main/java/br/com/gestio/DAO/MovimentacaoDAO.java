package br.com.gestio.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.gestio.model.Movimentacao;

public class MovimentacaoDAO {
    private final Connection conexao;

    public MovimentacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Movimentacao mov) throws SQLException {
        String sql = "INSERT INTO Movimentacao (tipo, descricao, valor, data, formaPagamento, idCarteira, idCategoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, mov.getTipo());
            stmt.setString(2, mov.getDescricao());
            stmt.setDouble(3, mov.getValor());
            stmt.setDate(4, new java.sql.Date(mov.getData().getTime()));
            stmt.setString(5, mov.getFormaPagamento());
            stmt.setInt(6, mov.getIdCarteira());
            stmt.setInt(7, mov.getIdCategoria());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    mov.setIdMovimentacao(rs.getInt(1));
                }
            }
        }
    }


    public List<Movimentacao> listarPorCarteira(int idCarteira) throws SQLException {
        List<Movimentacao> lista = new ArrayList<>();

        String sql = """
            SELECT m.*, c.nome AS nomeCategoria
            FROM Movimentacao m
            LEFT JOIN Categoria c ON m.idCategoria = c.idCategoria
            WHERE m.idCarteira = ?
            ORDER BY m.data DESC
        """;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movimentacao mov = new Movimentacao();
                    mov.setIdMovimentacao(rs.getInt("idMovimentacao"));
                    mov.setTipo(rs.getString("tipo"));
                    mov.setDescricao(rs.getString("descricao"));
                    mov.setValor(rs.getDouble("valor"));
                    mov.setData(rs.getDate("data"));
                    mov.setFormaPagamento(rs.getString("formaPagamento"));
                    mov.setIdCarteira(rs.getInt("idCarteira"));

                    int idCategoria = rs.getInt("idCategoria");
                    if (!rs.wasNull()) {
                        mov.setIdCategoria(idCategoria);
                    }
                    mov.setNomeCategoria(rs.getString("nomeCategoria"));
                    mov.setCriadoEm(rs.getTimestamp("criadoEm"));
                    mov.setAtualizadoEm(rs.getTimestamp("atualizadoEm"));
                    lista.add(mov);
                }
            }
        }

        return lista;
    }

    public void atualizar(Movimentacao mov) throws SQLException {
        String sql = "UPDATE Movimentacao SET tipo = ?, descricao = ?, valor = ?, data = ?, formaPagamento = ?, idCategoria = ? WHERE idMovimentacao = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, mov.getTipo());
            stmt.setString(2, mov.getDescricao());
            stmt.setDouble(3, mov.getValor());
            stmt.setDate(4, new java.sql.Date(mov.getData().getTime()));
            stmt.setString(5, mov.getFormaPagamento());
            stmt.setInt(6, mov.getIdCategoria());
            stmt.setInt(7, mov.getIdMovimentacao());

            stmt.executeUpdate();
        }
    }
    
	public int somarPorTipo(int idCarteira, String tipo) {
	    String sql = "SELECT SUM(valor) AS total FROM Movimentacao WHERE idCarteira = ? AND tipo = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setInt(1, idCarteira);
	        stmt.setString(2, tipo);
	
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            BigDecimal total = rs.getBigDecimal("total");
	            return total != null ? total.intValue() : 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Movimentacao WHERE idMovimentacao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
