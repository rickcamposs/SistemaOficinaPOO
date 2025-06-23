package com.sistemaoficina.dto;

import java.time.LocalDateTime;

public class NotaFiscal {
    
    private int id;
    private String data;
    private double total = 0;
    private String descricao;

    public NotaFiscal(String data,String descricao) {
        this.data = data;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
}
