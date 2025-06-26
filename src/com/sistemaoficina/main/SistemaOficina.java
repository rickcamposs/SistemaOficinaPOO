package com.sistemaoficina.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.sistemaoficina.dados.DadosAgendamento;
import com.sistemaoficina.dados.DadosClientes;
import com.sistemaoficina.dados.DadosElevador;
import com.sistemaoficina.dados.DadosDespesas;
import com.sistemaoficina.dados.DadosFuncionario;
import com.sistemaoficina.dados.DadosNotaFiscal;
import com.sistemaoficina.dados.DadosOrdemServico;
import com.sistemaoficina.dados.DadosPonto;
import com.sistemaoficina.dados.DadosProduto;
import com.sistemaoficina.dados.DadosVeiculo;
import com.sistemaoficina.dados.DadosVendas;
import com.sistemaoficina.dto.Funcionario;

/**
 * Classe principal do sistema de oficina, responsável por inicializar
 * o programa, realizar login de funcionários e exibir o menu principal
 * conforme o perfil do usuário logado.
 * 
 * <p>
 * Cada menu oferece opções de acordo com o perfil do usuário (funcionário, admin ou proprietário),
 * encaminhando as chamadas para os métodos responsáveis pelo CRUD e operações de cada módulo
 * do sistema.
 * </p>
 * 
 * <p>
 * Este sistema utiliza um modelo baseado em menus, permitindo navegação entre módulos como:
 * Cliente, Veículo, Produto, Agendamento, Ordem de Serviço, Funcionário, Financeiro, Vendas e Ponto.
 * </p>
 * 
 * @author Seu Nome
 */

public class SistemaOficina {
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * Método principal que inicia o sistema e solicita o login do funcionário.
     * 
     * @param args Argumentos de linha de comando (não utilizados).
     */

    public static void main(String[] args) {
        Funcionario funcionarioLogado = DadosFuncionario.realizaLogin(scanner);
        if (funcionarioLogado != null) {
            menuPrincipal(funcionarioLogado);
        } else {
            System.out.println("Não foi possível autenticar, encerrando o programa.");
        }
        scanner.close();
    }
    
    /**
     * Exibe o menu principal e redireciona para os submenus conforme
     * o perfil do funcionário autenticado.
     * 
     * @param usuario Funcionário logado.
     */


    public static void menuPrincipal(Funcionario usuario) {
        int opcao;
        boolean usuarioProprietario = usuario.getCargo().equals("Proprietario");
        boolean usuarioAdmin = usuario.getCargo().equals("Admin");

        Map<Integer, Runnable> acoesMenu = new HashMap<>();
        acoesMenu.put(1, () -> menuCliente());
        acoesMenu.put(2, () -> menuVeiculo());
        acoesMenu.put(3, () -> menuProduto());
        acoesMenu.put(4, () -> menuAgendamento());
        acoesMenu.put(5, () -> menuElevador());
        acoesMenu.put(6, () -> menuOrdemServico());
        acoesMenu.put(7, () -> menuPonto(usuario));
        acoesMenu.put(8, () -> menuVendasServicos());

        if (usuarioAdmin || usuarioProprietario) {
            acoesMenu.put(9, () -> menuFuncionario());
            acoesMenu.put(10, () -> menuAdmin(usuario));
        }

        if (usuarioProprietario) {
            acoesMenu.put(11, () -> menuFinanceiro());
        }

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Menu Cliente");
            System.out.println("2. Menu Veículo");
            System.out.println("3. Menu Produto");
            System.out.println("4. Menu Agendamento");
            System.out.println("5. Menu Elevador");
            System.out.println("6. Menu Ordem de Serviço");
            System.out.println("7. Menu Ponto");
            System.out.println("8. Menu Vendas");

            if (usuarioAdmin || usuarioProprietario) {
                System.out.println("9. Menu Funcionario");
                System.out.println("10. Menu Admin");
            }

            if (usuarioProprietario) {
                System.out.println("11. Menu Financeiro");
            }

            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 0) {
                System.err.println("O PROGRAMA FOI ENCERRADO!");
                break;
            }

