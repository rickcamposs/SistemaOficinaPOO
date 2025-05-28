package com.sistemaoficina.dto;
import com.SistemaOficina.enums.StatusServico;

public class Agendamento {
    private int id;
    private String nomeCliente;
    private String placaVeiculo;
    private String data;
    private String descricao;
    private double valorEstimado;
    private boolean cancelado;
    private boolean finalizado;
    private StatusServico status;
    /*private final StatusServico status*/
    

    public Agendamento(int id, String nomeCliente, String placaVeiculo, String data, String descricao,
            double valorEstimado) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.placaVeiculo = placaVeiculo;
        this.data = data;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.status = StatusServico.RECEBIDO;
        this.cancelado = false;
        this.finalizado = false;
    
    }

    public StatusServico getStatus(){
        return status;
}
    public void setStatus(StatusServico status){
        this.status = status;
}
    public int getId() {
        return id;
    }

    public boolean isCancelado() {
        return cancelado;
    }

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

    public boolean isFinalizado() {
        return finalizado;
    }

    public void finalizar() {
        this.finalizado = true;
    }

    public void setId(int id) {
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
                "\nStatus Agendamento: " + (cancelado ? "Cancelado (20% retido)" : "Agendado")+
                "\nStatus Serviço: " + status.name();
    }

    public double getValorEstimado() {
        return valorEstimado;
    }
    
    

}
