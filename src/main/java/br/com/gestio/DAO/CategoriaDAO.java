package br.com.gestio.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.gestio.model.Categoria;

public class CategoriaDAO {
    private Connection conexao;

    public CategoriaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (nome, personalizada, cpfUsuario) VALUES (?, TRUE, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNome());

            if (categoria.getCpfUsuario() != null) {
                stmt.setLong(2, categoria.getCpfUsuario());
            } else {
                stmt.setNull(2, Types.BIGINT);
            }

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    categoria.setIdCategoria(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Categoria> listarPorCpf(Long cpfUsuario) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE cpfUsuario IS NULL OR cpfUsuario = ? ORDER BY nome";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setIdCategoria(rs.getInt("idCategoria"));
                    c.setNome(rs.getString("nome"));
                    c.setPersonalizada(rs.getBoolean("personalizada"));
                    Object cpf = rs.getObject("cpfUsuario");
                    c.setCpfUsuario(cpf != null ? ((Number) cpf).longValue() : null);
                    categorias.add(c);
                }
            }
        }
        return categorias;
    }

    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET nome = ? WHERE idCategoria = ? AND personalizada = TRUE";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getIdCategoria());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE idCategoria = ? AND personalizada = TRUE";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
