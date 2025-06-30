package com.sistemaoficina.dto;

import com.sistemaoficina.enums.Combustivel;

/**
 * Representa um veículo cadastrado no sistema da oficina.
 * <p>
 * Armazena informações como placa, modelo, ano, cor e tipo de combustível.
 * <p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Veiculo {

    /**
     * Identificador único do veículo.
     */
    private int id;

    /**
     * Placa do veículo.
     */
    private String placa;

    /**
     * Modelo do veículo.
     */
    private String modelo;

    /**
     * Ano de fabricação do veículo.
     */
    private int ano;

    /**
     * Cor do veículo.
     */
    private String cor;

    /**
     * Tipo de combustível do veículo.
     */
    private Combustivel combustivel;

    /**
     * Construtor para criar um novo veículo com todos os dados obrigatórios.
     * 
     * @param placa      Placa do veículo.
     * @param modelo     Modelo do veículo.
     * @param ano        Ano de fabricação.
     * @param cor        Cor do veículo.
     * @param combustivel Tipo de combustível.
     */
    public Veiculo(String placa, String modelo, int ano, String cor, Combustivel combustivel) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.combustivel = combustivel;
    }

    /**
     * Retorna o identificador do veículo.
     * @return id do veículo.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador do veículo.
     * @param id novo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a placa do veículo.
     * @return placa.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Define a placa do veículo.
     * @param placa nova placa.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Retorna o modelo do veículo.
     * @return modelo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Define o modelo do veículo.
     * @param modelo novo modelo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Retorna o ano de fabricação do veículo.
     * @return ano de fabricação.
     */
    public int getAno() {
        return ano;
    }

    /**
     * Define o ano de fabricação do veículo.
     * @param ano novo ano de fabricação.
     */
    public void setAno(int ano) {
        this.ano = ano;
    }

    /**
     * Retorna a cor do veículo.
     * @return cor.
     */
    public String getCor() {
        return cor;
    }

    /**
     * Define a cor do veículo.
     * @param cor nova cor.
     */
    public void setCor(String cor) {
        this.cor = cor;
    }

    /**
     * Retorna o tipo de combustível do veículo.
     * @return tipo de combustível.
     */
    public Combustivel getCombustivel() {
        return combustivel;
    }

    /**
     * Define o tipo de combustível do veículo.
     * @param combustivel novo tipo de combustível.
     */
    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    /**
     * Retorna uma representação em string do veículo.
     * @return dados do veículo em texto.
     */
    @Override
    public String toString() { //Questão 3
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", combustivel='" + combustivel + '\'' +
                '}';
    }
}