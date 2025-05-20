  package com.mycompany.sistemaoficina;


public class Usuario {
    private String nome;
    private String tipo; // "admin" ou "proprietario"

    public Usuario(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isProprietario() {
        return "proprietario".equalsIgnoreCase(tipo);
    }
}