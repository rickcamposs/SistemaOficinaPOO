package com.sistemaoficina.dto;

import com.sistemaoficina.enums.CategoriaDespesa;
import java.util.Date;

/**
 * Representa uma despesa financeira da oficina.
 * <p>
 * Armazena informações detalhadas sobre uma despesa, como nome, valor,
 * data, descrição e categoria.
 * </p>
 * 
 * @author Janaina Alves COrdeiro
 * @version 1.0
 */
public class Despesa {
    /** Identificador único da despesa. */
    private int id;

    /** Nome ou título da despesa. */
    private String nome;

    /** Valor monetário da despesa. */
    private double valor;

    /** Descrição detalhada da despesa. */
    private String descricao;

    /** Data em que a despesa foi realizada. */
    private Date data;

    /** Categoria da despesa. */
    private CategoriaDespesa categoria;

    /**
     * Construtor da classe Despesa.
     *
     * @param nome      Nome da despesa.
     * @param valor     Valor da despesa.
     * @param data      Data da despesa.
     * @param descricao Descrição detalhada.
     * @param categoria Categoria da despesa.
     */
    public Despesa(String nome, double valor, Date data, String descricao, CategoriaDespesa categoria) {
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
        this.valor = valor;
        this.nome = nome;
    }

    /**
     * Retorna o ID da despesa.
     * 
     * @return Identificador da despesa.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a descrição da despesa.
     * 
     * @return Descrição.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna a data da despesa.
     * 
     * @return Data da despesa.
     */
    public Date getData() {
        return data;
    }

    /**
     * Retorna a categoria da despesa.
     * 
     * @return CategoriaDespesa.
     */
    public CategoriaDespesa getCategoria() {
        return categoria;
    }

    /**
     * Retorna o valor da despesa.
     * 
     * @return Valor.
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna o nome da despesa.
     * 
     * @return Nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o identificador da despesa.
     * 
     * @param id Novo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define a descrição da despesa.
     * 
     * @param descricao Nova descrição.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Define a data da despesa.
     * 
     * @param data Nova data.
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Define a categoria da despesa.
     * 
     * @param categoria Nova categoria.
     */
    public void setCategoria(CategoriaDespesa categoria) {
        this.categoria = categoria;
    }

    /**
     * Define o valor da despesa.
     * 
     * @param valor Novo valor.
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * Define o nome da despesa.
     * 
     * @param nome Novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna uma representação textual da despesa para fins de depuração.
     * 
     * @return String detalhando a despesa.
     */
    @Override
    public String toString(){
        return "Financeiro{ " + nome + " |" + descricao + " |Valor: " + valor + " | Data: " + data + " | Tipo: " + categoria + "}";
    }
}
