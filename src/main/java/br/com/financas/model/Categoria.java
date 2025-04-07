package br.com.financas.model;

public class Categoria {
    private int idCategoria;
    private String nome;
    private boolean personalizada;
    private Integer idUsuario; // <- Corrigido aqui

    public Categoria() {}

    public Categoria(int idCategoria, String nome, boolean personalizada, Integer idUsuario) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.personalizada = personalizada;
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public boolean isPersonalizada() { return personalizada; }
    public void setPersonalizada(boolean personalizada) { this.personalizada = personalizada; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
}
