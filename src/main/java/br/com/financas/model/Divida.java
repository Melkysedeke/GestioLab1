package br.com.financas.model;

import java.util.Date;

public class Divida {
    private int idDivida;
    private String descricao;
    private double valor;
    private Date data;
    private Date dataQuitacao;
    private int idCarteira;

    public Divida() {}

    public Divida(int idDivida, String descricao, double valor, Date data, Date dataQuitacao, int idCarteira) {
        this.idDivida = idDivida;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.dataQuitacao = dataQuitacao;
        this.idCarteira = idCarteira;
    }

    public int getIdDivida() { return idDivida; }
    public void setIdDivida(int idDivida) { this.idDivida = idDivida; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Date getDataQuitacao() { return dataQuitacao; }
    public void setDataQuitacao(Date dataQuitacao) { this.dataQuitacao = dataQuitacao; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }
}
