package com.sistemaoficina.dto;

import java.util.ArrayList;

/**
 * Representa um cliente da oficina, com informações como nome, endereço,
 * telefone,
 * email, CPF anonimizado e uma lista de veículos associados ao cliente.
 * Esta classe oferece funcionalidades para adicionar, editar, remover e listar
 * veículos
 * de um cliente, além de realizar o cadastro de novos clientes via console.
 */
public class Cliente {

    private int id;

    /**
     * Nome do cliente.
     */
    private String nome;

    /**
     * Endereço do cliente.
     */
    private String endereco;

    /**
     * Telefone do cliente.
     */
    private String telefone;

    /**
     * Email do cliente.
     */
    private String email;

    /**
     * CPF anonimizado do cliente.
     */
    private String cpfAnonimizado;

    /**
     * Lista de veículos associados ao cliente.
     */
    private ArrayList<Veiculo> veiculos;

    /**
     * Construtor vazio que inicializa a lista de veículos.
     */
    public Cliente() {
        this.veiculos = new ArrayList<>();
    }

    /**
     * Construtor que inicializa um cliente com os dados fornecidos e também a lista
     * de veículos.
     * 
     * @param nome           O nome do cliente.
     * @param endereco       O endereço do cliente.
     * @param telefone       O telefone do cliente.
     * @param email          O email do cliente.
     * @param cpfAnonimizado O CPF anonimizado do cliente.
     */
    public Cliente(String nome, String endereco, String telefone, String email, String cpfAnonimizado) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cpfAnonimizado = cpfAnonimizado;
        this.veiculos = new ArrayList<>();
    }

    // Getters e Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do cliente.
     * 
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     * 
     * @param nome O nome do cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o endereço do cliente.
     * 
     * @return O endereço do cliente.
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Define o endereço do cliente.
     * 
     * @param endereco O endereço do cliente.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o telefone do cliente.
     * 
     * @return O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone do cliente.
     * 
     * @param telefone O telefone do cliente.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Retorna o email do cliente.
     * 
     * @return O email do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do cliente.
     * 
     * @param email O email do cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o CPF anonimizado do cliente.
     * 
     * @return O CPF anonimizado do cliente.
     */
    public String getCpfAnonimizado() {
        return cpfAnonimizado;
    }

    /**
     * Define o CPF anonimizado do cliente.
     * 
     * @param cpfAnonimizado O CPF anonimizado do cliente.
     */
    public void setCpfAnonimizado(String cpfAnonimizado) {
        this.cpfAnonimizado = cpfAnonimizado;
    }

    /**
     * Retorna a lista de veículos do cliente.
     * 
     * @return A lista de veículos do cliente.
     */
    public ArrayList<Veiculo> getVeiculos() {
        return veiculos;
    }

    /**
     * Define a lista de veículos do cliente.
     * 
     * @param veiculos A lista de veículos do cliente.
     */
    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    // Métodos de manipulação de veículos

    /**
     * Adiciona um veículo à lista de veículos do cliente.
     * 
     * @param veiculo O veículo a ser adicionado.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        this.veiculos.add(veiculo);
    }

    /**
     * Edita um veículo na lista de veículos do cliente, substituindo o veículo na
     * posição especificada.
     * 
     * @param index   O índice do veículo a ser editado.
     * @param veiculo O novo veículo a ser inserido.
     */
    public void editarVeiculo(int index, Veiculo veiculo) {
        if (index >= 0 && index < veiculos.size()) {
            veiculos.set(index, veiculo);
        }
    }

    /**
     * Remove um veículo da lista de veículos do cliente.
     * 
     * @param index O índice do veículo a ser removido.
     */
    public void removerVeiculo(int index) {
        if (index >= 0 && index < veiculos.size()) {
            veiculos.remove(index);
        }
    }

    /**
     * Lista todos os veículos associados ao cliente.
     */
    public void listarVeiculos() {
        for (Veiculo veiculo : veiculos) {
            System.out.println(veiculo);
        }
    }

    /**
     * Retorna uma representação em string do cliente, incluindo nome, endereço,
     * telefone, email,
     * CPF anonimizado e a lista de veículos.
     * 
     * @return A representação em string do cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", cpfAnonimizado='" + cpfAnonimizado + '\'' +
                ", veiculos=" + veiculos +
                '}';
    }

}