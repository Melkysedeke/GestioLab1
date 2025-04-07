package br.com.financas.model;

public class Carteira {
    private int idCarteira;
    private String nome;
    private int idUsuario;

    public Carteira() {}

    public Carteira(int idCarteira, String nome, int idUsuario) {
        this.idCarteira = idCarteira;
        this.nome = nome;
        this.idUsuario = idUsuario;
    }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}
