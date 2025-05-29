package com.sistemaoficina.main;

import java.util.Scanner;

import com.sistemaoficina.dados.DadosAgendamento;
import com.sistemaoficina.dados.DadosClientes;
import com.sistemaoficina.dados.DadosFuncionario;
import com.sistemaoficina.dados.DadosProduto;
import com.sistemaoficina.dto.Funcionario;

public class SistemaOficina {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Funcionario funcionarioLogado = DadosFuncionario.realizaLogin(scanner);
        if (funcionarioLogado != null) {
            menuPrincipal(funcionarioLogado);
        } else {
            System.out.println("Não foi possível autenticar, encerrando o programa.");
        }
        scanner.close();
    }

    public static void menuPrincipal(Funcionario usuario) {
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            boolean usuarioPropietario = usuario.getCargo().equals("Proprietario");
            boolean usuarioAdmin = usuario.getCargo().equals("Admin");
            if (usuarioPropietario || usuarioAdmin) {
                System.out.println("1. Menu Cliente");
                System.out.println("2. Menu Produto");
                System.out.println("3. Menu Agendamento");
                System.out.println("4. Menu Funcionario");
                System.out.println("5. Menu Admin");
            if(usuarioPropietario) System.out.println("6. Menu Financeiro");
            } else {
                System.out.println("1. Menu Cliente");
                System.out.println("2. Menu Produto");
                System.out.println("3. Menu Agendamento");
            }

            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    menuCliente();
                }
                case 2 -> {
                    menuProduto();
                }
                case 3 -> {
                    menuAgendamento();
                }
                case 4 -> {
                    if(usuarioPropietario || usuarioAdmin) {
                        menuFuncionario();
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                
                case 5 -> {
                    if (usuarioPropietario || usuarioAdmin){
                        menuAdmin(usuario);
                    } else {
                        System.out.println("Opção Inválida");
                    }
                } 
                case 6 -> {
                    if(usuarioPropietario) {
                        menuFinanceiro();
                    } else if(usuarioAdmin) {
                        menuAdmin(usuario);
                    } else {
                        System.out.println("Opção inválida.");
                    }
                }
                case 0 -> System.err.println("SAINDO!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void menuCliente(){
        int opcao;
        do {
            System.out.println("\n--- CLIENTE ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Excluir Cliente");
            System.out.println("4. Listar Clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosClientes.cadastrar(scanner);
                case 2 -> DadosClientes.editar(scanner);
                case 3 -> DadosClientes.excluir(scanner);
                case 4 -> DadosClientes.listar();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    public static void menuFuncionario(){
        int opcao;
        do {
            System.out.println("\n--- FUNCIONARIO ---");
            System.out.println("1. Cadastrar Funcionario");
            System.out.println("2. Editar Funcionario");
            System.out.println("3. Excluir Funcionario");
            System.out.println("4. Listar Funcionarios");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {                
                case 1 -> DadosFuncionario.cadastrar(scanner);
                case 2 -> DadosFuncionario.editar(scanner);
                case 3 -> DadosFuncionario.excluir(scanner);
                case 4 -> DadosFuncionario.listar();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

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

    public static void menuFinanceiro() {
        int opcao;
        do {
            System.out.println("\n--- FINANCEIRO ---");
            System.out.println("1. Relatorio balanco mensal");
            System.out.println("2. Relatorio de vendas e servicos");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> System.out.println("Função de geração de relatório será implementada futuramente.");
                case 2 -> System.out.println("Função de geração de relatório será implementada futuramente.");
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    public static void menuAdmin(Funcionario usuario) {
        int opcao;
        do {
            System.out.println("\n--- ADMIN ---");
            System.out.println("1. Alterar senha");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> DadosFuncionario.alterarSenha(usuario, scanner);
                case 0 -> {
                }
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

}