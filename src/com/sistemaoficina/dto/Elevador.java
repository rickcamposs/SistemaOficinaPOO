package com.sistemaoficina.dto;

public class Elevador {
    private Integer idOrdemServico;
    private String tipoElevador;

    public Elevador(String tipoElevador) {
        this.tipoElevador = tipoElevador;
    }
    
    public Integer getIdOrdemServico() {
        return idOrdemServico;
    }
    public void setIdOrdemServico(Integer idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }
    public String getTipoElevador() {
        return tipoElevador;
    }
    public void setTipoElevador(String tipoElevador) {
        this.tipoElevador = tipoElevador;
    }
    
    @Override
    public String toString() {
        return "Elevador{" +
                "idOrdemServico='" + idOrdemServico + '\'' +
                ", tipoElevador='" + tipoElevador + '\'' +
                '}';
    }
    
}
