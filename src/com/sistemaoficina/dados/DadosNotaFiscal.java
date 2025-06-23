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
import com.sistemaoficina.dto.Cliente;
import com.sistemaoficina.dto.NotaFiscal;
import com.sistemaoficina.dto.Despesa;
import com.sistemaoficina.dto.Veiculo;
import java.util.OptionalInt;


public class DadosNotaFiscal {
    
    private static final String ARQUIVO_NF = "bd/NF.json";

    public static ArrayList<NotaFiscal> listaNF = carregarNF();

    public static ArrayList<NotaFiscal> carregarNF() {
        try (FileReader reader = new FileReader(ARQUIVO_NF)) {
            Type listaTipo = new TypeToken<ArrayList<Despesa>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de Nota Fiscal não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void salvarNFJson() {
        try (FileWriter writerNF = new FileWriter(ARQUIVO_NF)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaNF, writerNF);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    public static void listar() {
        if(listaNF.isEmpty()){
            System.out.println("Não existem Notass Fiscais para serem listadas!");
        }
        for (NotaFiscal nf : listaNF) {
            System.out.println("Notas Fiscais");
            System.out.println("Descrição: " + nf.getId() + " | Valor: " + nf.getData());
        }
    }
    
     public static NotaFiscal buscarId(int id) {
        for(NotaFiscal n : listaNF){
            if(n.getId() == id){
                return n;
            }
        }
        return null;
    }
     
    public static void criarNF(Scanner scanner){
        System.out.println("Crie a Nota Fiscal");
        System.out.print("Descreva o que foi utilizado no serviço realizado: ");
        String descricao = scanner.nextLine();
        System.out.print("Informe a data de criação da NF: ");
        String data = scanner.nextLine();
        
        NotaFiscal f = new NotaFiscal(descricao, data);
        OptionalInt maxId = listaNF.stream()
            .mapToInt(NotaFiscal::getId)
            .max();
        f.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaNF.add(f);
        salvarNFJson();
        
        System.out.println("Despesas salvas com sucesso.");
    }
    
    public static void baixaNF(Scanner scanner) {
        listar();
        if(listaNF.isEmpty()) return;
        System.out.print("Digite o id da Nota Fiscal que deseja dar baixa: ");
        int indice = Integer.parseInt(scanner.nextLine());
        NotaFiscal v = buscarId(indice);
        if(v == null){
            System.out.println("NF inexistente!");
            return;
        }

        for (Cliente cliente : DadosClientes.listaClientes) {
            if (cliente.getVeiculos() != null && cliente.getVeiculos().contains(v.getId())) {
                System.out.println("Este veículo está vinculado a um cliente. Desvincule-o antes de excluir.");
                return;
            }
        }

        listaNF.remove(v);
        salvarNFJson(); 
        System.out.println("Veículo excluido com sucesso.");
    }
}
