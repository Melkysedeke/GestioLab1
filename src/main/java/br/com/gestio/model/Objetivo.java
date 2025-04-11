package br.com.gestio.model;

import java.util.Date;

public class Objetivo {
    private int idObjetivo;
    private String nome;
    private String descricao;
    private double valorObjetivo;
    private double valorAtual;
    private Date prazo;
    private Date dataCriacao;
    private String status;
    private int idCarteira;

    public Objetivo() {}

    public Objetivo(int idObjetivo, String nome, String descricao, double valorObjetivo, double valorAtual, Date prazo, Date dataCriacao, String status, int idCarteira) {
        this.idObjetivo = idObjetivo;
        this.nome = nome;
        this.descricao = descricao;
        this.valorObjetivo = valorObjetivo;
        this.valorAtual = valorAtual;
        this.prazo = prazo;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.idCarteira = idCarteira;
    }


    public int getIdObjetivo() { return idObjetivo; }
    public void setIdObjetivo(int idObjetivo) { this.idObjetivo = idObjetivo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValorObjetivo() { return valorObjetivo; }
    public void setValorObjetivo(double valorObjetivo) { this.valorObjetivo = valorObjetivo; }

    public double getValorAtual() { return valorAtual; }
    public void setValorAtual(double valorAtual) { this.valorAtual = valorAtual; }

    public Date getPrazo() { return prazo; }
    public void setPrazo(Date prazo) { this.prazo = prazo; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }
}
