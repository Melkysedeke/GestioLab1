package br.com.gestio.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.gestio.model.Carteira;

public class CarteiraDAO {
    private Connection conexao;

    public CarteiraDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void criarCarteira(Carteira carteira) throws SQLException {
        String sql = "INSERT INTO carteira (nome, cpfUsuario) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, carteira.getNome());
            stmt.setLong(2, carteira.getCpfUsuario());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carteira.setIdCarteira(generatedKeys.getInt(1));
                }
            }
        }
    }
    
    public void atualizarUltimaCarteira(Long cpfUsuario, int idCarteira) throws SQLException {
        String sql = "UPDATE usuario SET idUltimaCarteira = ? WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            stmt.setLong(2, cpfUsuario);
            stmt.executeUpdate();
        }
    }
    
    public Carteira buscarUltimaCarteiraPorCpf(Long cpfUsuario) throws SQLException {
        String sql = "SELECT * FROM carteira WHERE cpfUsuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, cpfUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Carteira(
                        rs.getInt("idCarteira"),
                        rs.getString("nome"),
                        rs.getLong("cpfUsuario")
                    );
                }
            }
        }
        return null;
    }
    
    public Carteira buscarPorId(int idCarteira) throws SQLException {
        String sql = "SELECT * FROM carteira WHERE idCarteira = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Carteira(
                        rs.getInt("idCarteira"),
                        rs.getString("nome"),
                        rs.getLong("cpfUsuario")
                    );
                }
            }
        }
        return null;
    }

    public List<Carteira> listarPorCpf (Long cpfUsuario) throws SQLException {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT * FROM carteira WHERE cpfUsuario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setLong(1, cpfUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Carteira c = new Carteira(
                    rs.getInt("idCarteira"),
                    rs.getString("nome"),
                    rs.getLong("cpfUsuario")
                );
                carteiras.add(c);
            }
        }
        return carteiras;
    }
    
    public void editarCarteira(int idCarteira, String novoNome) throws SQLException {
        String sql = "UPDATE carteira SET nome = ? WHERE idCarteira = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setInt(2, idCarteira);
            stmt.executeUpdate();
        }
    }

    
    public void excluirCarteira(int idCarteira) throws SQLException {
        String sql = "DELETE FROM carteira WHERE idCarteira = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            stmt.executeUpdate();
        }
    }

	public int buscarIdUltimaCarteiraPorCpf(Long cpfUsuario) throws SQLException {
	    String sql = "SELECT idUltimaCarteira FROM usuario WHERE cpf = ?";
	    try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
	        stmt.setLong(1, cpfUsuario);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("idUltimaCarteira");
	            }
	        }
	    }
	    return 0;
	}
}
