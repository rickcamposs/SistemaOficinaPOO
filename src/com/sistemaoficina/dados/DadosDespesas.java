package com.sistemaoficina.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.dto.Despesa;
import com.sistemaoficina.enums.CategoriaDespesa;
import java.util.Date;
import java.util.OptionalInt;

public class DadosDespesas {
    
    private static final String ARQUIVO_DESPESA = "bd/despesas.json";

    public static ArrayList<Despesa> listaDespesa = carregarDespesa();

    public static ArrayList<Despesa> carregarDespesa() {
        try (FileReader reader = new FileReader(ARQUIVO_DESPESA)) {
            Type listaTipo = new TypeToken<ArrayList<Despesa>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo Despesa não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void salvarDespesaJson() {
        try (FileWriter writerDespesa = new FileWriter(ARQUIVO_DESPESA)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaDespesa, writerDespesa);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    public static void listar() {
        if(listaDespesa.isEmpty()){
            System.out.println("Não despesas para serem listados!");
        }
        for (Despesa d : listaDespesa) {
            System.out.println("DESPESAS");
            System.out.println( "ID: " + d.getId()+ " -> Nome: " +  d.getNome() + " | Data: " + d.getData() + 
                    " | Descrição: " + d.getDescricao() + " | Valor: " + d.getValor() + " | Tipo: " + d.getCategoria());
        }
    }
    
    public static void adicionarDespesa(Scanner scanner){
        System.out.println("Adicione a Despesa que Deseja");
        System.out.print("Nome da Despesa: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da Despesa: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Faça uma Descrição da Despesa: ");
        String descricao = scanner.nextLine();
        System.out.print("Selecione o Tipo de Despesa: ");
        CategoriaDespesa categoria =  selecionarCategoria(scanner);
        
        
        Despesa financa = new Despesa(nome, valor, new Date(), descricao, categoria);
        OptionalInt maxId = listaDespesa.stream()
            .mapToInt(Despesa::getId)
            .max();
        financa.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaDespesa.add(financa);
        salvarDespesaJson();
        
        System.out.println("Despesas salvas com sucesso.");
    }
    
    public static CategoriaDespesa selecionarCategoria(Scanner scanner){
        System.out.println("Selecione o tipo de combustível do veiculo:");
        System.out.println("1 - Despesas Gerais");
        System.out.println("2 - Despesas de Contas");
        System.out.println("3 - Despesas de Funcionários");
        System.out.println("4 - Despesas de Pecas");

        int categoriaEscolhida = scanner.nextInt();
        scanner.nextLine();

        return switch (categoriaEscolhida){
            case 1 -> CategoriaDespesa.DESPESAS_GERAIS;
            case 2 -> CategoriaDespesa.DESPESAS_CONTAS;
            case 3 -> CategoriaDespesa.DESPESAS_FUNCIONARIO;
            case 5 -> CategoriaDespesa.DESPESAS_PECAS;
            default -> {
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
                yield selecionarCategoria(scanner);
            }
        };
    }
    
    public static String getTextoCategoria(CategoriaDespesa categoria){
        if(categoria == null){
            return "Combustível Indefinido";
        }
        return switch (categoria) {
            case DESPESAS_GERAIS -> "Despesas Gerais";
            case DESPESAS_FUNCIONARIO -> "Despesas de Funcionarios";
            case DESPESAS_PECAS -> "Despesas de Peças";
            case DESPESAS_CONTAS -> "Despesas de Contas";
        };
    }
    
    public static Despesa buscarId(int id){
        for(Despesa f : listaDespesa){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }
    
    public static void excluirDespesa(Scanner scanner) {
        listar();
        if(listaDespesa.isEmpty()) return;
        System.out.print("Digite o número da Despesa que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Despesa f = buscarId(indice);
        if(f == null){
            System.out.println("Despesa inexistente!");
            return;
        }
        
        listaDespesa.remove(f);
        salvarDespesaJson();        
        System.out.println("Despesa excluida com sucesso.");
    }
    
    public static void editar(Scanner scanner){
        System.out.println("Escolha um veiculo por Id:");
        listar();
        if(listaDespesa.isEmpty()) return;
        int idVeiculo = Integer.parseInt(scanner.nextLine());
        Despesa despesa = buscarId(idVeiculo);
        if (despesa == null) {
            System.out.println("Veículo não existente!");
            return;
        }   
        System.out.println("Digite as informações do Veículo");
        System.out.print("Nome da Despesa: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da Despesa: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Categoria da Despesa: ");
        CategoriaDespesa categoria = selecionarCategoria(scanner);

        
        Despesa novaDespesa = new Despesa(nome, valor, new Date(), descricao, categoria);
        novaDespesa.setId(despesa.getId());

        int index = listaDespesa.indexOf(despesa);

        if (index != -1) {
            listaDespesa.set(index, novaDespesa);
        }

        salvarDespesaJson();
        
        System.out.println("Veículo editado com sucesso.");
    } 
}
