package com.sistemaoficina.dto;

import com.sistemaoficina.enums.Combustivel;

public class Veiculo {
    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    private Combustivel combustivel;

    public Veiculo(String placa, String modelo, int ano, String cor, Combustivel combustivel) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.combustivel = combustivel;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getPlaca() {
        return placa;
    }


    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public String getModelo() {
        return modelo;
    }


    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public int getAno() {
        return ano;
    }


    public void setAno(int ano) {
        this.ano = ano;
    }


    public String getCor() {
        return cor;
    }


    public void setCor(String cor) {
        this.cor = cor;
    }


    public Combustivel getCombustivel() {
        return combustivel;
    }


    public void setCombustivel(Combustivel combustivel) {
        this.combustivel = combustivel;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", combustivel='" + combustivel + '\'' +
                '}';
    }
}