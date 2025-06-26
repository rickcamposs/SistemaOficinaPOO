package com.sistemaoficina.enums;

/**
 * Enumeração que representa as categorias possíveis de despesas financeiras
 * dentro do sistema da oficina.
 * 
 * <p>
 * Cada valor representa um tipo de despesa comum no contexto de oficinas mecânicas.
 * </p>
 *
 * <ul>
 *   <li>{@link #DESPESAS_GERAIS} - Despesas operacionais gerais.</li>
 *   <li>{@link #DESPESAS_FUNCIONARIO} - Gastos relacionados a funcionários (salários, benefícios, etc).</li>
 *   <li>{@link #DESPESAS_PECAS} - Gastos com peças para conserto e manutenção.</li>
 *   <li>{@link #DESPESAS_CONTAS} - Pagamentos de contas fixas e serviços.</li>
 * </ul>
 */
public enum CategoriaDespesa {
    DESPESAS_GERAIS,
    DESPESAS_FUNCIONARIO,
    DESPESAS_PECAS,
    DESPESAS_CONTAS;
}
