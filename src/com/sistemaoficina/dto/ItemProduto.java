package com.sistemaoficina.dto;

public class ItemProduto {
    private int idProduto;
    private int quantidade;

    public ItemProduto(int idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }
    
    @Override
    public String toString() {
        return "ItemProduto{" +
                "idProduto='" + idProduto + '\'' +
                ", quantidade='" + quantidade + '\'' +
                '}';
    }
    
}