package br.com.financas.model;

import java.util.Date;

public class Transacao {
    private int idTransacao;
    private String tipo;
    private String descricao;
    private double valor;
    private Date data;
    private int idCarteira;
    private int idCategoria;

    public Transacao() {}

    public Transacao(int idTransacao, String tipo, String descricao, double valor, Date data, int idCarteira, int idCategoria) {
        this.idTransacao = idTransacao;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.idCarteira = idCarteira;
        this.idCategoria = idCategoria;
    }

    public int getIdTransacao() { return idTransacao; }
    public void setIdTransacao(int idTransacao) { this.idTransacao = idTransacao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
}
