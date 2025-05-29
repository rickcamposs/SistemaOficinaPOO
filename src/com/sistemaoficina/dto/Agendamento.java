package com.sistemaoficina.dto;

import com.sistemaoficina.enums.StatusServico;

public class Agendamento {
    private int id;
    private String nomeCliente;
    private String placaVeiculo;
    private String data;
    private String descricao;
    private double valorEstimado;
    private StatusServico status;
    private String direcionamento;

    public Agendamento(int id, String nomeCliente, String placaVeiculo, String data, String descricao,
            double valorEstimado) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.placaVeiculo = placaVeiculo;
        this.data = data;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.status = StatusServico.RECEBIDO;

    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }
    
    public String getDirecionamento(){
        return direcionamento;
    }
    
    public void setDirecionamento(String direcionamento){
        this.direcionamento = direcionamento;
    }

    public int getId() {
        return id;
    }

    public boolean isCancelado() {
        return this.status == StatusServico.Cancelado;
    }

    public void cancelar() {
        this.status = StatusServico.Cancelado;
        this.valorEstimado *= 0.80; // Retém 20%
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public boolean isFinalizado() {
        return this.status == StatusServico.Finalizado || this.status == StatusServico.Entregue
                || this.status == StatusServico.Direcionamento;
    }

    public void finalizar() {
        this.status = StatusServico.Finalizado;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String info = "Agendamento ID: " + id
                + "\nCliente: " + nomeCliente
                + "\nVeículo: " + placaVeiculo
                + "\nData: " + data
                + "\nDescrição: " + descricao
                + "\nValor Estimado: R$ " + String.format("%.2f", valorEstimado)
                + "\nStatus Agendamento: " + status
                + "\nStatus Serviço: " + status.name();

        if (status == StatusServico.Direcionamento && direcionamento != null) {
            info += "\nDirecionamento: " + direcionamento;
        }

        return info;
    }

    public double getValorEstimado() {
        return valorEstimado;
    }

}
