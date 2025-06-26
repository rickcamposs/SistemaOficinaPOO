package com.sistemaoficina.comparator;

import com.sistemaoficina.dto.Cliente;
import java.util.Comparator;

/**
 * Classe utilitária que provê {@link Comparator}s para ordenação de clientes.
 * <p>
 * Permite ordenar listas de {@link Cliente} por ID em ordem crescente ou decrescente.
 * </p>
 */
public class ComparatorCliente {

    /**
     * Comparator para ordenar {@link Cliente} pelo ID em ordem crescente.
     */
    public static Comparator<Cliente> usuarioCrescente = (a, b) ->
        Integer.valueOf(a.getId()).compareTo(Integer.valueOf(b.getId()));

    /**
     * Comparator para ordenar {@link Cliente} pelo ID em ordem decrescente.
     */
    public static Comparator<Cliente> usuarioDecrescente = (a, b) ->
        Integer.valueOf(b.getId()).compareTo(Integer.valueOf(a.getId()));
}
