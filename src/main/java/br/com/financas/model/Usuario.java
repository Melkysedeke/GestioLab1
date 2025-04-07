package br.com.financas.model;

import java.sql.Timestamp;

public class Usuario {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;

    public Usuario() {}

    public Usuario(int idUsuario, String nome, String email, String senha, String cpf, Timestamp criadoEm, Timestamp atualizadoEm) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
    }
    
    public Usuario(String nome, String email, String senha, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.criadoEm = new Timestamp(System.currentTimeMillis());
        this.atualizadoEm = new Timestamp(System.currentTimeMillis());
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Timestamp criadoEm) { this.criadoEm = criadoEm; }

    public Timestamp getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(Timestamp atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}