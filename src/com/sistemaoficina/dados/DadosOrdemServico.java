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
import com.sistemaoficina.dto.ItemProduto;
import com.sistemaoficina.dto.OrdemServico;
import com.sistemaoficina.dto.Produto;
import com.sistemaoficina.dto.Veiculo;
import com.sistemaoficina.enums.StatusServico;

/**
 * Classe responsável pelo gerenciamento das Ordens de Serviço (OS) da oficina.
 * Permite cadastrar, editar, listar, cancelar, finalizar e atualizar ordens de serviço,
 * além de vincular produtos às OS e realizar a persistência dos dados em arquivo JSON.
 * 
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
@SuppressWarnings("empty-statement")
public class DadosOrdemServico {
    /** Caminho do arquivo JSON de armazenamento das ordens de serviço. */
    private static final String ARQUIVO_ORDEMSERVICO = "bd/ordemservico.json";
    /** Lista estática das ordens de serviço carregadas em memória. */
    public static ArrayList<OrdemServico> listaOrdemServico = carregarOrdemServico();

    /**
     * Salva a lista de ordens de serviço no arquivo JSON.
     */
    public static void salvarOrdemServicoJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_ORDEMSERVICO)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaOrdemServico, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar agendamentos: " + e.getMessage());
        }
    }

    /**
     * Carrega as ordens de serviço do arquivo JSON para a memória.
     * 
     * @return Lista de ordens de serviço carregada do arquivo ou lista vazia se o arquivo não existir.
     */
    public static ArrayList<OrdemServico> carregarOrdemServico() {
        try (FileReader reader = new FileReader(ARQUIVO_ORDEMSERVICO)) {
            Type listaTipo = new TypeToken<ArrayList<OrdemServico>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de Ordem de Serviço não encontrado. Lista iniciada vazia");
            return new ArrayList<>();
        }
    }

    /**
     * Cadastra uma nova Ordem de Serviço para um cliente, associando um veículo e um funcionário.
     * 
     * @param scanner Scanner utilizado para entrada dos dados pelo usuário.
     */
    public static void cadastrar(Scanner scanner) {
        if (DadosClientes.listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        DadosClientes.listar();
        if (DadosClientes.listaClientes.isEmpty()) {
            return;
        }
        System.out.println("Selecione o cliente para iniciar a Ordem de Serviço: ");
        int indiceCliente = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = DadosClientes.buscarId(indiceCliente);
        if (cliente == null) {
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
        if (DadosFuncionario.listaFuncionarios.isEmpty()) {
            return;
        }
        int indiceFuncionario = scanner.nextInt();
        scanner.nextLine();
        Funcionario funcionario = DadosFuncionario.buscarId(indiceFuncionario);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }
        System.out.println("Informe o Valor Estimado para o Serviço: ");
        double valorEstimado = Double.parseDouble(scanner.nextLine());
        OptionalInt maxId = listaOrdemServico.stream()
                .mapToInt(OrdemServico::getId)
                .max();
        OrdemServico novaOrdem = new OrdemServico(maxId.isPresent() ? maxId.getAsInt() + 1 : 0, indiceCliente,
                indiceFuncionario, idVeiculo, valorEstimado);
        listaOrdemServico.add(novaOrdem);
        salvarOrdemServicoJson();
        System.out.println("Ordem de Serviço criada!");
    }

    /**
     * Busca uma ordem de serviço pelo seu ID.
     * 
     * @param id O ID da ordem de serviço a ser buscada.
     * @return OrdemServico encontrada, ou null se não existir.
     */
    public static OrdemServico buscarId(int id) {
        for (OrdemServico ordem : listaOrdemServico) {
            if (ordem.getId() == id) {
                return ordem;
            }
        }
        return null;
    }

    /**
     * Lista todas as ordens de serviço cadastradas, mostrando informações do cliente, veículo, funcionário e status.
     */
    public static void listar() {
        if (listaOrdemServico.isEmpty()) {
            System.out.println("Não há Ordens de Serviços para serem listadas");
        }
        for (OrdemServico ordem : listaOrdemServico) {
            Cliente cliente = DadosClientes.buscarId(ordem.getIdCliente());
            Veiculo veiculo = DadosVeiculo.buscarId(ordem.getIdVeiculo());
            Funcionario funcionario = DadosFuncionario.buscarId(ordem.getIdFuncionarioResponsavel());
            System.out.println("Id: " + ordem.getId() + " - " + cliente.getNome()
                    + " - R$" + ordem.getValorEstimado() + " - " + getTextoStatus(ordem.getStatus())
                    + " - Veículo: " + veiculo.getPlaca() + " " + veiculo.getModelo()
                    + " - Funcionário: " + funcionario.getNome());
            if (ordem.getStatus() == StatusServico.Direcionamento && ordem.getDiagnostico() != null) {
                System.out.println(" |Diadnóstico: " + ordem.getDiagnostico());
            }
        }
    }

    /**
     * Retorna o texto correspondente ao status da ordem de serviço.
     * 
     * @param status StatusServico da ordem.
     * @return Texto para exibição.
     */
    public static String getTextoStatus(StatusServico status) {
        if (status == null) {
            return "Status Indefinido";
        }
        return switch (status) {
            case RECEBIDO ->
                "Recebido";
            case Analise_do_Mecanico_Geral ->
                "Análise do Mecânico Geral";
            case Em_Manutenção_Geral ->
                "Em Manutenção Geral";
            case Enviado_Setor_Especializado ->
                "Enviado ao Setor Especializado";
            case Em_Manutenção_Especializada ->
                "Em Manutenção Especializada";
            case Direcionamento ->
                "Direcionamento";
            case Entregue ->
                "Entregue";
            case Cancelado ->
                "Cancelado";
            case Finalizado ->
                "Finalizado";
        };
    }

    /**
     * Cancela uma ordem de serviço escolhida pelo usuário, se não estiver finalizada ou já cancelada.
     * 
     * @param scanner Scanner utilizado para entrada de dados do usuário.
     */
    public static void cancelar(Scanner scanner) {
        if (listaOrdemServico.isEmpty()) {
            System.out.println("Nenhuma Ordem de Servicço encontrada");
            return;
        }
        listar();
        if (listaOrdemServico.isEmpty()) {
            return;
        }
        System.out.println("Digite o número da Ordem de Serviço que deseja cancelar.");
        int id = Integer.parseInt(scanner.nextLine());
        OrdemServico ordemS = buscarId(id);
        int index = listaOrdemServico.indexOf(ordemS);
        if (ordemS == null) {
            System.out.println("Ordem de Serviço não existe!");
            return;
        }
        if (ordemS.isFinalizado()) {
            System.out.println("Essa Ordem de Serviço está finalizada, não pode ser cancelada.");
        }
        if (ordemS.isCancelado()) {
            System.out.println("Essa Ordem de Serviço já está cancelada.");
            return;
        }
        ordemS.setStatus(StatusServico.Cancelado);
        System.out.println("Ordem de Serviço Cancelada, 20% retido.");
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();
        System.out.println("Ordem de Serviço salva com sucesso!\n");
    }

    /**
     * Finaliza uma ordem de serviço, se não estiver cancelada ou já finalizada.
     * 
     * @param scanner Scanner utilizado para entrada de dados do usuário.
     */
    public static void finalizar(Scanner scanner) {
        if (listaOrdemServico.isEmpty()) {
            System.out.println("Nenhuma Ordem de Serviço Encontrada.");
            return;
        }
        listar();
        if (listaOrdemServico.isEmpty()) {
            return;
        }
        System.out.print("Digite o número do agendamento que deseja cancelar: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdemServico ordemS = buscarId(id);
        int index = listaOrdemServico.indexOf(ordemS);
        if (ordemS == null) {
            System.out.println("Agendamento não existente!");
            return;
        }
        if (ordemS.isCancelado()) {
            System.out.println("Esse agendamento está cancelado, não pode ser finalizado.");
            return;
        }
        if (ordemS.isFinalizado()) {
            System.out.println("Esse agendamento já foi finalizado.");
            return;
        }
        ordemS.setStatus(StatusServico.Finalizado);
        System.out.println("Agendamento finalizado!");
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();
        System.out.println("Agendamento salvo com sucesso!\n");
    }

    /**
     * Atualiza informações de uma ordem de serviço, como status, diagnóstico, solução e valor.
     * 
     * @param scanner Scanner utilizado para entrada dos dados do usuário.
     */
    public static void atualizar(Scanner scanner) {
        listar();
        if (listaOrdemServico.isEmpty()) {
            return;
        }
        System.out.println("Escolha uma Ordem de Serviço para atualizar por ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        OrdemServico ordemS = buscarId(id);
        if (ordemS == null) {
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
            case 1 ->
                novoStatus = StatusServico.RECEBIDO;
            case 2 ->
                novoStatus = StatusServico.Analise_do_Mecanico_Geral;
            case 3 ->
                novoStatus = StatusServico.Em_Manutenção_Geral;
            case 4 ->
                novoStatus = StatusServico.Enviado_Setor_Especializado;
            case 5 ->
                novoStatus = StatusServico.Em_Manutenção_Especializada;
            case 6 ->
                novoStatus = StatusServico.Entregue;
            default -> {
                System.out.println("Opção Inválida");
                return;
            }
        }
        System.out.println("Digite o diagnóstico atualizado: ");
        String diagnostico = scanner.nextLine();
        System.out.println("Solução Atualizada: ");
        String solucao = scanner.nextLine();
        System.out.println("Atualize o Valor Final: ");
        double valorTotal = Double.parseDouble(scanner.nextLine());
        int index = listaOrdemServico.indexOf(ordemS);
        ordemS.setStatus(novoStatus);
        ordemS.setDiagnostico(diagnostico);
        ordemS.setSolucao(solucao);
        ordemS.setValorEstimado(valorTotal);
        listaOrdemServico.set(index, ordemS);
        salvarOrdemServicoJson();
        System.out.println("Ordem de Serviço atualizada com sucesso!");
    }

    /**
     * Adiciona produtos a uma ordem de serviço e atualiza o estoque.
     * Exibe o valor total dos produtos e do serviço ao final.
     * 
     * @param idOrdem ID da ordem de serviço que receberá os produtos.
     * @param scanner Scanner utilizado para entrada de dados do usuário.
     */
    public static void adicionarProdutosNaOrdem(int idOrdem, Scanner scanner) {
        OrdemServico ordem = buscarId(idOrdem);
        if (ordem == null) {
            System.out.println("Ordem de Serviço não encontrada.");
            return;
        }
        if (ordem.getProdutosUtilizados() == null) {
            ordem.setProdutosUtilizados(new ArrayList<>());
        }
        boolean continuar = true;
        while (continuar) {
            DadosProduto.listar();
            System.out.print("Digite o ID do produto a ser adicionado: ");
            int idProduto = Integer.parseInt(scanner.nextLine());
            Produto produto = DadosProduto.buscarId(idProduto);
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }
            System.out.print("Quantidade a usar: ");
            int quantidade = Integer.parseInt(scanner.nextLine());
            if (quantidade > produto.getQuantidade()) {
                System.out.println("Estoque insuficiente. Disponível: " + produto.getQuantidade());
                continue;
            }
            ItemProduto item = new ItemProduto(idProduto, quantidade);
            ordem.getProdutosUtilizados().add(item);
            DadosProduto.reduzirEstoque(idProduto, quantidade);
            System.out.println("Produto adicionado com sucesso.");
            System.out.print("Deseja adicionar mais produtos? (s/n): ");
            String resposta = scanner.nextLine().trim().toLowerCase();
            if (!resposta.equals("s")) {
                continuar = false;
            }
        }
        salvarOrdemServicoJson();
        System.out.println("Produtos vinculados à ordem de serviço.");
        double totalProdutos = 0;
        for (ItemProduto item : ordem.getProdutosUtilizados()) {
            Produto prod = DadosProduto.buscarId(item.getIdProduto());
            if (prod != null) {
                totalProdutos += prod.getValorVendido() * item.getQuantidade();
            }
        }
        double valorServico = ordem.getValorEstimado();
        double totalFinal = valorServico + totalProdutos;
        System.out.printf("Valor do Serviço: R$ %.2f%n", valorServico);
        System.out.printf("Valor dos Produtos: R$ %.2f%n", totalProdutos);
        System.out.printf("TOTAL: R$ %.2f%n", totalFinal);

        salvarOrdemServicoJson();
        System.out.println("Produtos vinculados à ordem de serviço.");
    }
}
