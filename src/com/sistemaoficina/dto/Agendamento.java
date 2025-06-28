package com.sistemaoficina.dto;
import com.sistemaoficina.enums.StatusServico;

/**
 * Representa um agendamento de serviço na oficina.
 * 
 * <p>
 * Contém informações sobre o cliente, data, descrição do serviço, valor estimado, 
 * status do serviço e direcionamento (caso necessário).
 * Permite o controle de fluxo do agendamento, como cancelamento e finalização.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Agendamento {
    /** Identificador único do agendamento. */
    private int id;
    /** Identificador do cliente relacionado ao agendamento. */
    private int idCliente;
    /** Data do agendamento (formato dd/MM/aaaa). */
    private String data;
    /** Descrição do problema relatado ou serviço solicitado. */
    private String descricao;
    /** Valor estimado do serviço a ser realizado. */
    private double valorEstimado;
    /** Status atual do agendamento (ex: Recebido, Cancelado, Finalizado). */
    private StatusServico status;
    /** Direcionamento do serviço (ex: setor especializado), caso haja. */
    private String direcionamento;

    /**
     * Construtor para criar um novo agendamento.
     * 
     * @param id            Identificador do agendamento.
     * @param idCliente     Identificador do cliente.
     * @param data          Data do agendamento.
     * @param descricao     Descrição do problema/serviço.
     * @param valorEstimado Valor estimado do serviço.
     */
    public Agendamento(int id, int idCliente, String data, String descricao,
            double valorEstimado) {
        this.id = id;
        this.data = data;
        this.idCliente = idCliente;
        this.descricao = descricao;
        this.valorEstimado = valorEstimado;
        this.status = StatusServico.RECEBIDO;
    }

    /**
     * Retorna o status atual do agendamento.
     * 
     * @return status do serviço.
     */
    public StatusServico getStatus() {
        return status;
    }

    /**
     * Atualiza o status do agendamento.
     * 
     * @param status Novo status a ser definido.
     */
    public void setStatus(StatusServico status) {
        this.status = status;
    }
    
    /**
     * Retorna o direcionamento do serviço, caso exista.
     * 
     * @return direcionamento do agendamento.
     */
    public String getDirecionamento(){
        return direcionamento;
    }

    /**
     * Retorna o ID do cliente relacionado ao agendamento.
     * 
     * @return id do cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Atualiza o ID do cliente.
     * 
     * @param idCliente Novo id do cliente.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    /**Retorna a Data do Agendamento do Cliente
     *
     *@return data do agendamento
     */
    public String getData() {
        return data;
    }
    
    /**Atualiza a data do agendamento
     *
     * @param data nova data.
     */
    public void setData(String data) {
        this.data = data;
    }
    
    
    
    /**
     * Define o direcionamento do serviço.
     * 
     * @param direcionamento Nova informação de direcionamento.
     */
    public void setDirecionamento(String direcionamento){
        this.direcionamento = direcionamento;
    }

    /**
     * Retorna o ID do agendamento.
     * 
     * @return id do agendamento.
     */
    public int getId() {
        return id;
    }

    /**
     * Verifica se o agendamento está cancelado.
     * 
     * @return true se estiver cancelado, false caso contrário.
     */
    public boolean isCancelado() {
        return this.status == StatusServico.Cancelado;
    }

    /**
     * Cancela o agendamento e retém 20% do valor estimado.
     */
    public void cancelar() {
        this.status = StatusServico.Cancelado;
        this.valorEstimado *= 0.80; // Retém 20%
    }

    /**
     * Verifica se o agendamento já está finalizado ou entregue.
     * 
     * @return true se finalizado, entregue ou direcionado.
     */
    public boolean isFinalizado() {
        return this.status == StatusServico.Finalizado || this.status == StatusServico.Entregue
                || this.status == StatusServico.Direcionamento;
    }

    /**
     * Finaliza o agendamento, alterando o status.
     */
    public void finalizar() {
        this.status = StatusServico.Finalizado;
    }

    /**
     * Atualiza o ID do agendamento.
     * 
     * @param id Novo id do agendamento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o valor estimado do serviço.
     * 
     * @return valor estimado.
     */
    public double getValorEstimado() {
        return valorEstimado;
    }

    /**
     * Retorna uma representação em texto do agendamento.
     * 
     * @return String detalhada com as informações do agendamento.
     */
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
