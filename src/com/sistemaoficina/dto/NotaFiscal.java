package com.sistemaoficina.dto;
import java.util.Date;

/**
 * Representa uma Nota Fiscal gerada após a conclusão de um serviço na oficina.
 * <p>
 * Armazena informações referentes ao serviço realizado, como data, valor total,
 * descrição dos serviços/produtos e vínculo com a ordem de serviço.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class NotaFiscal {

    /** Identificador único da nota fiscal. */
    private int id;

    /** Data de emissão da nota fiscal. */
    private Date data;

    /** Valor total da nota fiscal. */
    private double total = 0;

    /** Descrição do serviço ou produtos detalhados na nota fiscal. */
    private String descricao;

    /** Identificador da ordem de serviço relacionada a esta nota fiscal. */
    private int idOrdemServico;

    /**
     * Construtor para criar uma nova nota fiscal.
     *
     * @param data           Data de emissão da nota fiscal.
     * @param descricao      Descrição dos serviços ou produtos detalhados.
     * @param idOrdemServico Identificador da ordem de serviço correspondente.
     */
    public NotaFiscal(Date data, String descricao, int idOrdemServico) {
        this.data = data;
        this.descricao = descricao;
        this.idOrdemServico = idOrdemServico;
    }

    /**
     * Retorna o identificador da nota fiscal.
     *
     * @return ID da nota fiscal.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a data de emissão da nota fiscal.
     *
     * @return Data da nota fiscal.
     */
    public Date getData() {
        return data;
    }

    /**
     * Retorna a descrição da nota fiscal.
     *
     * @return Descrição dos serviços ou produtos.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o valor total da nota fiscal.
     *
     * @return Valor total.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Retorna o ID da ordem de serviço associada a esta nota fiscal.
     *
     * @return ID da ordem de serviço.
     */
    public int getIdOrdemServico() {
        return idOrdemServico;
    }

    /**
     * Define o identificador da nota fiscal.
     *
     * @param id Novo ID da nota fiscal.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define a data de emissão da nota fiscal.
     *
     * @param data Nova data.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Define a descrição detalhada da nota fiscal.
     *
     * @param descricao Nova descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Define o valor total da nota fiscal.
     *
     * @param total Novo valor total.
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Define o identificador da ordem de serviço vinculada.
     *
     * @param idOrdemServico Novo ID da ordem de serviço.
     */
    public void setIdOrdemServico(int idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }

    /**
     * Retorna uma representação em string da nota fiscal.
     *
     * @return String contendo os principais atributos da nota fiscal.
     */
    @Override
    public String toString() { // Questão 3
        return "NotaFiscal{" +
                "id='" + id + '\'' +
                ", data='" + data + '\'' +
                ", total='" + total + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idOrdemServico='" + idOrdemServico + '\'' +
                '}';
    }

}
