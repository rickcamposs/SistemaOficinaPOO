package com.sistemaoficina.dto;

import com.sistemaoficina.enums.StatusServico;

public class Agendamento {
    private int id;
    private int idCliente;
    private String data;
    private String descricao;
    private double valorEstimado;
    private StatusServico status;
    private String direcionamento;

    public Agendamento(int id, int idCliente, String data, String descricao,
            double valorEstimado) {
        this.id = id;
        this.data = data;
        this.idCliente = idCliente;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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


    public double getValorEstimado() {
        return valorEstimado;
    }

    @Override
    public String toString() {
        String info = "Agendamento ID: " + id
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
}
