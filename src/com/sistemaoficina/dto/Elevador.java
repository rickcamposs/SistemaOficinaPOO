package com.sistemaoficina.dto;

/**
 * Representa um elevador na oficina.
 * <p>
 * O elevador pode ser do tipo geral ou de alinhamento e balanceamento, 
 * e pode estar associado a uma ordem de serviço específica.
 * </p>
 * 
 * @author Janaina Alves Cordeiro
 * @version 1.0
 */
public class Elevador {

    /** ID da Ordem de Serviço associada ao elevador (pode ser nulo se não estiver ocupado). */
    private Integer idOrdemServico;

    /** Tipo do elevador (ex: "Elevador geral", "Elevador de alinhamento e balanceamento"). */
    private String tipoElevador;

    /**
     * Construtor para criar um novo elevador.
     * 
     * @param tipoElevador O tipo do elevador (ex: "Elevador geral").
     */
    public Elevador(String tipoElevador) {
        this.tipoElevador = tipoElevador;
    }
    
    /**
     * Retorna o ID da ordem de serviço vinculada ao elevador.
     * 
     * @return ID da ordem de serviço, ou null se não houver ordem vinculada.
     */
    public Integer getIdOrdemServico() {
        return idOrdemServico;
    }

    /**
     * Define o ID da ordem de serviço vinculada ao elevador.
     * 
     * @param idOrdemServico O novo ID da ordem de serviço (ou null para desvincular).
     */
    public void setIdOrdemServico(Integer idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }

    /**
     * Retorna o tipo do elevador.
     * 
     * @return Tipo do elevador (ex: "Elevador geral").
     */
    public String getTipoElevador() {
        return tipoElevador;
    }

    /**
     * Define o tipo do elevador.
     * 
     * @param tipoElevador O novo tipo do elevador.
     */
    public void setTipoElevador(String tipoElevador) {
        this.tipoElevador = tipoElevador;
    }
    
    /**
     * Retorna uma representação textual do elevador.
     * 
     * @return String com os dados do elevador.
     */
    @Override
    public String toString() { //Questão 3
        return "Elevador{" +
                "idOrdemServico='" + idOrdemServico + '\'' +
                ", tipoElevador='" + tipoElevador + '\'' +
                '}';
    }
    
}
