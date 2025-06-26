package com.sistemaoficina.dto;

/**
 * Representa um item de produto vinculado a uma ordem de serviço ou venda,
 * armazenando a quantidade utilizada e o ID do produto.
 * <p>
 * Esta classe serve para relacionar produtos e quantidades em outros contextos,
 * como ordens de serviço ou notas fiscais.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class ItemProduto {

    /** Identificador do produto relacionado a este item. */
    private int idProduto;

    /** Quantidade do produto utilizada ou vendida. */
    private int quantidade;

    /**
     * Construtor para criar um novo item de produto.
     *
     * @param idProduto  ID do produto.
     * @param quantidade Quantidade utilizada ou vendida.
     */
    public ItemProduto(int idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    /**
     * Obtém o identificador do produto deste item.
     *
     * @return O ID do produto.
     */
    public int getIdProduto() {
        return idProduto;
    }

    /**
     * Obtém a quantidade de produto deste item.
     *
     * @return A quantidade utilizada ou vendida.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Retorna uma representação em texto do item do produto.
     *
     * @return String detalhando id do produto e quantidade.
     */
    @Override
    public String toString() {
        return "ItemProduto{" +
                "idProduto='" + idProduto + '\'' +
                ", quantidade='" + quantidade + '\'' +
                '}';
    }

}
