package br.com.financas.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.financas.model.Meta;

public class MetaDAO {
    private Connection conexao;

    public MetaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Meta meta) throws SQLException {
        String sql = "INSERT INTO meta (descricao, valorMeta, prazo, idCarteira) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, meta.getDescricao());
            stmt.setDouble(2, meta.getValorMeta());
            stmt.setDate(3, new java.sql.Date(meta.getPrazo().getTime()));
            stmt.setInt(4, meta.getIdCarteira());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    meta.setIdMeta(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Meta> listarPorCarteira(int idCarteira) throws SQLException {
        List<Meta> metas = new ArrayList<>();
        String sql = "SELECT * FROM meta WHERE idCarteira = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    metas.add(new Meta(
                        rs.getInt("idMeta"),
                        rs.getString("descricao"),
                        rs.getDouble("valorMeta"),
                        rs.getDate("prazo"),
                        rs.getInt("idCarteira")
                    ));
                }
            }
        }
        return metas;
    }


    public void atualizar(Meta meta) throws SQLException {
        String sql = "UPDATE meta SET descricao = ?, valorMeta = ?, prazo = ? WHERE idMeta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, meta.getDescricao());
            stmt.setDouble(2, meta.getValorMeta());
            stmt.setDate(3, new java.sql.Date(meta.getPrazo().getTime()));
            stmt.setInt(4, meta.getIdMeta());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM meta WHERE idMeta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
