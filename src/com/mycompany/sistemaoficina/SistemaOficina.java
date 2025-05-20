package com.mycompany.sistemaoficina;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.sistemaoficinamecanica.Agendamento;
import com.mycompany.sistemaoficinamecanica.ArquivoUtils;
import static com.mycompany.sistemaoficinamecanica.ArquivoUtils.carregarFuncionarios;
import com.mycompany.sistemaoficinamecanica.Clientes;
import com.mycompany.sistemaoficinamecanica.Funcionario;
import com.mycompany.sistemaoficinamecanica.UsuarioManager;
import com.mycompany.sistemaoficinamecanica.Veiculo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemaOficina {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Clientes> listaClientes = ArquivoUtils.carregarClientes();
    private static ArrayList<String> despesasFuncionarios = new ArrayList<>();
    private static ArrayList<String> despesasPecas = new ArrayList<>();
    private static ArrayList<String> despesasOficina = new ArrayList<>();
    private static final String ARQUIVO_CLIENTES = "clientes.json";
    private static final String ARQUIVO_FUNCIONARIOS = "funcionarios.json";
    private static final String ARQUIVO_AGENDAMENTOS = "agendamentos.json";
    private static ArrayList<Funcionario> listaFuncionarios = ArquivoUtils.carregarFuncionarios();
    private static ArrayList<Agendamento> listaAgendamentos = ArquivoUtils.carregarAgendamentos();
    
    public static void main(String[] args) {
        listaFuncionarios = carregarFuncionarios();
        Usuario usuario = login();
        if (usuario != null) {
            menuPrincipal(usuario);
        } else {
            System.out.println("Encerrando o programa.");
        }
    }

    public static Usuario login() {
    System.out.println("===== LOGIN =====");
    System.out.print("Usuário: ");
    String usuarioInput = scanner.nextLine();
    System.out.print("Senha: ");
    String senhaInput = scanner.nextLine();

    // Admin e Proprietário fixos
    if (usuarioInput.equals("admin") && senhaInput.equals("1234")) {
        return new Usuario("Administrador", "admin");
    } else if (usuarioInput.equals("proprietario") && senhaInput.equals("0000")) {
        return new Usuario("Proprietário", "proprietario");
    }

    // Tentar autenticar como funcionário
    UsuarioManager usuarioManager = new UsuarioManager();
    Funcionario funcionario = usuarioManager.autenticarFuncionario(usuarioInput, senhaInput);

    if (funcionario != null) {
        return new Usuario(funcionario.getNome(), funcionario.getCargo());
    }

    System.out.println("Usuário ou senha incorretos.");
    return null;
}

public static void menuPrincipal(Usuario usuario) {
    int opcao;
    do {
        System.out.println("\n===== MENU PRINCIPAL =====");
        if (usuario.isProprietario()) {
            System.out.println("1. Cadastro");
            System.out.println("2. Consultas");
            System.out.println("3. Setor Financeiro");
            System.out.println("4. Agendamentos");

        }
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                if (usuario.isProprietario()) {
                    menuCadastroCompleto();
                }
            }
            case 2 -> {
                if (usuario.isProprietario()) {
                    menuConsultas();
                }
            }
            case 3 -> {
                if (usuario.isProprietario()) {
                    menuFinanceiro();
                }
            }
            case 4 -> {
                if (usuario.isProprietario()){
                    menuAgendamentos();
                }
            }
            case 0 -> salvarDadosJson();
            default -> System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}

public static void menuCadastroCompleto() {
    int opcao;
    do {
        System.out.println("\n--- CADASTRO ---");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Veículo");
        System.out.println("3. Cadastrar Funcionário");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> cadastrarVeiculo();
            case 3 -> cadastrarFuncionario();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}

public static void menuConsultas() {
    int opcao;
    do {
        System.out.println("\n--- CONSULTAS ---");
        System.out.println("1. Consultar Clientes e Veículos");
        System.out.println("2. Consultar Funcionários");
        System.out.println("3. Consultar Ordem de Serviço");
        System.out.println("4. Buscar Clientes");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> listarClientes();
            case 2 -> listarFuncionarios();
            case 3 -> consultarOrdensDeServico();
            case 4 -> buscarCliente();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}

    public static void menuCadastro() {
        int opcao;
        do {
            System.out.println("\n--- CADASTRO ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Cadastrar veículo");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> cadastrarVeiculo();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
    public static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("CPF (pseudo-anonimizado): ");
        String cpf = scanner.nextLine();

        Clientes cliente = new Clientes(nome, endereco, telefone, email, cpf);
        listaClientes.add(cliente);
        System.out.println("Cliente cadastrado com sucesso.");
    }

    public static void cadastrarVeiculo() {
        if (listaClientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        listarClientesSimples();
        System.out.print("Escolha o cliente que deseja adicionar veículo: ");
        int indice = scanner.nextInt();
        scanner.nextLine();

        if (indice >= 0 && indice < listaClientes.size()) {
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Placa: ");
            String placa = scanner.nextLine();
            System.out.print("Cor: ");
            String cor = scanner.nextLine();
            System.out.print("Ano: ");
            int ano = scanner.nextInt();
            scanner.nextLine();
            String combustivel = selecionarCombustivel(scanner);

            Veiculo veiculo = new Veiculo(placa, modelo, ano, cor, combustivel);
            listaClientes.get(indice).adicionarVeiculo(veiculo);
            System.out.println("Veículo adicionado.");
        } else {
            System.out.println("Índice inválido.");
        }
    } 
    public static String selecionarCombustivel(Scanner scanner) {
        System.out.println("Selecione o tipo de combustível:");
        System.out.println("1 - Diesel");
        System.out.println("2 - Álcool");
        System.out.println("3 - Gasolina Comum");
        System.out.println("4 - Gasolina Aditivada");
        System.out.println("5 - Outro");
        System.out.println("6 - Carro Elétrico");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        return switch (opcao) {
            case 1 -> "Diesel";
            case 2 -> "Álcool";
            case 3 -> "Gasolina Comum";
            case 4 -> "Gasolina Aditivada";
            case 5 -> {
                System.out.print("Digite o tipo de combustível: ");
                yield scanner.nextLine();
            }
            case 6 -> "Carro Elétrico";
            default -> "Não especificado";
        };
    }

    public static void listarClientesSimples() {
        for (int i = 0; i < listaClientes.size(); i++) {
            System.out.println(i + " - " + listaClientes.get(i).getNome());
        }
    }
 
    public static void listarClientes() {
    if (listaClientes.isEmpty()) {
        System.out.println("Nenhum cliente para listar.");
        return;
    }

    for (int i = 0; i < listaClientes.size(); i++) {
        System.out.println(i + " - " + listaClientes.get(i));
    }

    System.out.print("\nDeseja excluir algum cliente? (S/N): ");
    String resposta = scanner.nextLine();
    if (resposta.equalsIgnoreCase("S")) {
        System.out.print("Digite o número do cliente que deseja excluir: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        if (indice >= 0 && indice < listaClientes.size()) {
            listaClientes.remove(indice);
            salvarDadosJson();
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Índice inválido.");
        }
    }
}
    
    public static void buscarCliente(){
        System.out.println("Digite o nome ou parte do nome do cliente: ");
        String termo = scanner.nextLine().toLowerCase();
        
        boolean encontrado = false;
        for (int i = 0; i < listaClientes.size(); i++){
            Clientes cliente = listaClientes.get(i);
            if(cliente.getNome().toLowerCase().contains(termo)){
                System.out.println("Cliente Encontrado: ");
                System.out.println(i + " - " + cliente);
                encontrado = true;
            }
        }
        
        if (!encontrado){
            System.out.println("Nenhum cliente encontrado com esse nome.");
        }
    }

    public static void cadastrarFuncionario() {
        System.out.print("Nome do Funcionário: ");
        String nome = scanner.nextLine();

        System.out.println("Escolha o cargo do funcionário:");
        System.out.println("1 - Mecânico Geral");
        System.out.println("2 - Mecânico Especialista");
        System.out.println("3 - Atendente");
        System.out.println("4 - Repositor");

        int cargoEscolhido = scanner.nextInt();
        scanner.nextLine();
        String cargo = switch (cargoEscolhido) {
            case 1 -> "Mecânico Geral";
            case 2 -> "Mecânico Especialista";
            case 3 -> "Atendente";
            case 4 -> "Repositor";
            default -> "Não especificado";
        };

        System.out.print("Usuário (login): ");
        String usuario = scanner.nextLine();

        System.out.print("Senha para o login: ");
        String senha = scanner.nextLine();

        System.out.print("Salário: R$ ");
        double salario = scanner.nextDouble();
        scanner.nextLine();

        Funcionario funcionario = new Funcionario(nome, cargo, usuario, senha, salario);
        listaFuncionarios.add(funcionario);
        salvarFuncionariosJson();
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    public static void listarFuncionarios() {
    if (listaFuncionarios.isEmpty()) {
        System.out.println("Nenhum funcionário cadastrado.");
        return;
    }

    for (int i = 0; i < listaFuncionarios.size(); i++) {
        Funcionario f = listaFuncionarios.get(i);
        System.out.println(i + " - " + f.getNome() + " | " + f.getCargo() + " | Usuário: " + f.getUsuario() + " | Salário: R$ " + f.getSalario());
    }

    System.out.print("\nDeseja excluir algum funcionário? (S/N): ");
    String resposta = scanner.nextLine();
    if (resposta.equalsIgnoreCase("S")) {
        System.out.print("Digite o número do funcionário que deseja excluir: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        if (indice >= 0 && indice < listaFuncionarios.size()) {
            listaFuncionarios.remove(indice);
            salvarFuncionariosJson();
            System.out.println("Funcionário removido com sucesso.");
        } else {
            System.out.println("Índice inválido.");
        }
    }
}
    
public static void menuAgendamentos() {
    int opcao;
    do {
        System.out.println("\n--- MENU AGENDAMENTOS ---");
        System.out.println("1. Agendar Serviço");
        System.out.println("2. Cancelar Agendamento");
        System.out.println("3. Finalizar Agendamento");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");
        opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> agendarServico();
            case 2 -> cancelarAgendamento();
            case 3 -> finalizarAgendamento();
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    } while (opcao != 0);
}
    
    public static void salvarFuncionariosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_FUNCIONARIOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaFuncionarios, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionários: " + e.getMessage());
        }
    }

    public static void salvarDadosJson() {
    try (FileWriter writerClientes = new FileWriter(ARQUIVO_CLIENTES)) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(listaClientes, writerClientes);
        
        salvarFuncionariosJson();
        
        System.out.println("Clientes, Funcionários e Agendamentos salvos com sucesso em JSON.");
    } catch (IOException e) {
        System.out.println("Erro ao salvar dados: " + e.getMessage());
    }
}
    
    public static void salvarAgendamentosJson() {
    try (FileWriter writer = new FileWriter(ARQUIVO_AGENDAMENTOS)) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(listaAgendamentos, writer);
        
        System.out.println("Agendamentos salvos com sucesso em JSON.");
    } catch (IOException e) {
        System.out.println("Erro ao salvar agendamentos: " + e.getMessage());
    }
}

    public static void consultarAgendamentos() {
    if (listaAgendamentos.isEmpty()) {
        System.out.println("Nenhum agendamento encontrado.");
        return;
    }

    for (int i = 0; i < listaAgendamentos.size(); i++) {
        Agendamento ag = listaAgendamentos.get(i);
        System.out.println(i + " - Ordem de Serviço #" + ag.getId());
        System.out.println(ag);
        System.out.println("-------------------------");
    }

    System.out.print("Deseja finalizar alguma ordem de serviço? (S/N): ");
    String resposta = scanner.nextLine();
    if (resposta.equalsIgnoreCase("S")) {
        System.out.print("Digite o número da ordem: ");
        int indice = scanner.nextInt();
        scanner.nextLine();
        if (indice >= 0 && indice < listaAgendamentos.size()) {
            Agendamento ag = listaAgendamentos.get(indice);
            if (!ag.isFinalizado()) {
                ag.finalizar();
                salvarAgendamentosJson();
                System.out.println("Ordem de serviço finalizada com sucesso.");
            } else {
                System.out.println("Essa ordem já está finalizada.");
            }
        } else {
            System.out.println("Índice inválido.");
        }
    }
}
    
    public static void menuFinanceiro() {
        int opcao;
        do {
            System.out.println("\n=== SETOR FINANCEIRO ===");
            System.out.println("1. Balanço Fiscal (gerar relatório)");
            System.out.println("2. Controle de Despesas");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> System.out.println("Função de geração de relatório será implementada futuramente.");
                case 2 -> menuControleDespesas();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    public static void menuControleDespesas() {
        int opcao;
        do {
            System.out.println("\n--- CONTROLE DE DESPESAS ---");
            System.out.println("1. Lançar Despesas");
            System.out.println("2. Consultar Despesas");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> lancarDespesas();
                case 2 -> consultarDespesas();
                case 0 -> {}
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
    
  public static void lancarDespesas() {
    System.out.println("\n--- LANÇAR DESPESAS ---");
    System.out.println("1. Funcionários");
    System.out.println("2. Peças");
    System.out.println("3. Oficina");
    System.out.print("Escolha: ");
    int tipo = scanner.nextInt();
    scanner.nextLine();

    switch (tipo) {
        case 1 -> {
            // Listar os funcionários cadastrados
            listarFuncionarios();
            System.out.print("Escolha o funcionário pelo índice: ");
            int indice = scanner.nextInt();
            scanner.nextLine();

            if (indice >= 0 && indice < listaFuncionarios.size()) {
                Funcionario funcionarioEscolhido = listaFuncionarios.get(indice);
                System.out.print("Valor da despesa: ");
                double valor = scanner.nextDouble();
                scanner.nextLine();
                despesasFuncionarios.add(funcionarioEscolhido.getNome() + " - " + funcionarioEscolhido.getCargo() + " - R$ " + valor);
                System.out.println("Despesa de funcionário registrada.");
            } else {
                System.out.println("Índice inválido.");
            }
        }
        case 2 -> {
            System.out.println("Peça:");
            System.out.println("1 - Motor");
            System.out.println("2 - Suspensão");
            System.out.println("3 - Freios");
            System.out.println("4 - Sistema de Escape");
            System.out.println("5 - Elétricas");
            System.out.println("6 - Chassi");
            int peca = scanner.nextInt();
            scanner.nextLine();
            String pecaStr = switch (peca) {
                case 1 -> "Motor";
                case 2 -> "Suspensão";
                case 3 -> "Freios";
                case 4 -> "Sistema de Escape";
                case 5 -> "Elétricas";
                case 6 -> "Chassi";
                default -> "Peça Não Especificada";
            };
            System.out.print("Nome da peça: ");
            String nomePeca = scanner.nextLine();
            System.out.print("Quantidade: ");
            int qtd = scanner.nextInt();
            System.out.print("Valor unitário (R$): ");
            int valor = scanner.nextInt();
            scanner.nextLine();
            despesasPecas.add("Peça: " + nomePeca + " | Tipo: " + pecaStr + " | Quantidade: " + qtd + " | Valor: R$" + valor);
            System.out.println("Despesa de peça registrada.");
        }
        case 3 -> {
            System.out.println("Digite o tipo de despesa da oficina:");
            System.out.println("1 - Água");
            System.out.println("2 - Luz");
            System.out.println("3 - Internet");
            int tipoOficina = scanner.nextInt();
            scanner.nextLine();
            String tipoDespesa = switch (tipoOficina) {
                case 1 -> "Água";
                case 2 -> "Luz";
                case 3 -> "Internet";
                default -> "Outros";
            };
            System.out.print("Valor: ");
            double valorOficina = scanner.nextDouble();
            despesasOficina.add(tipoDespesa + " - R$ " + valorOficina);
        }
    }
} 
    public static void consultarDespesas() {
    System.out.println("\n=== CONSULTAR DESPESAS ===");

    System.out.println("Despesas de Funcionários: ");
    despesasFuncionarios.forEach(System.out::println);

    System.out.println("Despesas de Peças: ");
    despesasPecas.forEach(System.out::println);

    System.out.println("Despesas de Oficina: ");
    despesasOficina.forEach(System.out::println);

    double total = 0;
    for (String despesa : despesasFuncionarios) {
        String[] partes = despesa.split(" - R\\$ ");
        if (partes.length == 2) {
            total += Double.parseDouble(partes[1]);
        }
    }
    for (String despesa : despesasPecas) {
        try {
            // Exemplo: "Peça: pastilha | Tipo: Freios | Quantidade: 2 | Valor: R$50"
            String[] partes = despesa.split("\\|");
            int quantidade = 1;
            double valorUnitario = 0;

            for (String parte : partes) {
                parte = parte.trim();
                if (parte.startsWith("Quantidade:")) {
                    quantidade = Integer.parseInt(parte.replace("Quantidade:", "").trim());
                }
                if (parte.startsWith("Valor: R$")) {
                    valorUnitario = Double.parseDouble(parte.replace("Valor: R$", "").trim());
                }
            }
            total += quantidade * valorUnitario;
        } catch (Exception e) {
            System.out.println("Erro ao processar despesa de peça: " + despesa);
        }
    }
    for (String despesa : despesasOficina) {
        String[] partes = despesa.split(" - R\\$ ");
        if (partes.length == 2) {
            total += Double.parseDouble(partes[1]);
        }
    }
    System.out.printf("Total de despesas: R$ %.2f\n", total);
    }
    
    public static final ArrayList<Agendamento> agendamentos = new ArrayList<>();

public static void agendarServico() {
    if (listaClientes.isEmpty()) {
        System.out.println("Nenhum cliente cadastrado.");
        return;
    }
    
    salvarAgendamentosJson();


    listarClientesSimples();
    System.out.print("Selecione o cliente para agendamento: ");
    int indice = scanner.nextInt();
    scanner.nextLine();

    if (indice < 0 || indice >= listaClientes.size()) {
        System.out.println("Índice inválido.");
        return;
    }

    Clientes cliente = listaClientes.get(indice);
    if (cliente.getVeiculos().isEmpty()) {
        System.out.println("Cliente não tem veículos cadastrados.");
        return;
    }

    for (int i = 0; i < cliente.getVeiculos().size(); i++) {
        System.out.println(i + " - " + cliente.getVeiculos().get(i).getModelo() + " | Placa: " + cliente.getVeiculos().get(i).getPlaca());
    }

    System.out.print("Escolha o veículo para o agendamento: ");
    int veiculoIndex = scanner.nextInt();
    scanner.nextLine();

    if (veiculoIndex < 0 || veiculoIndex >= cliente.getVeiculos().size()) {
        System.out.println("Índice de veículo inválido.");
        return;
    }

    System.out.print("Data do agendamento (dd/mm/aaaa): ");
    String data = scanner.nextLine();
    System.out.print("Descrição do problema: ");
    String descricao = scanner.nextLine();
    System.out.print("Valor estimado do serviço: R$ ");
    double valor = scanner.nextDouble();
    scanner.nextLine();

    int novoId = 1000 + listaAgendamentos.size();
    Agendamento novo = new Agendamento(novoId, cliente.getNome(), cliente.getVeiculos().get(veiculoIndex).getPlaca(), data, descricao, valor);
    listaAgendamentos.add(novo);
    salvarAgendamentosJson();
    System.out.println("Agendamento realizado com sucesso!\n" + novo);
}
public static void cancelarAgendamento() {
    if (listaAgendamentos.isEmpty()) {
        System.out.println("Nenhum agendamento encontrado.");
        return;
    }

    for (int i = 0; i < listaAgendamentos.size(); i++) {
        Agendamento ag = listaAgendamentos.get(i);
        System.out.println(i + " - Ordem #" + ag.getId() + " | Cliente: " + ag.getNomeCliente() + " | Placa: " + ag.getPlacaVeiculo());
    }

    System.out.print("Digite o número do agendamento que deseja cancelar: ");
    int indice = scanner.nextInt();
    scanner.nextLine();

    if (indice >= 0 && indice < listaAgendamentos.size()) {
        Agendamento ag = listaAgendamentos.get(indice);
        if (!ag.isCancelado()) {
            ag.cancelar();
            salvarAgendamentosJson();
            System.out.println("Agendamento cancelado com 20% retido.");
        } else {
            System.out.println("Esse agendamento já está cancelado.");
        }
    } else {
        System.out.println("Índice inválido.");
    }
}

public static void finalizarAgendamento() {
    if (listaAgendamentos.isEmpty()) {
        System.out.println("Nenhuma ordem de serviço para finalizar.");
        return;
    }

    for (int i = 0; i < listaAgendamentos.size(); i++) {
        Agendamento ag = listaAgendamentos.get(i);
        System.out.println(i + " - Ordem #" + ag.getId() + " | Cliente: " + ag.getNomeCliente() + " | Placa: " + ag.getPlacaVeiculo());
    }

    System.out.print("Digite o número da ordem que deseja finalizar: ");
    int indice = scanner.nextInt();
    scanner.nextLine();

    if (indice >= 0 && indice < listaAgendamentos.size()) {
        listaAgendamentos.remove(indice);
        renumerarAgendamentos(); // reatribui os IDs
        salvarAgendamentosJson();
        System.out.println("Ordem de serviço finalizada e removida com sucesso.");
    } else {
        System.out.println("Índice inválido.");
    }
}
public static void renumerarAgendamentos() {
    for (int i = 0; i < listaAgendamentos.size(); i++) {
        listaAgendamentos.get(i).setId(1000 + i); // ID sequencial reiniciado
    }
}

public static void consultarOrdensDeServico(){
    if(listaAgendamentos.isEmpty()){
        System.out.println("Nenhuma ordem de serviço registrada");
        return;
    }
    System.out.println("\n=== ORDENS DE SERVIÇO ===");
    for (Agendamento ag : listaAgendamentos) {
        System.out.println(ag);
        System.out.println("----------------------------");
    }
}

}