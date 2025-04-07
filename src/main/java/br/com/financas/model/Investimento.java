package br.com.financas.model;

import java.util.Date;

public class Investimento {
    private int idInvestimento;
    private String tipo;
    private double valor;
    private int quantidade;
    private Date data;
    private Date dataVencimento;
    private int idCarteira;

    public Investimento() {}

    public Investimento(int idInvestimento, String tipo, double valor, int quantidade, Date data, Date dataVencimento, int idCarteira) {
        this.idInvestimento = idInvestimento;
        this.tipo = tipo;
        this.valor = valor;
        this.quantidade = quantidade;
        this.data = data;
        this.dataVencimento = dataVencimento;
        this.idCarteira = idCarteira;
    }

    public int getIdInvestimento() {
        return idInvestimento;
    }

    public void setIdInvestimento(int idInvestimento) {
        this.idInvestimento = idInvestimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }
}
