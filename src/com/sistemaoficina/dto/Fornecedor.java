package com.sistemaoficina.dto;

/**
 * Representa um fornecedor de produtos ou serviços.
 *
 * <p>
 * Armazena informações essenciais para contato e identificação.
 * <p>
 *
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Fornecedor {

    private int id;
    private String nomeReal;
    private String nomeFantasia;
    private String cnpj;
    private String telefone;
    private String email;
    private String tipoProduto;

    /**
     * Construtor da classe Fornecedor.
     *
     * @param nomeReal Nome oficial do fornecedor (razão social).
     * @param nomeFantasia Nome fantasia do fornecedor.
     * @param cnpj CNPJ do fornecedor.
     * @param telefone Telefone de contato.
     * @param email E-mail de contato.
     */
    public Fornecedor(String nomeReal, String nomeFantasia, String cnpj, String telefone, String email) {
        this.nomeReal = nomeReal;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
    }

    /**
     * @return ID do fornecedor.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Define o ID do fornecedor.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Nome real (razão social) do fornecedor.
     */
    public String getNomeReal() {
        return nomeReal;
    }

    /**
     * @param nomeReal Define o nome real (razão social).
     */
    public void setNomeReal(String nomeReal) {
        this.nomeReal = nomeReal;
    }

    /**
     * @return Nome fantasia do fornecedor.
     */
    public String getNomeFantasia() {
        return nomeFantasia;
    }

    /**
     * @param nomeFantasia Define o nome fantasia do fornecedor.
     */
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    /**
     * @return CNPJ do fornecedor.
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj Define o CNPJ do fornecedor.
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return Telefone de contato do fornecedor.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * @param telefone Define o telefone de contato.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * @return E-mail de contato do fornecedor.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email Define o e-mail de contato.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return o Tipo de Produto do fornecedor.
     */
    public String getTipoProduto() {
        return tipoProduto;
    }

    /**
     * @param tipoProduto Define o Tipo de Produto do fornecedor.
     */
    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    @Override
    public String toString() {
        return "Fornecedor{"
                + "id=" + id
                + ", nomeReal='" + nomeReal + '\''
                + ", nomeFantasia='" + nomeFantasia + '\''
                + ", cnpj='" + cnpj + '\''
                + ", telefone='" + telefone + '\''
                + ", email='" + email + '\''
                + ", tipoProduto='" + tipoProduto + '\''
                + '}';
    }
}
