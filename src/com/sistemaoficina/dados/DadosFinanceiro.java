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
import com.sistemaoficina.dto.NotaFiscal;
import com.sistemaoficina.dto.Despesa;
import com.sistemaoficina.enums.CategoriaFinanceiro;
import java.util.OptionalInt;

public class DadosFinanceiro {
    
    private static final String ARQUIVO_DESPESA = "bd/Despesas.json";

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
            System.out.println("Descrição: " + d.getDescricao() + " |Valor: " + d.getValor());
        }
    }
    
    public static void adicionarDespesa(Scanner scanner){
        System.out.println("Adicione a Despesa que Deseja");
        System.out.print("Nome do Gasto: ");
        String nome = scanner.nextLine();
        System.out.print("Valor da Despesa: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Digite a Data da Compra: ");
        String data = scanner.nextLine();
        System.out.print("Faça uma Descrição da Despesa: ");
        String descricao = scanner.nextLine();
        System.out.print("Selecione o Tipo de Despesa: ");
        CategoriaFinanceiro categoria =  selecionarCategoria(scanner);
        

        Despesa financa = new Despesa(nome, valor, data, descricao, categoria);
        OptionalInt maxId = listaDespesa.stream()
            .mapToInt(Despesa::getId)
            .max();
        financa.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaDespesa.add(financa);
        salvarDespesaJson();
        
        System.out.println("Despesas salvas com sucesso.");
    }
    
    public static CategoriaFinanceiro selecionarCategoria(Scanner scanner){
        System.out.println("Selecione o tipo de combustível do veiculo:");
        System.out.println("1 - Despesas Gerais");
        System.out.println("2 - Despesas de Contas");
        System.out.println("3 - Despesas de Funcionários");
        System.out.println("4 - Despesas de Pecas");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return switch (opcao){
            case 1 -> CategoriaFinanceiro.DESPESAS_GERAIS;
            case 2 -> CategoriaFinanceiro.DESPESAS_CONTAS;
            case 3 -> CategoriaFinanceiro.DESPESAS_FUNCIONARIO;
            case 5 -> CategoriaFinanceiro.DESPESAS_PECAS;
            default -> {
                System.out.println("Opção inválida. Por favor, selecione uma opção válida.");
                yield selecionarCategoria(scanner);
            }
        };
    }
    
    public static String getTextoCategoria(CategoriaFinanceiro categoria){
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
    
    /*public void gerarBalancoMensal(String mes, int ano){
        
        ArrayList<BalancoMensal> relatorioMensal = new ArrayList<>();
        
        double receitaTotal = 0;
        double despesaTotal = 0;
        
        
    }*/
    
}
