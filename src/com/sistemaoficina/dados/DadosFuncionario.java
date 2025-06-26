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
import com.sistemaoficina.dto.Funcionario;
import java.util.Iterator;

/**
 * Classe utilitária responsável pelo gerenciamento dos dados dos funcionários,
 * incluindo cadastro, edição, exclusão, autenticação, listagem e operações auxiliares.
 * <p>
 * Os dados são persistidos em arquivo JSON.
 * </p>
 *
 * @author Riquelme Moreira Campos
 * @version 1.0
 */
public class DadosFuncionario {

    /** Caminho do arquivo JSON que armazena os funcionários. */
    private static final String ARQUIVO_FUNCIONARIOS = "bd/funcionarios.json";

    /** Lista global de funcionários carregados na memória. */
    public static ArrayList<Funcionario> listaFuncionarios = carregarFuncionarios();

    /**
     * Salva a lista atual de funcionários no arquivo JSON.
     */
    public static void salvarFuncionariosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_FUNCIONARIOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaFuncionarios, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionários: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de funcionários do arquivo JSON.
     * Se não existir, retorna uma lista vazia.
     *
     * @return Lista de funcionários carregada ou vazia.
     */
    public static ArrayList<Funcionario> carregarFuncionarios() {
        try (FileReader reader = new FileReader(ARQUIVO_FUNCIONARIOS)) {
            Type listaTipo = new TypeToken<ArrayList<Funcionario>>(){}.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de funcionarios não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    /**
     * Realiza a autenticação de um funcionário pelo usuário e senha.
     *
     * @param usuario Nome de usuário.
     * @param senha Senha do funcionário.
     * @return Funcionário autenticado ou null se inválido.
     */
    public static Funcionario autenticarFuncionario(String usuario, String senha) {
        for (Funcionario f : listaFuncionarios) {
            if (f.validarCredenciais(usuario, senha)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Realiza o cadastro de um novo funcionário.
     *
     * @param scanner Scanner para entrada de dados do usuário.
     */
    public static void cadastrar(Scanner scanner) {
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
        double salario = Double.parseDouble(scanner.nextLine());
        Funcionario funcionario = new Funcionario(nome, cargo, usuario, senha, salario);
        OptionalInt maxId = listaFuncionarios.stream()
            .mapToInt(Funcionario::getId)
            .max();
        funcionario.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);
        listaFuncionarios.add(funcionario);
        salvarFuncionariosJson();
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    /**
     * Edita os dados de um funcionário já cadastrado.
     *
     * @param scanner Scanner para entrada de dados do usuário.
     */
    public static void editar(Scanner scanner) {
        System.out.println("Escolha um funcionario por Id:");
        listar();
        if(listaFuncionarios.isEmpty()) return;
        int idFuncionario = Integer.parseInt(scanner.nextLine());
        Funcionario funcionario = buscarId(idFuncionario);
        if (funcionario == null) {
            System.out.println("Funcionário não existente!");
            return;
        }        
        System.out.println("Digite as informações do funcionario: ");
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
        double salario = Double.parseDouble(scanner.nextLine());
        Funcionario novoFuncionario = new Funcionario(nome, cargo, usuario, senha, salario);
        novoFuncionario.setId(funcionario.getId());
        int index = listaFuncionarios.indexOf(funcionario);
        if (index != -1) {
            listaFuncionarios.set(index, novoFuncionario);
        }
        salvarFuncionariosJson();
        System.out.println("Funcionário editado com sucesso.");
    }

    /**
     * Lista todos os funcionários cadastrados.
     */
    public static void listar() {
        if(listaFuncionarios.isEmpty()){
            System.out.println("Não há funcionarios para serem listados!");
        }
        for (Funcionario f : listaFuncionarios) {
            System.out.println("Id: " + f.getId() + " - " + f.getNome());
        }
    }

    /**
     * Busca um funcionário pelo ID.
     *
     * @param id ID do funcionário.
     * @return Funcionário encontrado ou null.
     */
    public static Funcionario buscarId(int id) {
        for(Funcionario f : listaFuncionarios){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }

    /**
     * Busca um funcionário pelo ID usando Iterator.
     * 
     * @param sc Scanner para entrada do usuário.
     */
    public static void buscarIdIterator(Scanner sc){
        System.out.println("Digite o ID que deseja buscar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Iterator<Funcionario> iterator = listaFuncionarios.iterator();
        while (iterator.hasNext()){
            Funcionario func = iterator.next();
            if(func.getId() == id){
                System.out.println("Nome: " + func.getNome() + " | Cargo: " + func.getCargo());
            }
        }
    }

    /**
     * Busca um funcionário pelo ID usando forEach.
     *
     * @param sc Scanner para entrada do usuário.
     */
    public static void buscarIdForEach(Scanner sc){
        System.out.println("Digite o ID que deseja buscar: ");
        int id = sc.nextInt();
        sc.nextLine();
        listaFuncionarios.forEach(f -> {
            if(f.getId() == id){
                System.out.println("Nome: " + f.getNome() + " | Cargo " + f.getCargo());
            }
        });
    }

    /**
     * Exclui um funcionário do sistema.
     * 
     * @param scanner Scanner para entrada do usuário.
     */
    public static void excluir(Scanner scanner) {
        listar();
        if(listaFuncionarios.isEmpty()) return;
        System.out.print("Digite o número do funcionario que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Funcionario f = buscarId(indice);
        if(f == null){
            System.out.println("Funcionario não existente!");
            return;
        }
        listaFuncionarios.remove(f);
        salvarFuncionariosJson();        
        System.out.println("Funcionario excluido com sucesso.");
    }

    /**
     * Realiza o login de funcionário pelo console.
     * 
     * @param scanner Scanner para entrada do usuário.
     * @return Funcionário autenticado ou null.
     */
    public static Funcionario realizaLogin(Scanner scanner){
        System.out.println("===== LOGIN =====");
        System.out.print("Usuário: ");
        String usuarioInput = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaInput = scanner.nextLine();
        return autenticarFuncionario(usuarioInput, senhaInput);
    }

    /**
     * Altera a senha de um funcionário já cadastrado.
     * 
     * @param usuario Funcionário que terá a senha alterada.
     * @param scanner Scanner para entrada do usuário.
     */
    public static void alterarSenha(Funcionario usuario, Scanner scanner){
        System.out.println("Digite a nova senha do usuario: " + usuario.getNome());        
        String senha = scanner.nextLine();
        int index = listaFuncionarios.indexOf(usuario);
        usuario.setSenha(senha);
        listaFuncionarios.set(index, usuario);
        salvarFuncionariosJson();
        System.out.println("Senha alterada com sucesso!");
    }
}
