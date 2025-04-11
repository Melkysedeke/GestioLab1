package br.com.gestio.model;

public class Categoria {
    private int idCategoria;
    private String nome;
    private boolean personalizada;
    private Long cpfUsuario;

    public Categoria() {}

    public Categoria(int idCategoria, String nome, boolean personalizada, Long cpfUsuario) {
        this.idCategoria = idCategoria;
        this.nome = nome;
        this.personalizada = personalizada;
        this.cpfUsuario = cpfUsuario;
    }

    public int getIdCategoria() {return idCategoria;}
    public void setIdCategoria(int idCategoria) {this.idCategoria = idCategoria;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public boolean isPersonalizada() {return personalizada;}
    public void setPersonalizada(boolean personalizada) {this.personalizada = personalizada;}

    public Long getCpfUsuario() {return cpfUsuario;}
    public void setCpfUsuario(Long cpfUsuario) {this.cpfUsuario = cpfUsuario;}
}
