package com.sistemaoficina.dto;

public class Veiculo {
    private String placa;
    private String modelo;
    private int ano;
    private String cor;
    private String combustivel;

    public Veiculo(String placa, String modelo, int ano, String cor, String combustivel) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.combustivel = combustivel;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public String getCombustivel() {
        return combustivel;
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