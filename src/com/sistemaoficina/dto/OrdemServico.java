package com.sistemaoficina.dto;
import com.sistemaoficina.enums.StatusServico;

public class OrdemServico{
    private int id;
    private String nomeCliente;
    private String placaVeiculo;
    private String diagnostico;
    private String solucao;
    private String dataServico;
    private double valorEstimado;
    private StatusServico status;
    private String funcionarioResponsavel;
    
    public OrdemServico (int id, String nomeCliente, String placaVeiculo, String diagnostico, String solucao, String dataServico, 
            double valorEstimado, String funcionarioResponsavel) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.placaVeiculo = placaVeiculo;
        this.solucao = solucao;
        this.dataServico = dataServico;
        this.valorEstimado = valorEstimado;
        this.status = StatusServico.RECEBIDO;
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
    
    public StatusServico getStatus(){
        return status;
    }
    
    public void setStatus (StatusServico status){
        this.status = status;
    }
    public String getfuncionarioResponsavel(){
        return funcionarioResponsavel;
    }
    
    public void setFuncionarioResponsavel(String funcionarioRepsonsavel){
        this.funcionarioResponsavel = funcionarioResponsavel;
    }
    
    public String getDiagnostico(){
        return diagnostico;
    }
    
    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }
    
    public int getId(){
        return id;
    }
    
    public boolean isCancelado(){
        return this.status == StatusServico.Cancelado;
    }
    
    public void cancelar(){
        this.status = StatusServico.Cancelado;
        this.valorEstimado *= 0.80;
    }
    public String getNomeCliente(){
        return nomeCliente;
    }
    public String getPlacaVeiculo(){
        return placaVeiculo;
    }
    public boolean isFinalizado(){
        return this.status == StatusServico.Finalizado || this.status == StatusServico.Entregue
                || this.status == StatusServico.Direcionamento;
    }
    public void finalizar(){
        this.status = StatusServico.Finalizado;
    }
    public void setId(int id){
        this.id = id;
    }
    
    @Override
    public String toString(){
        String info = "Ordem de Serviço ID: " + id
                +"\nCliente: " + nomeCliente
                +"\nVeículo: " + placaVeiculo
                +"\nData: " + dataServico
                +"\nDiagnostico: " + diagnostico
                +"\nSolução: " + solucao
                +"\nValor Estimado: " + valorEstimado
                +"\nStatus Ordem de Serviço: " + status
                +"\nStatus Serviço: " + status.name();
        
        if (status == StatusServico.Direcionamento && diagnostico != null){
            info += "\n Diagnostico: " + diagnostico;
        }
        
        return info;
    }
    
    public double getValorEstimado(){
        return valorEstimado;
    }
    
} 