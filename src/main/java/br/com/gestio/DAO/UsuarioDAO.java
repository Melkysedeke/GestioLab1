package br.com.gestio.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.gestio.model.Usuario;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (cpf, nome, email, senha, idUltimaCarteira) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, usuario.getCpf());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getSenha());
            stmt.setObject(5, usuario.getIdUltimaCarteira(), java.sql.Types.INTEGER);

            stmt.executeUpdate();
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

    public Usuario buscarPorEmailOuCpf(String email, Long cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ? OR cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setLong(2, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirUsuario(rs);
                }
            }
        }
        return null;
    }

    private Usuario construirUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setCpf(rs.getLong("cpf"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setIdUltimaCarteira(rs.getObject("idUltimaCarteira") != null ? rs.getInt("idUltimaCarteira") : null);
        usuario.setCriadoEm(rs.getTimestamp("criadoEm"));
        usuario.setAtualizadoEm(rs.getTimestamp("atualizadoEm"));
        return usuario;
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, idUltimaCarteira = ? WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setObject(4, usuario.getIdUltimaCarteira(), java.sql.Types.INTEGER);
            stmt.setLong(5, usuario.getCpf());

            stmt.executeUpdate();
        }
    }

    public void deletar(Long cpf) throws SQLException {
        String sql = "DELETE FROM usuario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, cpf);
            stmt.executeUpdate();
        }
    }
}
