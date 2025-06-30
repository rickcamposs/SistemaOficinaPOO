package com.sistemaoficina.dto;
import java.util.Date;

/**
 * Representa uma venda de produto realizada na oficina.
 * <p>
 * Armazena informações sobre o produto vendido, quantidade, valores e data da venda.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Venda {
    /** Identificador único da venda. */
    private int id;

    /** Identificador do produto vendido. */
    private int idProduto;

    /** Quantidade do produto vendida. */
    private int quantidadeVendida;

    /** Valor unitário do produto no momento da venda. */
    private double valorUnitario;

    /** Data em que a venda foi realizada. */
    private Date dataVenda;

    /**
     * Construtor para criar uma nova venda.
     * 
     * @param id                Identificador único da venda.
     * @param idProduto         ID do produto vendido.
     * @param quantidadeVendida Quantidade vendida do produto.
     * @param valorUnitario     Valor unitário do produto.
     * @param dataVenda         Data da venda.
     */
    public Venda(int id, int idProduto, int quantidadeVendida, double valorUnitario, Date dataVenda) {
        this.id = id;
        this.idProduto = idProduto;
        this.quantidadeVendida = quantidadeVendida;
        this.valorUnitario = valorUnitario;
        this.dataVenda = dataVenda;
    }

    /**
     * Retorna o identificador da venda.
     * 
     * @return ID da venda.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o identificador do produto vendido.
     * 
     * @return ID do produto.
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Retorna a quantidade de produtos vendida.
     * 
     * @return Quantidade vendida.
     */
    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    /**
     * Retorna o valor unitário do produto vendido.
     * 
     * @return Valor unitário.
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Retorna a data em que a venda foi realizada.
     * 
     * @return Data da venda.
     */
    public Date getDataVenda() {
        return dataVenda;
    }

    /**
     * Calcula e retorna o valor total da venda.
     * 
     * @return Valor total da venda (quantidade x valor unitário).
     */
    public double getValorTotal() {
        return quantidadeVendida * valorUnitario;
    }
    
    @Override
    public String toString(){ //Questão 3
        return "Venda{" +
                "id='" + id + '\'' +
                ", idProduto='" + idProduto + '\'' +
                ", quantidadeVendida='" + quantidadeVendida + '\'' +
                ", valorUnitario='" + valorUnitario + '\'' +
                ", dataVenda='" + dataVenda + '\'' +
                '}';
    }
}
