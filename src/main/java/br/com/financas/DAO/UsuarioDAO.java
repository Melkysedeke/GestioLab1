package br.com.financas.DAO;

import java.sql.*;
import br.com.financas.model.Usuario;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, cpf, criadoEm, atualizadoEm) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.setTimestamp(5, usuario.getCriadoEm());
            stmt.setTimestamp(6, usuario.getAtualizadoEm());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGerado = generatedKeys.getInt(1);
                        usuario.setIdUsuario(idGerado);
                    }
                }
            }
        }
    }

    public Usuario autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    public Usuario buscarPorEmailOuCpf(String email, String cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? OR cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("idUsuario"),
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("senha"),
            rs.getString("cpf"),
            rs.getTimestamp("criadoEm"),
            rs.getTimestamp("atualizadoEm")
        );
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, cpf = ?, atualizadoEm = ? WHERE idUsuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.setTimestamp(5, usuario.getAtualizadoEm());
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
