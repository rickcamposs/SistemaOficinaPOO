package com.sistemaoficina.dto;
import com.sistemaoficina.enums.CategoriaDespesa;

public class Despesa {
    private int id;
    private String nome;
    private double valor;
    private String descricao;
    private String data;
    private CategoriaDespesa categoria;
    

    public Despesa(String nome, double valor, String data, String descricao, CategoriaDespesa categoria) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
        this.valor = valor;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public CategoriaDespesa getCategoria() {
        return categoria;
    }

    public double getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCategoria(CategoriaDespesa tipo) {
        this.categoria = categoria;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString(){
        return "Financeiro{ " + nome + " |"+ descricao + " " + "|Valor: " + valor + " | Data: " + " | Tipo: " + categoria + "}";
    }
    
}
