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
import com.sistemaoficina.enums.StatusServico;

@SuppressWarnings("empty-statement")
public class DadosAgendamento {
    private static final String ARQUIVO_AGENDAMENTOS = "bd/agendamentos.json";

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
            System.out.println("Id: " + a.getId() + " - " + a.getNomeCliente() + 
                " - R$" + a.getValorEstimado() + " - " + getTextoStatus(a.getStatus()));
        if (a.getStatus() == StatusServico.Direcionamento && a.getDirecionamento() != null){
            System.out.println("| Direcionamento: " + a.getDirecionamento());
        }
        }
    }

    public static String getTextoStatus(StatusServico status){
        if(status == null){
            return "Status Indefinido";
        }
        return switch (status) {
            case RECEBIDO -> "Recebido";
            case Analise_do_Mecanico_Geral -> "Análise do Mecânico Geral";
            case Em_Manutenção_Geral -> "Em Manutenção Geral";
            case Enviado_Setor_Especializado -> "Enviado ao Setor Especializado";
            case Em_Manutenção_Especializada -> "Em Manutenção Especializada";
            case Direcionamento -> "Direcionamento";
            case Entregue -> "Entregue";
            case Cancelado -> "Cancelado";
            case Finalizado -> "Finalizado";
        };
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

        ag.setStatus(StatusServico.Cancelado);
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

        
        ag.setStatus(StatusServico.Finalizado);

        System.out.println("Agendamento finalizado!");
        listaAgendamentos.set(index, ag);
        salvarAgendamentosJson();        
        System.out.println("Agendamento salvo com sucesso!\n");
    } 
    
    public static void atualizarStatus(Scanner scanner) {
        listar();
        StatusServico novoStatus;
        System.out.print("Digite o ID do agendamento: ");
        int id = Integer.parseInt(scanner.nextLine());
        Agendamento ag = buscarId(id);
        int index = listaAgendamentos.indexOf(ag);
        if (ag == null) {
            System.out.println("Agendamento não encontrado.");
             return;
        }
            
        System.out.println("Status atual: " + getTextoStatus(ag.getStatus()));
        System.out.println("Selecione o novo status:");
        System.out.println("1. Recebido");
        System.out.println("2. Analise do Mecanico Geral");
        System.out.println("3. Em Manutenção Geral");
        System.out.println("4. Enviado Setor Especialista");
        System.out.println("5. Em Manutenção Especialista");
        System.out.println("6. Direcionamento");
        System.out.println("7. Entregue ");
        System.out.print("Escolha: ");
        
        int op = scanner.nextInt();
        scanner.nextLine();
        
        novoStatus = null;

        switch (op) {
            case 1 -> novoStatus = StatusServico.RECEBIDO;
            case 2 -> novoStatus = StatusServico.Analise_do_Mecanico_Geral;
            case 3 -> novoStatus = StatusServico.Em_Manutenção_Geral;
            case 4 -> novoStatus = StatusServico.Enviado_Setor_Especializado;
            case 5 -> novoStatus = StatusServico.Em_Manutenção_Especializada;
            case 6 -> { 
                novoStatus = StatusServico.Direcionamento;
                System.out.println("Informe o Direcionamento: ");
                String infoDirecionamento = scanner.nextLine();
                ag.setDirecionamento(infoDirecionamento);
            }
            case 7 -> novoStatus = StatusServico.Entregue;
            default -> {
                System.out.println("Opção Inválida");
                return;
            }
        }

        ag.setStatus(novoStatus);
        listaAgendamentos.set(index, ag);
        salvarAgendamentosJson();
        System.out.println("Status atualizado com sucesso!");
    }
}
