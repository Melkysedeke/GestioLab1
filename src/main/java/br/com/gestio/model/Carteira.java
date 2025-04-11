package br.com.gestio.model;

public class Carteira {
    private int idCarteira;
    private String nome;
    private Long cpfUsuario;

    public Carteira() {}

    public Carteira(int idCarteira, String nome, Long cpfUsuario) {
        this.idCarteira = idCarteira;
        this.nome = nome;
        this.cpfUsuario = cpfUsuario;
    }

    public int getIdCarteira() {return idCarteira;}
    public void setIdCarteira(int idCarteira) {this.idCarteira = idCarteira;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public Long getCpfUsuario() {return cpfUsuario;}
    public void setCpfUsuario(Long cpfUsuario) {this.cpfUsuario = cpfUsuario;}
}
