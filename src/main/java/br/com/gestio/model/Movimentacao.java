package br.com.gestio.model;

import java.sql.Timestamp;
import java.util.Date;

public class Movimentacao {
    private int idMovimentacao;
    private String tipo;
    private String descricao;
    private double valor;
    private Date data;
    private String formaPagamento;
    private int idCarteira;
    private int idCategoria;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;

    public Movimentacao() {}

    public Movimentacao(int idMovimentacao, String tipo, String descricao, double valor,
            Date data, String formaPagamento, int idCarteira, int idCategoria) {
			this.idMovimentacao = idMovimentacao;
			this.tipo = tipo;
			this.descricao = descricao;
			this.valor = valor;
			this.data = data;
			this.formaPagamento = formaPagamento;
			this.idCarteira = idCarteira;
			this.idCategoria = idCategoria;
		}


    public int getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(int idMovimentacao) { this.idMovimentacao = idMovimentacao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Timestamp criadoEm) { this.criadoEm = criadoEm; }

    public Timestamp getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(Timestamp atualizadoEm) { this.atualizadoEm = atualizadoEm; }
    
    private String nomeCategoria;

    public String getNomeCategoria() {return nomeCategoria;}
    public void setNomeCategoria(String nomeCategoria) {this.nomeCategoria = nomeCategoria;}

}
