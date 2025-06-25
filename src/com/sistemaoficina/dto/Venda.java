package com.sistemaoficina.dto;

import java.util.Date;

public class Venda {
    private int id;
    private int idProduto;
    private int quantidadeVendida;
    private double valorUnitario;
    private Date dataVenda;

    public Venda(int id, int idProduto, int quantidadeVendida, double valorUnitario, Date dataVenda) {
        this.id = id;
        this.idProduto = idProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorUnitario = valorUnitario;
        this.dataVenda = dataVenda;
    }

    public int getId() {
        return id;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public double getValorTotal() {
        return quantidadeVendida * valorUnitario;
    }
} 