            Runnable acao = acoesMenu.get(opcao);
            if (acao != null) {
                acao.run();
            } else {
                System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
    
     /**
     * Exibe o menu do módulo de clientes e encaminha as opções
     * para o respectivo CRUD.
     */

    public static void menuCliente(){
        int opcao;
        do {
            System.out.println("\n--- CLIENTE ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Excluir Cliente");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Lista Clientes Crescente");
            System.out.println("6. Lista Clientes Decrescente");
            System.out.println("7. Busca Clientes (Iterator)");
            System.out.println("8. Busca Clientes (Binary Search)");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosClientes.cadastrar(scanner);
                case 2 -> DadosClientes.editar(scanner);
                case 3 -> DadosClientes.excluir(scanner);
                case 4 -> DadosClientes.listar();
                case 5 -> DadosClientes.listaCrescente();
                case 6 -> DadosClientes.listaDecrescente();
                case 7 -> DadosClientes.buscarIdIterator(scanner);
                case 8 -> DadosClientes.buscaIdBinary(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu do módulo de funcionários e permite operações de CRUD,
     * busca por iterator e forEach.
     */
    
    public static void menuFuncionario(){
        int opcao;
        do {
            System.out.println("\n--- FUNCIONARIO ---");
            System.out.println("1. Cadastrar Funcionario");
            System.out.println("2. Editar Funcionario");
            System.out.println("3. Excluir Funcionario");
            System.out.println("4. Listar Funcionarios");
            System.out.println("5. Buscar Funcionarios (Interator)");
            System.out.println("6. Buscar Funcionarios (ForEach)");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {                
                case 1 -> DadosFuncionario.cadastrar(scanner);
                case 2 -> DadosFuncionario.editar(scanner);
                case 3 -> DadosFuncionario.excluir(scanner);
                case 4 -> DadosFuncionario.listar();
                case 5 -> DadosFuncionario.buscarIdIterator(scanner);
                case 6 -> DadosFuncionario.buscarIdForEach(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu do módulo de produtos, permitindo cadastro,
     * edição, exclusão e consulta de estoque.
     */

    public static void menuProduto(){
        int opcao;
        do {
            System.out.println("\n--- PRODUTO ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Editar Produto");
            System.out.println("3. Verificar estoque de Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosProduto.cadastrar(scanner);         
                case 2 -> DadosProduto.editar(scanner);
                case 3 -> DadosProduto.listar();
                case 4 -> DadosProduto.excluir(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
     /**
     * Exibe o menu do módulo de agendamentos, incluindo cadastro,
     * cancelamento, finalização, listagem e atualização de status.
     */
    
    public static void menuAgendamento(){
        int opcao;
        do {
            System.out.println("\n--- AGENDAMENTO ---");
            System.out.println("1. Cadastrar Agendamento");
            System.out.println("2. Cancelar Agendamento");
            System.out.println("3. Finalizar Agendamento");
            System.out.println("4. Listar Agendamentos");
            System.out.println("5. Atualizar Status");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {   
                case 1 -> DadosAgendamento.cadastrar(scanner);   
                case 2 -> DadosAgendamento.cancelar(scanner);   
                case 3 -> DadosAgendamento.finalizar(scanner);
                case 4 -> DadosAgendamento.listar();
                case 5 -> DadosAgendamento.atualizarStatus(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu do módulo de ordens de serviço, permitindo criação,
     * atualização, finalização, cancelamento, listagem, vínculo de produtos e notas fiscais.
     */
    
    public static void menuOrdemServico(){
        int opcao;
        do {
            System.out.println("\n--- Ordem de Serviço ---");
            System.out.println("1. Criar Ordem de Serviço");
            System.out.println("2. Atualizar Ordem de Serviço");
            System.out.println("3. Finalizar Ordem de Serviço");
            System.out.println("4. Listar Ordem de Serviço");
            System.out.println("5. Cancelar Ordem de Serviço");
            System.out.println("6. Notas Fiscais");
            System.out.println("7. Vincular Produto a Ordem de Serviço");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {   
                case 1 -> DadosOrdemServico.cadastrar(scanner);   
                case 2 -> DadosOrdemServico.atualizar(scanner);   
                case 3 -> DadosOrdemServico.finalizar(scanner);
                case 4 -> DadosOrdemServico.listar();
                case 5 -> DadosOrdemServico.cancelar(scanner);
                case 6 -> menuNotasFiscais();
                case 7 -> {
                    System.out.print("Digite o ID da Ordem de Serviço para adicionar produtos: ");
                    DadosOrdemServico.listar();
                    int idOrdem = scanner.nextInt();
                    scanner.nextLine();
                    DadosOrdemServico.adicionarProdutosNaOrdem(idOrdem, scanner);
                }
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu de notas fiscais, permitindo criação, listagem e impressão.
     */
    
    public static void menuNotasFiscais(){
        int opcao;
        do{
            System.out.println("\n--- Notas Fiscais ---");
            System.out.println("1. Criar Nota Fiscal de Serviço");
            System.out.println("2. Listar Nota Fiscal de Serviço");
            System.out.println("3. Imprimir Nota Fiscal");
            System.out.println("0. Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao){
                case 1 -> DadosNotaFiscal.criarNF(scanner);
                case 2 -> DadosNotaFiscal.listar();
                case 3 -> DadosNotaFiscal.imprimirNF(scanner);
                case 0 -> {}
                default ->System.out.println("Opção Inválida");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu financeiro, permitindo gerenciar despesas, gerar balanço mensal
     * e relatórios de vendas e serviços.
     */
    
    public static void menuFinanceiro() {
        int opcao;
        do {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1. Menu Despesas");
            System.out.println("2. Relatorio balanco mensal");
            System.out.println("3. Relatorio Vendas e Servicos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuDespesas();
                case 2 -> {
                    System.out.print("Digite o mês (1-12): ");
                    int mes = scanner.nextInt();
                    System.out.print("Digite o ano (ex: 2025): ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();
                    DadosDespesas.gerarBalancoMensal(mes, ano);;
                }
                case 3 -> DadosVendas.gerarRelatorioVendas();
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu para vendas e serviços, permitindo registrar vendas e excluir vendas.
     */
    
    public static void menuVendasServicos(){
        int opcao;
        do {
            System.out.println("\n--- VENDAS E SERVICOS ---");
            System.out.println("1. Registrar Venda");
            System.out.println("2. Remover Venda");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosVendas.registrarVenda(scanner);
                case 2 -> DadosVendas.excluirVenda(scanner);
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu para despesas, incluindo cadastro, exclusão, edição e listagem.
     */
    
    public static void menuDespesas() {
        int opcao;
        do {
            System.out.println("\n--- Despesas ---");
            System.out.println("1. Adicionar Despesas");
            System.out.println("2. Excluir Despesas");
            System.out.println("3. Editar Despesas");
            System.out.println("4. Listar Despesas");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosDespesas.adicionarDespesa(scanner);
                case 2 -> DadosDespesas.excluirDespesa(scanner);
                case 3 -> DadosDespesas.editar(scanner);
                case 4 -> DadosDespesas.listar();
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu administrativo para o usuário admin ou proprietário, com opções para
     * alteração de senha e inicialização de elevadores.
     * 
     * @param usuario Funcionário logado.
     */

    public static void menuAdmin(Funcionario usuario) {
        int opcao;
        do {
            System.out.println("\n--- ADMIN ---");
            System.out.println("1. Alterar senha");
            System.out.println("2. Inicializar elevadores");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosFuncionario.alterarSenha(usuario, scanner);
                case 2 -> DadosElevador.inicilizarElevadores();
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu para gerenciamento de veículos, incluindo cadastro, edição,
     * exclusão, vinculação e desvinculação de cliente.
     */

    public static void menuVeiculo(){
        int opcao;
        do {
            System.out.println("\n--- VEÍCULO ---");
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Editar veículo");
            System.out.println("3. Excluir veículo");
            System.out.println("4. Listar veículos");
            System.out.println("5. Atribuir veículo a cliente");
            System.out.println("6. Desvincular veículo do cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {   
                case 1 -> DadosVeiculo.cadastrar(scanner);   
                case 2 -> DadosVeiculo.editar(scanner);   
                case 3 -> DadosVeiculo.excluir(scanner);
                case 4 -> DadosVeiculo.listar();
                case 5 -> DadosVeiculo.atribuirCliente(scanner);
                case 6 -> DadosVeiculo.desvincularCliente(scanner);
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu para gerenciamento de elevadores, permitindo atribuição e desvinculação
     * de ordens de serviço, além de listagem.
     */

    public static void menuElevador(){
        int opcao;
        do {
            System.out.println("\n--- ELEVADOR ---");
            System.out.println("1. Atribuir OS a elevador");
            System.out.println("2. Desvincular OS do elevador");
            System.out.println("3. Listar elevadores");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {   
                case 1 -> DadosElevador.atribuirOrdemServico(scanner);  
                case 2 -> DadosElevador.desvincularOrdemServico(scanner);  
                case 3 -> DadosElevador.listar();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    /**
     * Exibe o menu de ponto (controle de jornada), permitindo abertura,
     * fechamento e listagem de pontos para o proprietário.
     * 
     * @param usuario Funcionário logado.
     */

    public static void menuPonto(Funcionario usuario){
        int opcao;
        boolean usuarioProprietario = usuario.getCargo().equals("Proprietario");
        do {

            System.out.println("\n--- PONTO ---");
            System.out.println("1. Abrir ponto");
            System.out.println("2. Fechar ponto");
            if(usuarioProprietario) {
                System.out.println("3. Listar pontos");
            }
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {   
                case 1 -> DadosPonto.abrirPonto(usuario);
                case 2 -> DadosPonto.fecharPonto(usuario);
                case 3 -> {
                    if (usuarioProprietario){
                        DadosPonto.listarPontosPorUsuario(scanner);
                    } else {
                        System.out.println("Opção Inválida");
                    }
                }
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}

