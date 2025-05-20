package com.sistemaoficina.dados;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sistemaoficina.dto.Agendamento;
import com.sistemaoficina.dto.Cliente;

public class DadosAgendamento {
    private static final String ARQUIVO_AGENDAMENTOS = "agendamentos.json";

    public static ArrayList<Agendamento> listaAgendamentos = carregarAgendamentos();

    public static void salvarAgendamentosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_AGENDAMENTOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaAgendamentos, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }

    public static ArrayList<Agendamento> carregarAgendamentos() {
        try (FileReader reader = new FileReader(ARQUIVO_AGENDAMENTOS)) {
            Type listaTipo = new TypeToken<ArrayList<Agendamento>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de agendamentos não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void cadastrar(Scanner scanner) {
        if (DadosClientes.listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        DadosClientes.listar();
        if(DadosClientes.listaClientes.isEmpty()) return;
        System.out.print("Selecione o cliente para agendamento: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = DadosClientes.buscarId(indice);

        if (cliente.getVeiculos().isEmpty()) {
            System.out.println("Cliente não tem veículos cadastrados.");
            return;
        }

        System.out.println("Veiculo: " + cliente.getVeiculos().get(0).getModelo() + " | Placa: " + cliente.getVeiculos().get(0).getPlaca());        

        System.out.print("Data do agendamento (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        System.out.print("Descrição do problema: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor estimado do serviço: R$ ");
        double valor = Double.parseDouble(scanner.nextLine());

        OptionalInt maxId = listaAgendamentos.stream()
            .mapToInt(Agendamento::getId)
            .max();
        Agendamento novoAgendamento = new Agendamento(maxId.isPresent() ? maxId.getAsInt() + 1 : 0, cliente.getNome(),
                cliente.getVeiculos().get(0).getPlaca(), data, descricao, valor);
        listaAgendamentos.add(novoAgendamento);
        salvarAgendamentosJson();
        System.out.println("Agendamento realizado com sucesso!\n");
    }

    public static Agendamento buscarId(int id){
        for(Agendamento a : listaAgendamentos){
            if(a.getId() == id){
                return a;
            }
        }
        return null;
    }

    public static void listar(){
        if(listaAgendamentos.isEmpty()){
            System.out.println("Não há agendamentos para serem listados");
        }
        for (Agendamento a : listaAgendamentos) {
            String textoCanceladoFinalizado = "Agendado";
            if(a.isCancelado()){
                textoCanceladoFinalizado = "Cancelado";
            }
            if(a.isFinalizado()){
                textoCanceladoFinalizado = "Finalizado";
            }
            System.out.println("Id: " + a.getId() + " - " + a.getNomeCliente() + 
                " - R$" + a.getValorEstimado() + " - " + textoCanceladoFinalizado);
        }
    }

    public static void cancelar(Scanner scanner) {
        if (listaAgendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
            return;
        }

        listar();
        if(listaAgendamentos.isEmpty()) return;
        System.out.print("Digite o número do agendamento que deseja cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Agendamento ag = buscarId(id);
        int index = listaAgendamentos.indexOf(ag);
        if(ag == null){
            System.out.println("Agendamento não existente!");
            return;
        }
        if(ag.isFinalizado()){
            System.out.println("Esse agendamento está finalizado, não pode ser cancelado.");
            return;
        }        
        if(ag.isCancelado()){
            System.out.println("Esse agendamento já está cancelado.");
            return;
        }

        ag.cancelar();
        System.out.println("Agendamento cancelado com 20% retido.");

        listaAgendamentos.set(index, ag);
        salvarAgendamentosJson();
        
        System.out.println("Agendamento salvo com sucesso!\n");
    }

    public static void finalizar(Scanner scanner) {
        listar();
        if(listaAgendamentos.isEmpty()) return;
        System.out.print("Digite o número do agendamento que deseja cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Agendamento ag = buscarId(id);
        int index = listaAgendamentos.indexOf(ag);

        if(ag == null){
            System.out.println("Agendamento não existente!");
            return;
        } 
        if(ag.isCancelado()){
            System.out.println("Esse agendamento está cancelado, não pode ser finalizado.");
            return;
        }
        if(ag.isFinalizado()){
            System.out.println("Esse agendamento já foi finalizado.");
            return;
        }

        ag.finalizar();

        System.out.println("Agendamento finalizado!");
        listaAgendamentos.set(index, ag);
        salvarAgendamentosJson();        
        System.out.println("Agendamento salvo com sucesso!\n");
    } 

}
