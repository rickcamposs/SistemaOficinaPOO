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
import com.sistemaoficina.dto.Cliente;
import com.sistemaoficina.dto.Funcionario;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Veiculo;
import com.sistemaoficina.enums.StatusServico;

@SuppressWarnings("empty-statement")
public class DadosOrdemServico {
    private static final String ARQUIVO_ORDEMSERVICO = "bd/ordemservico.json";

    public static ArrayList<OrdemServico> listaOrdemServico = carregarOrdemServico();

    public static void salvarOrdemServicoJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_ORDEMSERVICO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaOrdemServico, writer);            
        } catch (IOException e) {
            System.out.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }
    
    public static ArrayList<OrdemServico> carregarOrdemServico(){
     try (FileReader reader = new FileReader (ARQUIVO_ORDEMSERVICO)){
         Type listaTipo = new TypeToken<ArrayList<OrdemServico>>(){}.getType();
         Gson gson = new GsonBuilder().setPrettyPrinting().create();
         return gson.fromJson(reader, listaTipo);
     } catch (IOException e){
         System.out.println("Arquivo de Ordem de Serviço não encontrado. Lista iniciada vazia");
         return new ArrayList<>();
     }
    }
    
    public static void cadastrar (Scanner scanner){
        if (DadosClientes.listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        DadosClientes.listar();
        if(DadosClientes.listaClientes.isEmpty()) return;
        System.out.println("Selecione o cliente para iniciar a Ordem de Serviço: ");
        int indiceCliente = scanner.nextInt();
        scanner.nextLine();
        
        Cliente cliente = DadosClientes.buscarId(indiceCliente);
        
        if(cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        if (cliente.getVeiculos().isEmpty()) {
            System.out.println("Cliente não têm veículos cadastrados.");
            return;
        }
        
        System.out.println("Selecione o veículo do cliente: ");
        DadosClientes.listarVeiculosCliente(cliente);
        int idVeiculo = scanner.nextInt();
        scanner.nextLine();

        if (!cliente.getVeiculos().contains(idVeiculo)) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        System.out.println("Selecione o funcionário responsável da Ordem de Serviço: ");

        DadosFuncionario.listar();
        if(DadosFuncionario.listaFuncionarios.isEmpty()) return;

        int indiceFuncionario = scanner.nextInt();
        scanner.nextLine();
        
        Funcionario funcionario = DadosFuncionario.buscarId(indiceFuncionario);

        if(funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        OptionalInt maxId = listaOrdemServico.stream()
                .mapToInt(OrdemServico::getId)
                .max();
                
        OrdemServico novaOrdem = new OrdemServico(maxId.isPresent() ? maxId.getAsInt() + 1 : 0, indiceCliente, indiceFuncionario, idVeiculo);
        listaOrdemServico.add(novaOrdem);
        salvarOrdemServicoJson();
        System.out.println("Ordem de Serviço criada!");
    
    }
    
    public static OrdemServico buscarId(int id){
        for (OrdemServico ordem : listaOrdemServico){
            if(ordem.getId() == id){
                return ordem;
            }
        }
        return null;
    }
    
    public static void listar(){
        if(listaOrdemServico.isEmpty()){
            System.out.println("Não há Ordens de Serviços para serem listadas");
        }

        
        for (OrdemServico ordem : listaOrdemServico){
            Cliente cliente = DadosClientes.buscarId(ordem.getIdCliente());
            Veiculo veiculo = DadosVeiculo.buscarId(ordem.getIdVeiculo());
            Funcionario funcionario = DadosFuncionario.buscarId(ordem.getIdFuncionarioResponsavel());
            System.out.println("Id: " + ordem.getId() + " - " + cliente.getNome() + 
                    " - R$" + ordem.getValorEstimado() + " - " + getTextoStatus(ordem.getStatus())
                    + " - Veículo: " + veiculo.getPlaca() + " " + veiculo.getModelo()
                    + " - Funcionário: " + funcionario.getNome());
        if (ordem.getStatus() == StatusServico.Direcionamento && ordem.getDiagnostico() != null){
            System.out.println(" |Diadnóstico: " + ordem.getDiagnostico());
        }
        }
    }
    
    public static String getTextoStatus(StatusServico status){
        if(status == null) {
            return "Status Indefinido";
        }
        
        return switch (status){
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
    
    public static void cancelar(Scanner scanner){
        if (listaOrdemServico.isEmpty()){
            System.out.println("Nenhuma Ordem de Servicço encontrada");
            return;
        }
        
        listar();
        if(listaOrdemServico.isEmpty()) return;
        System.out.println("Digite o número da Ordem de Serviço que deseja cancelar.");
        int id = Integer.parseInt(scanner.nextLine());
        
        OrdemServico ordemS = buscarId(id);
        int index = listaOrdemServico.indexOf(ordemS);
        if(ordemS == null) {
            System.out.println("Ordem de Serviço não existe!");
            return;
        }
        if(ordemS.isFinalizado()){
            System.out.println("Essa Ordem de Serviço está finalizada, não pode ser cancelada.");
        }
        if(ordemS.isCancelado()){
            System.out.println("Essa Ordem de Serviço já está cancelada.");
            return;
        }
        
        ordemS.setStatus(StatusServico.Cancelado);
        System.out.println("Ordem de Serviço Cancelada, 20% retido.");
        
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();
        
        System.out.println("Ordem de Serviço salva com sucesso!\n");
    }
    public static void finalizar(Scanner scanner) {
        if(listaOrdemServico.isEmpty()){
            System.out.println("Nenhuma Ordem de Serviço Encontrada.");
            return;
        }
        
        listar();
        if(listaOrdemServico.isEmpty()) return;
        System.out.print("Digite o número do agendamento que deseja cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());

        OrdemServico ordemS = buscarId(id);
        int index = listaOrdemServico.indexOf(ordemS);

        if(ordemS == null){
            System.out.println("Agendamento não existente!");
            return;
        } 
        if(ordemS.isCancelado()){
            System.out.println("Esse agendamento está cancelado, não pode ser finalizado.");
            return;
        }
        if(ordemS.isFinalizado()){
            System.out.println("Esse agendamento já foi finalizado.");
            return;
        }

        
        ordemS.setStatus(StatusServico.Finalizado);

        System.out.println("Agendamento finalizado!");
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();        
        System.out.println("Agendamento salvo com sucesso!\n");
    }
    
    public static void atualizar(Scanner scanner){
        listar();
        if(listaOrdemServico.isEmpty()) return;
        System.out.println("Escolha uma Ordem de Serviço para atualizar por ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdemServico ordemS = buscarId(id);
        if(ordemS == null){
            System.out.println("Ordem de Serviço inexistente.");
            return;
        }
        
        System.out.println("Status atual: " + getTextoStatus(ordemS.getStatus()) + " - Digite o status atualizado: ");
        System.out.println("Selecione o novo status:");
        System.out.println("1. Recebido");
        System.out.println("2. Analise do Mecanico Geral");
        System.out.println("3. Em Manutenção Geral");
        System.out.println("4. Enviado Setor Especialista");
        System.out.println("5. Em Manutenção Especialista");
        System.out.println("6. Entregue ");
        System.out.print("Escolha: ");

        int op = scanner.nextInt();
        scanner.nextLine();
        
        StatusServico novoStatus;

        switch (op) {
            case 1 -> novoStatus = StatusServico.RECEBIDO;
            case 2 -> novoStatus = StatusServico.Analise_do_Mecanico_Geral;
            case 3 -> novoStatus = StatusServico.Em_Manutenção_Geral;
            case 4 -> novoStatus = StatusServico.Enviado_Setor_Especializado;
            case 5 -> novoStatus = StatusServico.Em_Manutenção_Especializada;
            case 6 -> novoStatus = StatusServico.Entregue;
            default -> {
                System.out.println("Opção Inválida");
                return;
            }
        }
        
        System.out.println("Digite o diagnóstico atualizado: ");
        String diagnostico = scanner.nextLine();
        System.out.println("Solução Atualizada: ");
        String solucao = scanner.nextLine();

        int index = listaOrdemServico.indexOf(ordemS);
        ordemS.setStatus(novoStatus);
        ordemS.setDiagnostico(diagnostico);
        ordemS.setSolucao(solucao);
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();
        
        System.out.println("Ordem de Serviço atualizada com sucesso!");
    }
}
