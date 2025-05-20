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
import com.sistemaoficina.dto.Veiculo;

public class DadosClientes {
    private static final String ARQUIVO_CLIENTES = "clientes.json";

    public static ArrayList<Cliente> listaClientes = carregarClientes();

    /**
     * Carrega a lista de clientes a partir do arquivo JSON.
     * Caso o arquivo não exista ou ocorra um erro na leitura, retorna uma lista
     * vazia.
     * 
     * @return A lista de clientes carregada do arquivo ou uma lista vazia se
     *         ocorrer erro.
     */
    public static ArrayList<Cliente> carregarClientes() {
        try (FileReader reader = new FileReader(ARQUIVO_CLIENTES)) {
            Type listaTipo = new TypeToken<ArrayList<Cliente>>() {
            }.getType();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(reader, listaTipo);
        } catch (IOException e) {
            System.out.println("Arquivo de clientes não encontrado. Lista iniciada vazia.");
            return new ArrayList<>();
        }
    }

    public static void salvarClientesJson() {
        try (FileWriter writerClientes = new FileWriter(ARQUIVO_CLIENTES)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listaClientes, writerClientes);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static void cadastrar(Scanner scanner) {
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

        Cliente cliente = new Cliente(nome, endereco, telefone, email, cpf);
        cadastrarVeiculo(cliente, scanner);
        OptionalInt maxId = listaClientes.stream()
            .mapToInt(Cliente::getId)
            .max();
        cliente.setId(maxId.isPresent() ? maxId.getAsInt() + 1 : 0);
        listaClientes.add(cliente);
        salvarClientesJson();
        System.out.println("Cliente cadastrado com sucesso.");
    }

    public static void cadastrarVeiculo(Cliente cliente, Scanner scanner) {
        System.out.println("Cadastrar veiculo");
        System.out.print("Modelo do veiculo: ");
        String modelo = scanner.nextLine();
        System.out.print("Placa do veiculo: ");
        String placa = scanner.nextLine();
        System.out.print("Cor do veiculo: ");
        String cor = scanner.nextLine();
        System.out.print("Ano do veiculo: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        String combustivel = selecionarCombustivel(scanner);

        Veiculo veiculo = new Veiculo(placa, modelo, ano, cor, combustivel);
        cliente.adicionarVeiculo(veiculo);
    }

    public static String selecionarCombustivel(Scanner scanner) {
        System.out.println("Selecione o tipo de combustível do veiculo:");
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

    public static void editar(Scanner scanner){
        System.out.println("Escolha um cliente por Id:");
        listar();
        if(listaClientes.isEmpty()) return;
        int idCliente = Integer.parseInt(scanner.nextLine());
        Cliente cliente = buscarId(idCliente);
        if(cliente == null){
            System.out.println("Cliente não existente!");
            return;
        }
        System.out.println("Digite as informações do cliente: ");
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

        Cliente novoCliente = new Cliente(nome, endereco, telefone, email, cpf);
        novoCliente.setId(cliente.getId());
        cadastrarVeiculo(novoCliente, scanner);

        int index = listaClientes.indexOf(cliente);

        if (index != -1) {
            listaClientes.set(index, novoCliente);
        }

        salvarClientesJson();
        System.out.println("Cliente editado com sucesso.");
        

    }

    public static void listar() {
        if(listaClientes.isEmpty()){
            System.out.println("Não há clientes para serem listados!");
        }
        for (Cliente c : listaClientes) {
            System.out.println("Id: " + c.getId() + " - " + c.getNome());
        }
    }

    public static Cliente buscarId(int id) {
        for(Cliente c : listaClientes){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }


    public static void excluir(Scanner scanner) {
        listar();
        if(listaClientes.isEmpty()) return;
        System.out.print("Digite o número do cliente que deseja excluir: ");
        int indice = Integer.parseInt(scanner.nextLine());
        Cliente c = buscarId(indice);
        if(c == null){
            System.out.println("Cliente não existente!");
            return;
        }

        listaClientes.remove(c);
        salvarClientesJson();
        
        System.out.println("Cliente excluido com sucesso.");
    }
}
