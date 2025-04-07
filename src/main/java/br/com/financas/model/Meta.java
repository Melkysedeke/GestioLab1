package br.com.financas.model;

import java.util.Date;

public class Meta {
    private int idMeta;
    private String descricao;
    private double valorMeta;
    private Date prazo;
    private int idCarteira;

    public Meta(int idMeta, String descricao, double valorMeta, Date prazo, int idCarteira) {
        this.idMeta = idMeta;
        this.descricao = descricao;
        this.valorMeta = valorMeta;
        this.prazo = prazo;
        this.idCarteira = idCarteira;
    }

    public int getIdMeta() { return idMeta; }
    public void setIdMeta(int idMeta) { this.idMeta = idMeta; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValorMeta() { return valorMeta; }
    public void setValorMeta(double valorMeta) { this.valorMeta = valorMeta; }

    public Date getPrazo() { return prazo; }
    public void setPrazo(Date prazo) { this.prazo = prazo; }

    public int getIdCarteira() { return idCarteira; }
    public void setIdCarteira(int idCarteira) { this.idCarteira = idCarteira; }
}
