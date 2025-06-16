package com.sistemaoficina.comparator;

import com.sistemaoficina.dto.Cliente;
import java.util.Comparator;


public class ComparatorCliente{
    
    public static Comparator<Cliente> usuarioCrescente = (a,b) -> Integer.valueOf(a.getId()).compareTo(Integer.valueOf(b.getId()));
    
    public static Comparator<Cliente> usuarioDecrescente = (a,b) -> Integer.valueOf(b.getId()).compareTo(Integer.valueOf(a.getId()));
}