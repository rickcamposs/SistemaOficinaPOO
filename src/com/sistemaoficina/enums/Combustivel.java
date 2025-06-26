package com.sistemaoficina.enums;

/**
 * Enumeração que representa os tipos de combustíveis utilizados pelos veículos
 * cadastrados no sistema da oficina.
 * 
 * <p>
 * Cada constante desta enum define um tipo de combustível comum em veículos automotores.
 * </p>
 *
 * <ul>
 *   <li>{@link #GASOLINA} - Veículo movido à gasolina.</li>
 *   <li>{@link #ALCOOL} - Veículo movido à álcool (etanol).</li>
 *   <li>{@link #DIESEL} - Veículo movido à diesel.</li>
 *   <li>{@link #FLEX} - Veículo com motor flexível (gasolina e/ou álcool).</li>
 *   <li>{@link #ELETRICO} - Veículo elétrico.</li>
 *   <li>{@link #HIBRIDO} - Veículo híbrido (combina motor a combustão e elétrico).</li>
 * </ul>
 */
public enum Combustivel {
    GASOLINA,
    ALCOOL,
    DIESEL,
    FLEX,
    ELETRICO,
    HIBRIDO
}
