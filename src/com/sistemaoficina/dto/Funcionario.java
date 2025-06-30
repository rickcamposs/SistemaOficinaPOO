package com.sistemaoficina.dto;

/**
 * Representa um funcionário do sistema de oficina mecânica.
 * 
 * <p>
 * 
 * Contém informações sobre o nome, cargo, usuário, senha e salário do
 * funcionário.
 * 
 * <p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Funcionario {
    private int id;
    private String nome;
    private String cargo;
    private String usuario;
    private double salario;
    private String senha;

    /**
     * Construtor para criar um novo funcionário.
     * 
     * @param nome    O nome do funcionário.
     * @param cargo   O cargo do funcionário.
     * @param usuario O nome de usuário do funcionário para autenticação.
     * @param senha   A senha do funcionário para autenticação.
     * @param salario O salário do funcionário.
     */
    public Funcionario(String nome, String cargo, String usuario, String senha, double salario) {
        this.nome = nome;
        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
        this.salario = salario;
    }

    /**
     * Obtém o nome de usuário do funcionário.
     * 
     * @return O nome de usuário do funcionário.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Obtém a senha do funcionário.
     * 
     * @return A senha do funcionário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Obtém o nome do funcionário.
     * 
     * @return O nome do funcionário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o cargo do funcionário.
     * 
     * @return O cargo do funcionário.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Obtém o salário do funcionário.
     * 
     * @return O salário do funcionário.
     */
    public double getSalario() {
        return salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Valida as credenciais do funcionário (usuário e senha).
     * 
     * @param usuario O nome de usuário fornecido para autenticação.
     * @param senha   A senha fornecida para autenticação.
     * @return Verdadeiro se as credenciais corresponderem, falso caso contrário.
     */
    public boolean validarCredenciais(String usuario, String senha) {
        return this.usuario.equals(usuario) && this.senha.equals(senha);
    }
    
    @Override
    public String toString() { //Questão 3
        return "Funcionario{" +
                "nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario='" + salario + '\'' +
                ", usuario='" + usuario + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
    
}
