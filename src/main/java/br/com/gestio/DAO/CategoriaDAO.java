package br.com.gestio.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.gestio.model.Categoria;

public class CategoriaDAO {
    private Connection conexao;

    public CategoriaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY nome";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria c = new Categoria();
                    c.setIdCategoria(rs.getInt("idCategoria"));
                    c.setNome(rs.getString("nome"));
                    categorias.add(c);
                }
            }
        }
        return categorias;
    }


}
