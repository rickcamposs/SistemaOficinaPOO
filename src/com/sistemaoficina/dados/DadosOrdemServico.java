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
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.enums.StatusServico;

@SuppressWarnings("empty-statement")
public class DadosOrdemServico {
    private static final String ARQUIVO_ORDEMSERVICO = "bd/OrdemdeServico.json";

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
        int indice = scanner.nextInt();
        scanner.nextLine();
        
        Cliente cliente = DadosClientes.buscarId(indice);
        
        if (cliente.getVeiculos().isEmpty()) {
            System.out.println("Cliente não têm veículos cadastrados.");
            return;
        }
        
        System.out.println("Veiculo: " + cliente.getVeiculos().get(0).getModelo() + "| Placa: " +cliente.getVeiculos().get(0).getPlaca());
        
    
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
            System.out.println("Id: " + ordem.getId() + " - " + ordem.getNomeCliente() + 
                    " - R$" + ordem.getValorEstimado() + " - " + getTextoStatus(ordem.getStatus()));
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
        /*int index = listaOrdemServico.indexOf(ordemS);*/
        if(ordemS == null){
            System.out.println("Ordem de Serviço inexistente.");
            return;
        }
        
        System.out.println("Digite o diagnóstico atualizado: ");
        String diagnostico = scanner.nextLine();
        System.out.println("Solução Atualizada: ");
        String solucao = scanner.nextLine();
        
        OrdemServico novaOrdem = new OrdemServico(diagnostico, solucao);
        novaOrdem.setId(ordemS.getId());
        
        int index = listaOrdemServico.indexOf(ordemS);
        
        if(index != -1){
            listaOrdemServico.set(index, novaOrdem);
        }
        salvarOrdemServicoJson();
        
        System.out.println("Ordem de Serviço atualizada com sucesso!");
    }
}
