package br.com.gestio.model;

import java.sql.Timestamp;

public class Usuario {
    private long cpf;
    private String nome;
    private String email;
    private String senha;
    private Integer idUltimaCarteira;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;

    public Usuario() {}

    public Usuario(long cpf, String nome, String email, String senha, Integer idUltimaCarteira) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idUltimaCarteira = idUltimaCarteira;
    }

    public long getCpf() { return cpf; }
    public void setCpf(long cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Integer getIdUltimaCarteira() { return idUltimaCarteira; }
    public void setIdUltimaCarteira(Integer idUltimaCarteira) { this.idUltimaCarteira = idUltimaCarteira; }

    public Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Timestamp criadoEm) { this.criadoEm = criadoEm; }

    public Timestamp getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(Timestamp atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}