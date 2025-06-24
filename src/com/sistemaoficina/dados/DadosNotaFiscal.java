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
import com.sistemaoficina.dto.OrdemServico;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.OptionalInt;


public class DadosNotaFiscal {
    
    private static final String ARQUIVO_NF = "bd/notafiscal.json";

    public static ArrayList<NotaFiscal> listaNF = carregarNF();

    public static ArrayList<NotaFiscal> carregarNF() {
        try (FileReader reader = new FileReader(ARQUIVO_NF)) {
            Type listaTipo = new TypeToken<ArrayList<NotaFiscal>>() {
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
            System.out.println("Não existem Notas Fiscais para serem listadas!");
        }
        for (NotaFiscal nf : listaNF) {
            System.out.println("Notas Fiscais");
            System.out.println( "ID: " + nf.getId()+ " -> Descrição: " +  nf.getDescricao() + " | Data: " + nf.getData()
            + "| Valor Total do Serviço: " + DadosOrdemServico.buscarId(nf.getIdOrdemServico()).getValorEstimado());
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
     
    public static void criarNF(Scanner scanner) {
        System.out.println("Crie a Nota Fiscal");
        System.out.print("Descreva o que foi utilizado no serviço realizado: ");
        String descricao = scanner.nextLine();
        System.out.println("Selecione o ID da Ordem de Serviço referente a essa Nota Fiscal: ");
        DadosOrdemServico.listar();
        if (DadosOrdemServico.listaOrdemServico.isEmpty()) {
            return;
        }
        int idOrdemServico = Integer.parseInt(scanner.nextLine());
        OrdemServico or = DadosOrdemServico.buscarId(idOrdemServico);
        if (or == null) {
            System.out.println("Ordem de Serviço inexistente");
            return;
        }
        
        NotaFiscal f = new NotaFiscal(new Date(), descricao, idOrdemServico);
        OptionalInt maxId = listaNF.stream()
            .mapToInt(NotaFiscal::getId)
            .max();
        f.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);

        listaNF.add(f);
        salvarNFJson();
        
        System.out.println("NF salva com sucesso.");
    }
    
    public static void imprimirNF(Scanner scanner){
        if (listaNF.isEmpty()) {
        System.out.println("Não há notas fiscais para imprimir.");
        return;
    }

    listar(); // Mostra resumo para o usuário escolher

    System.out.print("Digite o ID da nota fiscal que deseja imprimir: ");
    int id = Integer.parseInt(scanner.nextLine());
    NotaFiscal nf = buscarId(id);

    if (nf == null) {
        System.out.println("Nota Fiscal não encontrada.");
    } else {
        imprimirNotaFiscal(nf); // Aqui faz o print completo
    }
    }
    
    public static void imprimirNotaFiscal(NotaFiscal nf) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    System.out.println("========================================");
    System.out.println("            OFICINA MILHO VERDE         ");
    System.out.println("           NOTA FISCAL SIMPLIFICADA     ");
    System.out.println("========================================");
    System.out.println("Data: " + sdf.format(nf.getData()));
    System.out.println("----------------------------------------");
    System.out.println("ID: " + nf.getId());
    System.out.println("Descrição: " + nf.getDescricao());

    OrdemServico os = DadosOrdemServico.buscarId(nf.getIdOrdemServico());
    if (os != null) {
        System.out.printf("Valor Total do Serviço: R$ %.2f\n", os.getValorEstimado());
    } else {
        System.out.println("Valor Total do Serviço: [OS não encontrada]");
    }

    System.out.println("----------------------------------------");
    System.out.println("Obrigado pela preferência!");
    System.out.println("========================================\n");
}
}
