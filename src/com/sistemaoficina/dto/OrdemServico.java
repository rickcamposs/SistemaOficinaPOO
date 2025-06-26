package com.sistemaoficina.dto;
import com.sistemaoficina.enums.StatusServico;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa uma Ordem de Serviço da oficina.
 * <p>
 * Contém informações sobre o cliente, funcionário responsável, veículo, diagnóstico, 
 * solução aplicada, produtos utilizados, valor estimado e status da ordem.
 * Permite o controle do fluxo do serviço (cancelamento, finalização e atualização de status).
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class OrdemServico{
    /** Identificador único da ordem de serviço. */
    private int id;
    /** Identificador do cliente associado à ordem. */
    private int idCliente;
    /** Identificador do funcionário responsável pela ordem. */
    private int idFuncionarioResponsavel;
    /** Identificador do veículo relacionado à ordem. */
    private int idVeiculo;
    /** Diagnóstico realizado no veículo. */
    private String diagnostico;
    /** Solução aplicada ao serviço. */
    private String solucao;
    /** Data de criação da ordem de serviço. */
    private Date dataServico;
    /** Valor estimado do serviço. */
    private double valorEstimado;
    /** Status atual da ordem de serviço. */
    private StatusServico status;
    /** Lista de produtos utilizados na execução do serviço. */
    private List<ItemProduto> produtosUtilizados = new ArrayList<>();

    /**
     * Construtor para criar uma nova ordem de serviço.
     * 
     * @param id                        Identificador da ordem.
     * @param idCliente                 Identificador do cliente.
     * @param idFuncionarioResponsavel  Identificador do funcionário responsável.
     * @param idVeiculo                 Identificador do veículo.
     * @param valorEstimado             Valor estimado do serviço.
     */
    public OrdemServico(int id, int idCliente, int idFuncionarioResponsavel, int idVeiculo, double valorEstimado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
        this.status = StatusServico.RECEBIDO;
        this.idVeiculo = idVeiculo;
        this.valorEstimado = valorEstimado;
        this.produtosUtilizados = new ArrayList<>();
    }

    /**
     * Retorna o ID do cliente.
     * 
     * @return ID do cliente vinculado à ordem.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Retorna a data de criação da ordem de serviço.
     * 
     * @return data da ordem.
     */
    public Date getDataServico() {
        return dataServico;
    }
    
    /**
     * Retorna o ID do funcionário responsável pela ordem.
     * 
     * @return ID do funcionário responsável.
     */
    public int getIdFuncionarioResponsavel() {
        return idFuncionarioResponsavel;
    }

    /**
     * Atualiza o ID do funcionário responsável pela ordem.
     * 
     * @param idFuncionarioResponsavel novo ID do funcionário.
     */
    public void setIdFuncionarioResponsavel(int idFuncionarioResponsavel) {
        this.idFuncionarioResponsavel = idFuncionarioResponsavel;
    }

    /**
     * Atualiza o ID do cliente associado à ordem.
     * 
     * @param idCliente novo ID do cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    /**
     * Retorna o status atual da ordem de serviço.
     * 
     * @return status da ordem.
     */
    public StatusServico getStatus(){
        return status;
    }
    
    /**
     * Atualiza o status da ordem de serviço.
     * 
     * @param status novo status.
     */
    public void setStatus (StatusServico status){
        this.status = status;
    }
    
    /**
     * Retorna o diagnóstico do serviço.
     * 
     * @return diagnóstico.
     */
    public String getDiagnostico(){
        return diagnostico;
    }
    
    /**
     * Atualiza o diagnóstico do serviço.
     * 
     * @param diagnostico novo diagnóstico.
     */
    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }
    
    /**
     * Retorna o ID da ordem de serviço.
     * 
     * @return ID da ordem.
     */
    public int getId(){
        return id;
    }
    
    /**
     * Verifica se a ordem está cancelada.
     * 
     * @return true se cancelada, false caso contrário.
     */
    public boolean isCancelado(){
        return this.status == StatusServico.Cancelado;
    }
    
    /**
     * Cancela a ordem de serviço, retendo 20% do valor estimado.
     */
    public void cancelar(){
        this.status = StatusServico.Cancelado;
        this.valorEstimado *= 0.80;
    }

    /**
     * Verifica se a ordem foi finalizada, entregue ou direcionada.
     * 
     * @return true se finalizada, entregue ou direcionada.
     */
    public boolean isFinalizado(){
        return this.status == StatusServico.Finalizado || this.status == StatusServico.Entregue
                || this.status == StatusServico.Direcionamento;
    }

    /**
     * Finaliza a ordem de serviço, alterando o status para finalizado.
     */
    public void finalizar(){
        this.status = StatusServico.Finalizado;
    }

    /**
     * Atualiza o ID da ordem de serviço.
     * 
     * @param id novo ID da ordem.
     */
    public void setId(int id){
        this.id = id;
    }
    
    /**
     * Retorna o valor estimado do serviço.
     * 
     * @return valor estimado.
     */
    public double getValorEstimado(){
        return valorEstimado;
    }

    /**
     * Retorna a solução aplicada na ordem de serviço.
     * 
     * @return solução.
     */
    public String getSolucao() {
        return solucao;
    }
    
    /**
     * Retorna o ID do veículo relacionado à ordem.
     * 
     * @return ID do veículo.
     */
    public int getIdVeiculo() {
        return idVeiculo;
    }
    
    /**
     * Atualiza a solução aplicada ao serviço.
     * 
     * @param solucao nova solução.
     */
    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    /**
     * Atualiza o ID do veículo.
     * 
     * @param idVeiculo novo ID do veículo.
     */
    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    /**
     * Atualiza a data do serviço.
     * 
     * @param dataServico nova data.
     */
    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }

    /**
     * Atualiza o valor estimado do serviço.
     * 
     * @param valorEstimado novo valor.
     */
    public void setValorEstimado(double valorEstimado) {
        this.valorEstimado = valorEstimado;
    }
    
    /**
     * Retorna a lista de produtos utilizados no serviço.
     * 
     * @return lista de itens de produto.
     */
    public List<ItemProduto> getProdutosUtilizados() {
        return produtosUtilizados;
    }
    
    /**
     * Atualiza a lista de produtos utilizados na ordem de serviço.
     * 
     * @param produtosUtilizados nova lista de itens de produto.
     */
    public void setProdutosUtilizados(List<ItemProduto> produtosUtilizados) {
        this.produtosUtilizados = produtosUtilizados;
    }
    
    /**
     * Retorna uma representação textual da ordem de serviço.
     * 
     * @return detalhes em string da ordem.
     */
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
