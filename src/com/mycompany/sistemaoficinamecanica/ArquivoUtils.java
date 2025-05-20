package com.mycompany.sistemaoficinamecanica;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe utilitária para manipulação de arquivos JSON no sistema de oficina mecânica.
 * Contém métodos para salvar e carregar informações de clientes e funcionários utilizando a biblioteca Gson.
 */
public class ArquivoUtils {

    /**
     * Caminho do arquivo onde os clientes serão armazenados em formato JSON.
     */
    private static final String CAMINHO_CLIENTES = "clientes.json";

    /**
     * Instância do objeto Gson configurada para impressão formatada.
     */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Salva a lista de clientes no arquivo JSON.
     * 
     * @param listaClientes A lista de clientes a ser salva.
     */
    public static void salvarClientes(ArrayList<Clientes> listaClientes) {
        try (FileWriter writer = new FileWriter(CAMINHO_CLIENTES)) {
            gson.toJson(listaClientes, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar clientes: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de clientes a partir do arquivo JSON.
     * Caso o arquivo não exista ou ocorra um erro na leitura, retorna uma lista vazia.
     * 
     * @return A lista de clientes carregada do arquivo ou uma lista vazia se ocorrer erro.
     */
    public static ArrayList<Clientes> carregarClientes() {
        try (FileReader reader = new FileReader(CAMINHO_CLIENTES)) {
            Type listaTipo = new TypeToken<ArrayList<Clientes>>(){}.getType();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de clientes não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Carrega a lista de funcionários a partir do arquivo JSON.
     * Caso o arquivo não exista, cria um novo arquivo vazio.
     * 
     * @return A lista de funcionários carregada do arquivo ou uma lista vazia se não houver dados.
     */
    public static ArrayList<Funcionario> carregarFuncionarios() {
        File arquivo = new File("funcionarios.json");

        if (!arquivo.exists()) {
            System.out.println("Nenhum funcionário encontrado ainda. Arquivo será criado ao cadastrar.");
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            Gson gson = new Gson();
            Funcionario[] funcionariosArray = gson.fromJson(reader, Funcionario[].class);
            return new ArrayList<>(Arrays.asList(funcionariosArray));
        } catch (IOException e) {
            System.out.println("Erro ao carregar funcionários: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public static ArrayList<Agendamento> carregarAgendamentos() {
    try (java.io.FileReader reader = new java.io.FileReader("agendamentos.json")) {
        Gson gson = new Gson();
        java.lang.reflect.Type tipo = new com.google.gson.reflect.TypeToken<ArrayList<Agendamento>>() {}.getType();
        return gson.fromJson(reader, tipo);
    } catch (Exception e) {
        return new ArrayList<>(); // Retorna lista vazia se não conseguir ler
    }
}
}
