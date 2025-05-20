package com.sistemaoficina.dto;

public class Agendamento {
    private int id;
    private String nomeCliente;
    private String placaVeiculo;
    private String data;
    private String descricao;
    private double valorEstimado;
    private boolean cancelado;
    private boolean finalizado;

    public Agendamento(int id, String nomeCliente, String placaVeiculo, String data, String descricao, double valorEstimado) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.placaVeiculo = placaVeiculo;
        this.data = data;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.cancelado = false;
        this.finalizado = false;
    }

    public int getId() { return id; }
    public boolean isCancelado() { return cancelado; }

    public void cancelar() {
        this.cancelado = true;
        this.valorEstimado *= 0.80; // Retém 20%
    } 
    
    public String getNomeCliente() {
    return nomeCliente;
}

public String getPlacaVeiculo() {
    return placaVeiculo;
}

    public boolean isFinalizado() { return finalizado; }
    public void finalizar() { 
        this.finalizado = true; 
    }
    
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Agendamento ID: " + id +
                "\nCliente: " + nomeCliente +
                "\nVeículo: " + placaVeiculo +
                "\nData: " + data +
                "\nDescrição: " + descricao +
                "\nValor Estimado: R$ " + String.format("%.2f", valorEstimado) +
                "\nStatus: " + (cancelado ? "Cancelado (20% retido)" : "Agendado");
    }

    public double getValorEstimado() {
        return valorEstimado;
    }
    
}
