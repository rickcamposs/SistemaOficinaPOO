package com.sistemaoficina.dto;

import java.util.Date;

/**
 * Representa o registro de ponto (entrada e saída) de um funcionário da oficina.
 * <p>
 * Cada registro armazena a data/hora de entrada, data/hora de saída, o ID do funcionário 
 * e um identificador único do ponto.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Ponto {

    /** Identificador único do ponto. */
    private int id;

    /** Data e hora de entrada do funcionário. */
    private Date dataEntrada;

    /** Data e hora de saída do funcionário (pode ser nulo se o ponto estiver aberto). */
    private Date dataSaida;

    /** ID do funcionário ao qual o ponto pertence. */
    private int idFuncionario;

    /**
     * Construtor para criar um novo registro de ponto.
     * 
     * @param id            Identificador do ponto.
     * @param dataEntrada   Data/hora de entrada do funcionário.
     * @param dataSaida     Data/hora de saída do funcionário (pode ser nulo).
     * @param idFuncionario ID do funcionário.
     */
    public Ponto(int id, Date dataEntrada, Date dataSaida, int idFuncionario) {
        this.id = id;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.idFuncionario = idFuncionario;
    }

    /**
     * Retorna o identificador do ponto.
     * 
     * @return ID do ponto.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do ponto.
     * 
     * @param id Novo ID do ponto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data/hora de entrada do funcionário.
     * 
     * @return Data/hora de entrada.
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * Define a data/hora de entrada do funcionário.
     * 
     * @param dataEntrada Nova data/hora de entrada.
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * Retorna a data/hora de saída do funcionário.
     * 
     * @return Data/hora de saída, ou null se o ponto não foi fechado.
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * Define a data/hora de saída do funcionário.
     * 
     * @param dataSaida Nova data/hora de saída.
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * Retorna o ID do funcionário deste ponto.
     * 
     * @return ID do funcionário.
     */
    public int getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * Define o ID do funcionário deste ponto.
     * 
     * @param idFuncionario Novo ID do funcionário.
     */
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Retorna uma representação textual do registro de ponto.
     * 
     * @return String com os dados do ponto.
     */
    @Override
    public String toString() {
        return "Ponto{" +
                "id='" + id + '\'' +
                ", Dataentrada='" + dataEntrada + '\'' +
                ", dataSaida='" + dataSaida + '\'' +
                ", idFuncionario='" + idFuncionario + '\'' +
                '}';
    }
}
