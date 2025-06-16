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

public class DadosFuncionario {
    private static final String ARQUIVO_FUNCIONARIOS = "bd/funcionarios.json";

    public static ArrayList<Funcionario> listaFuncionarios = carregarFuncionarios();
    
    public static void salvarFuncionariosJson() {
        try (FileWriter writer = new FileWriter(ARQUIVO_FUNCIONARIOS)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaFuncionarios, writer);
        } catch (IOException e) {
            System.out.println("Erro ao salvar funcionários: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de funcionários a partir do arquivo JSON.
     * Caso o arquivo não exista, cria um novo arquivo vazio.
     * 
     * @return A lista de funcionários carregada do arquivo ou uma lista vazia se não houver dados.
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
     * Realiza a autenticação de um funcionário com base no nome de usuário e senha fornecidos.
     * 
     * @param usuario O nome de usuário do funcionário.
     * @param senha A senha do funcionário.
     * @return O objeto Funcionario autenticado, ou null se as credenciais forem inválidas.
     */
    public static Funcionario autenticarFuncionario(String usuario, String senha) {
        for (Funcionario f : listaFuncionarios) {
            if (f.validarCredenciais(usuario, senha)) {
                return f;
            }
        }
        return null;
    }

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

    public static void editar(Scanner scanner) {
        System.out.println("Escolha um funcionario por Id:");
        listar();
        if(listaFuncionarios.isEmpty()) return;
        int idFuncionario = Integer.parseInt(scanner.nextLine());
        Funcionario funcionario = buscarId(idFuncionario);
        if (funcionario == null) {
            System.out.println("Cliente não existente!");
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

    public static void listar() {
        if(listaFuncionarios.isEmpty()){
            System.out.println("Não há funcionarios para serem listados!");
        }
        for (Funcionario f : listaFuncionarios) {
            System.out.println("Id: " + f.getId() + " - " + f.getNome());
        }
    }

    public static Funcionario buscarId(int id) {
        for(Funcionario f : listaFuncionarios){
            if(f.getId() == id){
                return f;
            }
        }
        return null;
    }
    
    //Questão 15
    
    public static void buscarIdIterator(Scanner sc){
        System.out.println("Digite o ID que deseja buscar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Iterator<Funcionario> iterator = listaFuncionarios.iterator();
        while (iterator.hasNext()){
            Funcionario func = iterator.next();
            if(func.getId() == id){
                System.out.println(func.getNome() + " " + func.getCargo());
            }
        }
    }
    
    
    public static void buscarIdForEach(Scanner sc){
        System.out.println("Digite o ID que deseja buscar: ");
        int id = sc.nextInt();
        sc.nextLine();
        
        listaFuncionarios.forEach(f -> {
            if(f.getId() == id){
                System.out.println(f.getNome() + " " + f.getCargo());
            }
        });
    }
    
    //final questão 15

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

    public static Funcionario realizaLogin(Scanner scanner){
        System.out.println("===== LOGIN =====");
        System.out.print("Usuário: ");
        String usuarioInput = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaInput = scanner.nextLine();

        return autenticarFuncionario(usuarioInput, senhaInput);
    }

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
