package com.sistemaoficina.dto;

public class Produto {
    private int id;
    private int quantidade;
    private String nome;
    private Double valorPago;
    private Double valorVendido;

    public Produto(int quantidade, String nome, Double valorPago, Double valorVendido){
        this.quantidade = quantidade;
        this.nome = nome;
        this.valorPago = valorPago;
        this.valorVendido = valorVendido;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getValorPago() {
        return valorPago;
    }
    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }
    public Double getValorVendido() {
        return valorVendido;
    }
    public void setValorVendido(Double valorVendido) {
        this.valorVendido = valorVendido;
    } 
    
}
