package com.sistemaoficina.dto;

/**
 * Representa um produto disponível no estoque da oficina.
 * 
 * <p>
 * Armazena informações como nome, quantidade em estoque, valor pago na compra e valor de venda ao cliente.
 * Utilizada para controle de inventário, vendas e utilização em serviços.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Produto {
    /** Identificador único do produto. */
    private int id;
    /** Quantidade disponível em estoque. */
    private int quantidade;
    /** Nome do produto. */
    private String nome;
    /** Valor pago pelo produto na compra (custo). */
    private Double valorPago;
    /** Valor pelo qual o produto será vendido (preço de venda). */
    private Double valorVendido;

    /**
     * Construtor para criar um novo produto.
     * 
     * @param quantidade   Quantidade inicial em estoque.
     * @param nome         Nome do produto.
     * @param valorPago    Valor de compra (custo).
     * @param valorVendido Valor de venda ao cliente.
     */
    public Produto(int quantidade, String nome, Double valorPago, Double valorVendido) {
        this.quantidade = quantidade;
        this.nome = nome;
        this.valorPago = valorPago;
        this.valorVendido = valorVendido;
    }

    /**
     * Retorna o identificador único do produto.
     * 
     * @return id do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do produto.
     * 
     * @param id id do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a quantidade disponível em estoque.
     * 
     * @return quantidade em estoque.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Atualiza a quantidade disponível em estoque.
     * 
     * @param quantidade nova quantidade.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Retorna o nome do produto.
     * 
     * @return nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     * 
     * @param nome nome do produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o valor pago pelo produto na compra.
     * 
     * @return valor de compra.
     */
    public Double getValorPago() {
        return valorPago;
    }

    /**
     * Define o valor pago pelo produto na compra.
     * 
     * @param valorPago valor de compra.
     */
    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    /**
     * Retorna o valor de venda do produto.
     * 
     * @return valor de venda.
     */
    public Double getValorVendido() {
        return valorVendido;
    }

    /**
     * Define o valor de venda do produto.
     * 
     * @param valorVendido valor de venda.
     */
    public void setValorVendido(Double valorVendido) {
        this.valorVendido = valorVendido;
    }

    /**
     * Retorna uma representação em texto do produto.
     * 
     * @return String com os principais dados do produto.
     */
    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", id='" + id + '\'' +
                ", quantidade='" + quantidade + '\'' +
                ", valorPago='" + valorPago + '\'' +
                ", valorVendido='" + valorVendido + '\'' +
                '}';
    }
}
