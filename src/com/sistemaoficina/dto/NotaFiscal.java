package com.sistemaoficina.dto;

import java.util.Date;

public class NotaFiscal {
    
    private int id;
    private Date data;
    private double total = 0;
    private String descricao;
    private int idOrdemServico;

    public NotaFiscal(Date data,String descricao, int idOrdemServico) {
        this.data = data;
        this.descricao = descricao;
        this.idOrdemServico = idOrdemServico;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTotal() {
        return total;
    }

    public int getIdOrdemServico() {
        return idOrdemServico;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setIdOrdemServico(int idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }
    
    @Override
    public String toString() {
        return "NotaFiscal{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                ", total='" + total + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idOrdemServico='" + idOrdemServico + '\'' +
                '}';
    }
    
}
