package br.com.gestio.model;

import java.util.Date;

public class Divida {
    private int idDivida;
    private String tipo;
    private String descricao;
    private double valor;
    private Date dataCriacao;
    private Date dataVencimento;
    private Date dataQuitacao;
    private String status;
    private int idCarteira;

    public Divida() {}

    public Divida(int idDivida, String tipo, String descricao, double valor, Date dataCriacao, Date dataVencimento, Date dataQuitacao, String status, int idCarteira) {
        this.idDivida = idDivida;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.dataCriacao = dataCriacao;
        this.dataVencimento = dataVencimento;
        this.dataQuitacao = dataQuitacao;
        this.status = status;
        this.idCarteira = idCarteira;
    }

    public int getIdDivida() {
        return idDivida;
    }

    public void setIdDivida(int idDivida) {
        this.idDivida = idDivida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataQuitacao() {
        return dataQuitacao;
    }

    public void setDataQuitacao(Date dataQuitacao) {
        this.dataQuitacao = dataQuitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }
}
