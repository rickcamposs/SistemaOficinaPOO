package com.sistemaoficina.enums;

/**
 * Enumeração que representa os diversos status possíveis para um serviço,
 * ordem de serviço ou agendamento dentro do sistema da oficina.
 * 
 * <p>
 * Cada constante indica uma etapa do fluxo de trabalho ou o estado atual do serviço/agendamento.
 * </p>
 *
 * <ul>
 *   <li>{@link #RECEBIDO} - Serviço recebido pela oficina.</li>
 *   <li>{@link #Analise_do_Mecanico_Geral} - Em análise pelo mecânico geral.</li>
 *   <li>{@link #Em_Manutenção_Geral} - Em manutenção geral.</li>
 *   <li>{@link #Enviado_Setor_Especializado} - Enviado para setor/mecânico especializado.</li>
 *   <li>{@link #Em_Manutenção_Especializada} - Em manutenção especializada.</li>
 *   <li>{@link #Finalizado} - Serviço finalizado.</li>
 *   <li>{@link #Direcionamento} - Serviço direcionado para outro setor/empresa/encaminhamento externo.</li>
 *   <li>{@link #Entregue} - Serviço ou veículo entregue ao cliente.</li>
 *   <li>{@link #Cancelado} - Serviço ou agendamento cancelado.</li>
 * </ul>
 */
public enum StatusServico {
    RECEBIDO,
    Analise_do_Mecanico_Geral,
    Em_Manutenção_Geral,
    Enviado_Setor_Especializado,
    Em_Manutenção_Especializada,
    Finalizado,
    Direcionamento,
    Entregue,
    Cancelado
}
