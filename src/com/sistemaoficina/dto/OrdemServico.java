package com.sistemaoficina.dto;

import com.sistemaoficina.enums.StatusServico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemServico{
    private int id;
    private int idCliente;
    private int idFuncionarioResponsavel;
    private int idVeiculo;
    private String diagnostico;
    private String solucao;
    private Date dataServico;
    private double valorEstimado;
    private StatusServico status;
    private List<ItemProduto> produtosUtilizados = new ArrayList<>();

    public OrdemServico (int id, int idCliente, int idFuncionarioResponsavel, int idVeiculo, double valorEstimado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
        this.status = StatusServico.RECEBIDO;
        this.idVeiculo = idVeiculo;
        this.valorEstimado = valorEstimado;
        this.produtosUtilizados = new ArrayList<>();
    }

    public int getIdCliente() {
        return idCliente;
    }

    public Date getDataServico() {
        return dataServico;
    }
    
    public int getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    public void setIdFuncionarioResponsavel(int idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public StatusServico getStatus(){
        return status;
    }
    
    public void setStatus (StatusServico status){
        this.status = status;
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
    
    public double getValorEstimado(){
        return valorEstimado;
    }

    public String getSolucao() {
        return solucao;
    }
    
    public int getIdVeiculo() {
        return idVeiculo;
    }
    
    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }


    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }

    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }
    
    
    public List<ItemProduto> getProdutosUtilizados() {
        return produtosUtilizados;
    }
    
    public void setProdutosUtilizados(List<ItemProduto> produtosUtilizados) {
        this.produtosUtilizados = produtosUtilizados;
    }
    
    @Override
    public String toString(){
        String info = "Ordem de Serviço ID: " + id
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
} 